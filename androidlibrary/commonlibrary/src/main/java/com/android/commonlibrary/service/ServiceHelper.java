package com.android.commonlibrary.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;

/**
 * Title:服务帮助类
 * description:
 * autor:pei
 * created on 2019/11/17
 */
public class ServiceHelper {

    protected final int SERVICE_FRONT = 0x1988;

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
