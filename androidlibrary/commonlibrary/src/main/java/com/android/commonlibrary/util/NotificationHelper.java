package com.android.commonlibrary.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

/**
 * Title: “通知”工具类
 * description: 建立通知不需要权限
 * autor:pei
 * created on 2019/11/8
 */
public class NotificationHelper {

    private static final String ACTIVITY_TAG="activity_tag";//activity传值标志
    private static final String BROADCAST_TAG="broadcast_tag";//broadcast传值标志

    private NotificationManager mNotificationManager;// 通知管理对象
    private Notification mNotification;// 存储通知所需的各种信息

    private int mSmallIcon;//小图标,一般为应用app的icon,格式如: R.mipmap.ic_launcher
    private int mLargeIcon;//设置大图标,格式如: R.mipmap.ic_launcher<一般不设置的时候会显示小图标的图片>
    private String mTitle;//标题
    private String mContent;//内容

    //请求跳转Activity
    private int mActivityRequestCode;
    private Bundle mActivityBundle;
    private Class<?>mActivityClass;
    //请求跳转broadcast
    private int mBroadcastRequestCode;
    private Bundle mBroadcastBundle;
    private Class<?>mBroadcastClass;

    /**
     * 初始化,设置小图标 [必须调用]
     *
     * 注：小图标必须设置,否则通知不会显示,严重会导致app崩溃
     * @param smallIcon
     * @return
     */
    public NotificationHelper init(int smallIcon){
        this.mSmallIcon=smallIcon;
        return NotificationHelper.this;
    }

    /**设置标题**/
    public NotificationHelper setTitle(String title){
        this.mTitle=title;
        return NotificationHelper.this;
    }

    /**设置内容**/
    public NotificationHelper setContent(String content){
        this.mContent=content;
        return NotificationHelper.this;
    }

    /**
     * 设置大图标
     *
     * @param largeIcon 样式为：R.mipmap.ic_launcher
     * @return
     */
    public NotificationHelper setLargeIcon(int largeIcon){
        this.mLargeIcon=largeIcon;
        return NotificationHelper.this;
    }

    /**
     * 设置用于点击通知跳转到activity
     * @param requestCode
     * @param cls cls填类似“TestActivity.class”格式
     * @return
     */
    public NotificationHelper setActivityIntent(Bundle bundle,int requestCode, Class<?>cls){
        this.mActivityRequestCode=requestCode;
        this.mActivityBundle=bundle;
        this.mActivityClass=cls;
        return NotificationHelper.this;
    }

    /**
     * 设置用于点击通知跳转到broadcast(广播)
     * @param requestCode
     * @param cls cls填类似“TestActivity.class”格式
     * @return
     */
    public NotificationHelper setBroadcastIntent(Bundle bundle,int requestCode,Class<?>cls){
        this.mBroadcastRequestCode=requestCode;
        this.mBroadcastBundle=bundle;
        this.mBroadcastClass=cls;
        return NotificationHelper.this;
    }


