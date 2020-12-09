package com.android.commonlibrary.message_data;

import android.app.Activity;
import com.android.commonlibrary.util.LogUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:事务队列处理类
 * Description:
 *
 * Created by pei
 * Date: 2018/12/21
 */
public class AffairManager {

    private final int DEFAULT_INDEX = -1;
    private static final long DELAY_TIME = 500;//默认线程处理时间间隔(单位毫秒)
    private long mDelayTime=DELAY_TIME;

    //存储事务的集合
    private List<Object> mAffairList;
    private boolean isRunning = false;//事务线程是否在运行的标志
    private OnAffairListener mOnAffairListener;

    public AffairManager() {
        mAffairList = new ArrayList<>();
    }

    /**
     * 设置事务队列处理时间间隔
     * @param delayTime 间隔时间,单位毫秒
     * @return
     */
    public AffairManager setDelayTime(long delayTime){
        if(delayTime<0){
            throw new SecurityException("========事务队列设置处理时间间隔不能为负数========");
        }
        this.mDelayTime=delayTime;
        return  AffairManager.this;
    }

    public void setOnAffairListener(OnAffairListener onAffairListener){
        this.mOnAffairListener=onAffairListener;
    }

    /**事务处理方法**/
    public void handleAffair(Object obj) {
        if(mOnAffairListener==null){
            String errorMessage="AffairManager handle Exception: " +
                    "Unable to start AffairRunnable," +
                    "you need \"setOnAffairListener(OnAffairListener onAffairListener)\" " +
                    "before \"handleAffair(Object obj)\"";
            throw new NullPointerException(errorMessage);
        }
        if (obj == null) {
            return;
        }
        addAffair(obj);
        if (!isRunning()) {
            //启动线程执行
            setRunning(true);
            //启动线程
            AffairRunnable runnable = new AffairRunnable();
            new Thread(runnable).start();
        }
    }

    /**需要更新UI的时候,可以在doAffair(Object obj)或affairDestroy()中执行**/
    public void updateInUI(Activity activity, Runnable runnable){
        if(activity!=null){
            activity.runOnUiThread(runnable);
        }
    }

    /**结束事务**/
    public void stopAffair(){
        setRunning(false);
    }

    /**
     * 添加事务
     **/
    private void addAffair(Object obj) {
        if (obj != null) {
            synchronized (mAffairList) {
                mAffairList.add(obj);
            }
        }
    }

    /**获取事务Thread运行状态**/
    private  boolean isRunning() {
        return isRunning;
    }

    /**设置事务Thread运行状态**/
    private  void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * 取出事务
     **/
    private Object getAffair(int index) {
        if (index > DEFAULT_INDEX) {
            synchronized (mAffairList) {
                return mAffairList.get(index);
            }
        }
        return null;
    }

    /**
     * 移除事务(根据对象移除)
     **/
    private void removeAffair(Object obj) {
        if (mAffairList.contains(obj)) {
            synchronized (mAffairList) {
                mAffairList.remove(obj);
            }
        }
    }

    /**
     * 事务队列是否为空
     **/
    private boolean isAffairEmpty() {
        return mAffairList.isEmpty();
    }

    /**
     * 获取事务队列长度
     **/
    private int getAffairSize() {
        return mAffairList.size();
    }

    class AffairRunnable implements Runnable {

        @Override
        public void run() {
            if(mOnAffairListener==null){
                String errorMessage="AffairManager handle Exception: " +
                        "Unable to start AffairRunnable," +
                        "you need \"setOnAffairListener(OnAffairListener onAffairListener)\" " +
                        "before \"handleAffair(Object obj)\"";
                throw new NullPointerException(errorMessage);
            }
            LogUtil.i("======事务处理线程启动=====");
            while (isRunning()) {
                if (!isAffairEmpty()) {
                    LogUtil.i("======事务队列长度===" + getAffairSize());
                    Object obj = getAffair(0);
                    if(obj!=null){
                        //事务处理业务逻辑
                        boolean saveObj=mOnAffairListener.doAffair(obj);
                        if(!saveObj) {//为false表示事务执行完毕后会将该obj从队列中移除,默认返回fasle
                            //移除事务
                            removeAffair(obj);
                        }
                        try {
                            Thread.sleep(mDelayTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //移除事务
                        removeAffair(obj);
                    }
                } else {
                    LogUtil.i("======事务队列长度===" + getAffairSize());
                    //队列检测为空，准备退出线程
                    setRunning(false);
                    break;
                }
            }
            //清空事务队列
            mAffairList.clear();
            mOnAffairListener.affairDestroy();
            LogUtil.i("======AffairManager====线程结束====");
        }
    }

    public interface OnAffairListener {
        /**
         * 事务执行完毕后是否保留(不移除obj)
         *
         * @param obj 事务处理的obj
         * @return 队列中是否仍然保留obj的标志，默认返回false,即不保留(会从队列中将该obj移除)
         */
        boolean doAffair(Object obj);
        //队列中数据全部处理完毕
        void affairDestroy();
    }

}
