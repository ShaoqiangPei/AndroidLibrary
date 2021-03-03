package com.android.commonlibrary.util.contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.android.commonlibrary.entity.contentprovider.SmsData;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: 监听短信接收器(一般用于处理接收验证短信)
 *
 * description: 需要在 androidManifast.xml中添加不同的以下权限
 *              <uses-permission android:name="android.permission.READ_SMS"/>
 *              android 6.0+还需要在代码中手动添加以上权限。
 * autor:pei
 * created on 2021/3/2
 */
public class SmsMonitor {

    //短信
    private static final String SMS_URI="content://sms";
    private static final String ADDRESS="address";
    private static final String PERSON="person";
    private static final String BODY="body";
    private static final String DATE="date";
    private static final String TYPE="type";

    public static final String SMS_RECEIVE="接收";
    public static final String SMS_SEND="发送";
    public static final String SMS_DRAFT="草稿";
    public static final String SMS_OUTBOX="发件箱";
    public static final String SMS_SEND_FAILED="发送失败";
    public static final String SMS_TO_SEND_LIST="待发送列表";
    public static final String SMS_ZERO="0类型短信";

    //检测时间段(防止重复调用)
    private static final long DEFAULT_MILLISECONDS=1000;//一秒
    //未查询到数据前重复查询最多次数
    private static final int TOTAL_QUERY_COUNT=5;
    //每次查询时间间隔(单位毫秒)
    private static final long QUERY_DIFF_TIME=300;

    private Context mContext;
    private Uri mUri;
    private ContentResolver mContentResolver;
    private ContentObserver mContentObserver;
    private long mLastTime;
    private int mCount;

    private SmsMonitor(){}

    private static class Holder {
        private static SmsMonitor instance = new SmsMonitor();
    }

    public static SmsMonitor getInstance() {
        return Holder.instance;
    }

    /***
     * 初始化
     *
     * @param context
     */
    public SmsMonitor init(Context context){
        this.mContext=context;
        return SmsMonitor.this;
    }

