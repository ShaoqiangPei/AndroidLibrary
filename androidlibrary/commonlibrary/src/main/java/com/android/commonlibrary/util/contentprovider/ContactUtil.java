package com.android.commonlibrary.util.contentprovider;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import com.android.commonlibrary.entity.contentprovider.ContactData;
import com.android.commonlibrary.entity.contentprovider.SmsData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: 手机联系人,短信获取帮助类
 *
 * description: 根据不同的方法需要在 androidManifast.xml中添加不同的以下权限
 *              <uses-permission android:name="android.permission.READ_SMS"/>
 *              <uses-permission android:name="android.permission.READ_CONTACTS"/>
 *              <uses-permission android:name="android.permission.SEND_SMS"/>
 *              android 6.0+还需要在代码中手动添加以上权限。
 *
 * autor:pei
 * created on 2021/3/2
 */
public class ContactUtil {

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

    /***
     * 获取手机短信
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.READ_SMS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     * @param context
     * @return
     */
    public static List<SmsData> getSMSDataList(Context context){
        List<SmsData> list=new ArrayList<>();

        //获取内容提供者
        ContentResolver contentResolver = context.getContentResolver();
        //获取短信表的路径
        Uri uri = Uri.parse(ContactUtil.SMS_URI);
        //设置要查询的列名
        String[] line = {ContactUtil.ADDRESS,ContactUtil.PERSON,ContactUtil.BODY,ContactUtil.DATE,ContactUtil.TYPE};
        //各个参数的意思，路径、列名、条件、条件参数、排序
        Cursor cursor = contentResolver.query(uri, line, null, null, null);
        //下面就跟操作普通数据库一样了
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(ContactUtil.ADDRESS));
                String person = cursor.getString(cursor.getColumnIndex(ContactUtil.PERSON));
                String body = cursor.getString(cursor.getColumnIndex(ContactUtil.BODY));
                String date = cursor.getString(cursor.getColumnIndex(ContactUtil.DATE));
                String type = cursor.getString(cursor.getColumnIndex(ContactUtil.TYPE));

                SmsData smsData=new SmsData();
                smsData.setAddress(address);
                smsData.setPerson(person);
                smsData.setBody(body);
                //时间长度转化为时间格式
                smsData.setDate(getLongToDate(date));
                //设置短信类型
                smsData.setType(getSmsType(type));
                list.add(smsData);
            }
            cursor.close();
        }
        return list;
    }

    /**短信类型**/
    public static String getSmsType(String type){
        String strType = "";
        switch (type) {
            case "1":
                strType=ContactUtil.SMS_RECEIVE;
                break;
            case "2":
                strType=ContactUtil.SMS_SEND;
                break;
            case "3":
                strType=ContactUtil.SMS_DRAFT;
                break;
            case "4":
                strType=ContactUtil.SMS_OUTBOX;
                break;
            case "5":
                strType=ContactUtil.SMS_SEND_FAILED;
                break;
            case "6":
                strType=ContactUtil.SMS_TO_SEND_LIST;
                break;
            case "0":
                strType=ContactUtil.SMS_ZERO;
                break;
            default:
                strType=null;
                break;
        }
        return strType;
    }


    /**
     * 将毫秒数转成日期
     * @param dateStr eg: "1581004800000"
     * @return eg:2020-02-07
     */
    public static String getLongToDate(String dateStr){
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


    /**
     * 获取手机联系人
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.READ_CONTACTS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     *
     * 通过ContactsContract.Contacts.CONTENT_URI来获取_ID和DISPLAY_NAME
     * _ID 该联系人的索引
     * 通过这个ID可以在ContactsContract.CommonDataKinds.Phone.CONTENT_URI 中找到该联系人的电话号码
     * 通过这个ID可以在ContactsContract.CommonDataKinds.Email.CONTENT_URI 找到该联系人的邮箱
     * DISPLAY_NAME 是该联系人的姓名
     */
    public static List<ContactData> getContactDataList(Context context){
        List<ContactData> list=new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactData contactData = new ContactData();
                //获取该联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //该联系人的姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactData.setName(name);

                /**
                 * 查找该联系人的phone信息
                 * 在ContactsContract.CommonDataKinds.Phone.CONTENT_URI 中查询
                 * 条件为ContactsContract.CommonDataKinds.Phone.CONTACT_ID = 上面查询到的ID
                 */
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                if (phones != null && phones.moveToNext()) {
                    //获取该联系人的手机号码
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactData.setNumber(number);
                    phones.close();
                }

                /**
                 * 查找该联系人的email信息
                 * 在ContactsContract.CommonDataKinds.Email.CONTENT_URI 中查询
                 * 条件为ContactsContract.CommonDataKinds.Phone.CONTACT_ID = 上面查询到的ID
                 */
                Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId, null, null);
                if (emails != null && emails.moveToNext()) {
                    //获取该联系人的邮箱
                    String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    contactData.setEmail(email);
                    emails.close();
                }
                list.add(contactData);
            }
            cursor.close();
        }
        return list;
    }

    /****
     * 发送短信
     *
     * 需要在 androidManifast.xml中添加以下权限
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     * android 6.0+还需要在代码中手动添加以上权限。
     *
     * @param phoneNumber：发送短信时对方电话号码
     * @param message：要发送的信息
     * @param sendSmsAction: 发送短信时需要处理发送事件的广播action,要设置成唯一标识字符串,如：com.test.kk.send
     * @param receiveSmsAction: 接收短信时需要处理接收事件的广播action,要设置成唯一标识字符串,如：com.test.kk.receive
     * @param context:上下文
     */
    public static void sendSmsMessage(String phoneNumber, String message,
                                      String sendSmsAction,
                                      String receiveSmsAction,
                                      Context context){
        String numberFlag="phoneNumber";
        String messageFlag="message";
        Intent sendIntent=new Intent(sendSmsAction);
        sendIntent.putExtra(numberFlag,phoneNumber);
        sendIntent.putExtra(messageFlag,message);
        PendingIntent sendPI = PendingIntent.getBroadcast(context,0,sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //声明短信接收的广播意图
        Intent receiveIntent=new Intent(receiveSmsAction);
        receiveIntent.putExtra(numberFlag,phoneNumber);
        receiveIntent.putExtra(messageFlag,message);
        PendingIntent receivePI = PendingIntent.getBroadcast(context,1,receiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //发送短信
        SmsManager smsManager= SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,message,sendPI,receivePI);
    }

}