    public NotificationCompat.Builder createBuilder(Context context) {
        NotificationCompat.Builder builder = getBuilder(context);
//        builder.setNumber(10)//显示在通知栏右边的数字
        builder.setWhen(System.currentTimeMillis());
        //设置标题
        if(StringUtil.isNotEmpty(mTitle)){
            builder.setContentTitle(mTitle);//设置通知标题
        }
        //设置内容
        if(StringUtil.isNotEmpty(mContent)){
            builder.setContentText(mContent);//设置通知内容
        }
        //设置大图标
        if(mLargeIcon!=0) {
            Drawable drawable = context.getResources().getDrawable(mLargeIcon);//获取drawable
            try {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bitmap = bd.getBitmap();
                builder.setLargeIcon(bitmap);//设置大图标
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //设置跳转activity
        if(mActivityClass!=null){
            //设置PendingIntent，用于点击通知条要跳转的类
            Intent intent = new Intent(context, mActivityClass);//cls填类似“TestActivity.class”格式
            //        intent.setAction("xxx");
            if(mActivityBundle!=null){
                intent.putExtra(NotificationHelper.ACTIVITY_TAG+mActivityRequestCode,mActivityBundle);
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(context, mActivityRequestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pendingIntent);//点击通知时跳转到intent指定的activity
            builder.setDeleteIntent(pendingIntent);//侧滑通知时跳转到intent指定的activity
            builder.setAutoCancel(true);//设为true，点击通知栏移除通知(需要和setContentIntent一起使用,否则无效)
        }
        //设置跳转广播
        if(mBroadcastClass!=null){
            //设置PendingIntent，用于点击通知条要跳转的类
            Intent intent = new Intent(context, mBroadcastClass);//cls为一个广播类，一般注册一个静态广播处理
            //        intent.setAction("xxx");
            if(mBroadcastBundle!=null){
                intent.putExtra(NotificationHelper.BROADCAST_TAG+mBroadcastRequestCode,mBroadcastBundle);
            }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, mBroadcastRequestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pendingIntent);//点击通知时跳转到intent指定的activity
            builder.setDeleteIntent(pendingIntent);//侧滑通知时跳转到intent指定的activity
            builder.setAutoCancel(true);//设为true，点击通知栏移除通知(需要和setContentIntent一起使用,否则无效)
        }
        return builder;
    }

    /**获取activity传值的bundle**/
    public Bundle getActivityBundle(Intent intent,int activityRequestCode){
        Bundle bundle=null;
        if(intent!=null){
            bundle=intent.getBundleExtra(NotificationHelper.ACTIVITY_TAG+activityRequestCode);
        }
        return bundle;
    }

    /**获取broadcast传值的bundle**/
    public Bundle getBroadcastBundle(Intent intent,int broadcastRequestCode){
        Bundle bundle=null;
        if(intent!=null){
            bundle=intent.getBundleExtra(NotificationHelper.BROADCAST_TAG+broadcastRequestCode);
        }
        return bundle;
    }

    private NotificationCompat.Builder getBuilder(Context context){
        //channelId的实际作用是将notification进行分类，如设置不同优先级等
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        if(mSmallIcon==0) {
            throw new SecurityException("=====请先调用 ‘setSmallIcon(int smallIcon)’ 方法=====");
        }
        builder.setSmallIcon(mSmallIcon);//小图标必须设置,否则通知不会显示,严重会导致app崩溃
        return builder;
    }


    /**
     * 设置自定义通知布局<一般单独使用>
     *
     * @param layoutId 自定义布局,样式为:R.layout.activity_main
     * @return
     */
    public NotificationCompat.Builder createCustomBuilder(Context context, int layoutId, OnRemoteViewListener listener) {
        //channelId的实际作用是将notification进行分类，如设置不同优先级等
        NotificationCompat.Builder builder = getBuilder(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutId);
        if(listener!=null) {
            RemoteViews reView = listener.getRemoteView(remoteViews);
            if (reView != null) {
                remoteViews = reView;
            }
        }
        builder.setCustomBigContentView(remoteViews)//设置通知的布局
                .setCustomHeadsUpContentView(remoteViews)//设置悬挂通知的布局
                .setCustomContentView(remoteViews)
                .setContent(remoteViews);
        return builder;
    }

    public interface OnRemoteViewListener{
        RemoteViews getRemoteView(RemoteViews remoteViews);
    }

    /**发送通知**/
    public void sendNotification(Context context, int notificationId, NotificationCompat.Builder builder){
        if(builder!=null) {
            //建立通知
            if (mNotificationManager == null) {
                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            //高版本需要渠道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//android8.0
                //只在Android O之上需要渠道
                String channelId = "default";
                String channelName = "默认通知";
                NotificationChannel notificationChannel=new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            mNotification = builder.build();
            //发送通知
            mNotificationManager.notify(notificationId, mNotification);
        }
    }

    /***
     * 取消消息提醒
     *
     * @param notificationId 通知id
     */
    public void cancel(Context context,int notificationId){
        if(mNotificationManager==null){
            mNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mNotificationManager.cancel(notificationId);

    }

}

//========通知基础知识参考=============
//https://www.jianshu.com/p/cb8426620e74
//https://gavinliu.cn/2017/08/22/Android-%E5%90%83%E5%A5%A5%E5%88%A9%E5%A5%A5%E7%B3%BB%E5%88%97-1-Notification/