    /***
     * 注册短信接收监听(一般在点击"发送短信"按钮时注册)
     *
     * @param phoneNumber：phoneNumber为具体电话号码时表示只监听该号码短信
     *                     phoneNumber表示监听所有短信
     *
     * @param diffTime：设置监听时间段。如 diffTime=1000*30 表示监听过去30秒事件内的短信
     * @param listener: 监听得到的短信数据
     *
     */
    public void registerSmsObserver(String phoneNumber, long diffTime, OnSmsChangeListener listener){
        if(mContext==null){
            throw new NullPointerException("======请先调用init(Context context)方法========");
        }

        List<SmsData> list=new ArrayList<>();
        mContentResolver=mContext.getContentResolver();
        mUri= Uri.parse(SmsMonitor.SMS_URI);
        //设置要查询的列名
        String[] line = {SmsMonitor.ADDRESS,SmsMonitor.PERSON,SmsMonitor.BODY,SmsMonitor.DATE,SmsMonitor.TYPE};
        Handler handler=new Handler();
        //短信观察器
        mContentObserver=new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                if (accept()) {
                    LogUtil.i("=====监听通过=======");
                    //查询短信信息
                    querySmsInfo(line,list,phoneNumber,diffTime,listener);
                } else {
                    LogUtil.i("======监听限制=====");
                }
            }
        };
        //注册观察器
        mContentResolver.registerContentObserver(mUri,true,mContentObserver);
        LogUtil.i("======注册短信接收监听====");
    }

    /***
     * 查询本地短信
     *
     * @param line 查询条件
     * @param list 存储查询结果
     * @param phoneNumber 要查的电话号码,若为null,则查所有短信
     * @param diffTime 查询时间条件, diffTime - 现在 的时间段
     * @param listener 查询结果监听
     */
    private void querySmsInfo(String[] line, List<SmsData> list, String phoneNumber, long diffTime, OnSmsChangeListener listener){
        //查询条件
        String selection = null;
        if (StringUtil.isNotEmpty(phoneNumber)) {
            selection = "address='" + phoneNumber + "' and date>'" + (System.currentTimeMillis() - diffTime) + "'";
        } else {
            selection = "date>'" + (System.currentTimeMillis() -  diffTime) + "'";
        }
        Cursor cursor = mContext.getContentResolver().query(mUri, line, selection, null, "date desc");
        //下面就跟操作普通数据库一样了
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(SmsMonitor.ADDRESS));
                String person = cursor.getString(cursor.getColumnIndex(SmsMonitor.PERSON));
                String body = cursor.getString(cursor.getColumnIndex(SmsMonitor.BODY));
                String date = cursor.getString(cursor.getColumnIndex(SmsMonitor.DATE));
                String type = cursor.getString(cursor.getColumnIndex(SmsMonitor.TYPE));

                SmsData smsData = new SmsData();
                smsData.setAddress(address);
                smsData.setPerson(person);
                smsData.setBody(body);
                //时间长度转化为时间格式
                smsData.setDate(getLongToDate(date));
                //设置短信类型
                smsData.setType(getSmsType(type));
                if (!list.contains(smsData)) {
                    list.add(smsData);
                }
            }
            cursor.close();
        }
        if(listener != null){
            if(!list.isEmpty()){
                //由于是时间倒序查询添加的,所以最新一条数据 index=0
                listener.getSmsInfo(list.get(0));
                //查到短信后将查询计数次数清零
                mCount=0;
            }else{
                LogUtil.i("======获取list无数据重新执行=====");
                //若未查询到短信则每隔300毫秒查一次,最多查5次
                if(mCount<SmsMonitor.TOTAL_QUERY_COUNT) {
                    try {
                        Thread.sleep(SmsMonitor.QUERY_DIFF_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    querySmsInfo(line, list, phoneNumber, diffTime, listener);
                    mCount++;
                }else{
                    //连续查5次还没查到短信则宣告查询失败
                    mCount=0;
                    LogUtil.e("======短信监听失败=====");
                    //查询失败
                    listener.getSmsInfo(null);
                }
            }
        }else{
            LogUtil.i("======监听listener为null=====");
        }
    }

    /**防止重复调用**/
    private boolean accept() {
        //大于一秒方可通过
        long diff = System.currentTimeMillis() - mLastTime;
        mLastTime = System.currentTimeMillis();
        if (diff > SmsMonitor.DEFAULT_MILLISECONDS) {
            return true;
        }
        return false;
    }

    /***
     * 注销短信接收监听
     *
     * 注：一般在短信接收超时时注销
     *    当然为了保证代码健壮性,在界面退出时也要调用
     */
    public void unRegisterSmsObserver(){
        if(mContentResolver!=null&&mContentObserver!=null){
            mContentResolver.unregisterContentObserver(mContentObserver);
            LogUtil.i("======注销短信接收监听====");
        }
    }

    /**
     * 将毫秒数转成日期
     * @param dateStr eg: "1581004800000"
     * @return eg:2020-02-07
     */
    private String getLongToDate(String dateStr){
        long dateLong=0;
        try {
            dateLong= Long.valueOf(dateStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sf.format(dateLong);
        return nowTime;
    }

    /**短信类型**/
    private String getSmsType(String type){
        String strType = "";
        switch (type) {
            case "1":
                strType=SmsMonitor.SMS_RECEIVE;
                break;
            case "2":
                strType=SmsMonitor.SMS_SEND;
                break;
            case "3":
                strType=SmsMonitor.SMS_DRAFT;
                break;
            case "4":
                strType=SmsMonitor.SMS_OUTBOX;
                break;
            case "5":
                strType=SmsMonitor.SMS_SEND_FAILED;
                break;
            case "6":
                strType=SmsMonitor.SMS_TO_SEND_LIST;
                break;
            case "0":
                strType=SmsMonitor.SMS_ZERO;
                break;
            default:
                strType=null;
                break;
        }
        return strType;
    }

    /**短信变化监听接口**/
    public interface OnSmsChangeListener{
        /**
         * 查询返回结果
         *
         * @param smsData 返回具体对象表示查询成功,返回 null表示查询失败
         */
        void getSmsInfo(SmsData smsData);
    }

}
