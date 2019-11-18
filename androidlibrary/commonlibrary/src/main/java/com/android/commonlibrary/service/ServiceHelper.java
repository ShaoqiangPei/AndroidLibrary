package com.android.commonlibrary.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.android.commonlibrary.R;
import com.android.commonlibrary.service.local_service.LocalServiceHelper;
import com.android.commonlibrary.util.NotificationHelper;

/**
 * Title:服务帮助类
 * description:
 * autor:pei
 * created on 2019/11/17
 */
public class ServiceHelper {

    protected final int SERVICE_FRONT = 0x1988;

    /**
     * 开启前台运行模式(简易通知栏模式)
     * @param drawableId 设置小图标,一般设置app的icon,样式如：R.mipmap.ic_launcher
     */
    public void startForeService(Service service,int drawableId,Context context){
        if(service!=null&&context!=null&&drawableId!=0) {
            NotificationHelper notificationHelper = new NotificationHelper();
            //创建builder
            NotificationCompat.Builder builder = notificationHelper.init(drawableId)//初始化,设置小图标,必须调用,一般设置app的icon
                    .setTitle("")//设置标题
                    .setContent("")//设置内容
                    .createBuilder(context);//创建builder
            Notification notification = builder.build();
            //设置前台模式
            startForeService(service, notification);
        }
    }

    /**开启服务前台模式**/
    public void startForeService(Service service, Notification notification){
        if(service!=null&&notification!=null) {
            service.startForeground(SERVICE_FRONT, notification);
        }
    }

    /**关闭服务前台模式**/
    public void stopForeService(Service service){
        if(service!=null) {
            service.stopForeground(true);
        }
    }

}
