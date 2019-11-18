package com.android.commonlibrary.service.local_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.android.commonlibrary.service.ServiceHelper;

/**
 * Title:"非绑定式"服务帮助类
 * description:
 * autor:pei
 * created on 2019/11/17
 */
public class LocalServiceHelper extends ServiceHelper {

    private Intent mLocalIntent;//“非绑定式"服务意图

    /***
     * 启动"非绑定式"服务
     * @param context
     * @param serviceCls
     * @param action 要与服务在manifast.xml中注册的<action android:name=“xxx”/> 中的 xxx 值保持一致
     */
    public void startLocalService(Context context, Class<?>serviceCls, String action){
        //需要注意的是：setAction中的参数要与服务在manifast.xml中注册的<action android:name=“xxx”/> 中的 xxx 值保持一致
        mLocalIntent=new Intent(context, serviceCls);
        mLocalIntent=startLocalServiceByIntent(context,mLocalIntent,serviceCls,action);
    }


    /***
     * 关闭"非绑定式"服务
     */
    public void stopLocalService(Context context){
        stopLocalServiceByIntent(context,mLocalIntent);
    }

    /***
     * 启动"非绑定式"服务(可设置intent传参)
     * @param context
     * @param intent
     * @param serviceCls
     * @param action
     * @return
     */
    public Intent startLocalServiceByIntent(Context context,Intent intent,Class<?>serviceCls,String action){
        if(intent!=null){
            intent.setAction(action);
            context.startService(intent);
        }
        return intent;
    }

    /**
     * 关闭"非绑定式"服务(需要传intent)
     */
    public void stopLocalServiceByIntent(Context context,Intent intent){
        if(intent!=null){
            context.stopService(intent);
        }
    }

    /***
     * "非绑定式"服务主动关闭自己
     */
    public void stopLocalBySelf(Service service){
        if(service!=null) {
            service.stopSelf();
        }
    }

    /***
     * "非绑定式"服务主动关闭自己
     */
    public void stopLocalSelfById(Service service,int startId){
        if(service!=null) {
            service.stopSelf(startId);
        }
    }

}
