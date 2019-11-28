package com.android.commonlibrary.service.remote_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.android.commonlibrary.service.ServiceHelper;

/**
 * Title:"绑定式"服务帮助类
 * description:
 * autor:pei
 * created on 2019/11/20
 */
public class RemoteServiceHelper <T extends RemoteService>extends ServiceHelper {

    protected Context mContext;
    protected Intent mServiceIntent;
    protected RemoteService mService;
    protected Class<T> mCls;

    /**在自建的Service中初始化时调用，主要用于设置服务前台模式**/
    public RemoteServiceHelper(){}

    /**在MainActivity中初始化调用，主要用于服务的启动，停止及与调用服务交互的方法**/
    public RemoteServiceHelper(Class<T> cls, Context context) {
        this.mCls = cls;
        this.mContext = context;
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            serviceDisconnected(arg0);
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder ibinder) {
            mService = ((RemoteService.RemoteBinder) ibinder).getService();
            serviceConnected(arg0, ibinder);
        }
    };

    /**
     * 子类可重写
     **/
    protected void serviceDisconnected(ComponentName arg0) {
        //服务解绑时需要做处理时，可重写此方法
    }

    /**
     * 子类可重写
     **/
    protected void serviceConnected(ComponentName arg0, IBinder ibinder) {
        //服务绑定时需要做处理时，可重写此方法
    }

    /**
     * 启动服务
     **/
    public void startService() {
        if (mCls == null) {
            throw new NullPointerException("==== mCls不能为null,并且mCls需要继承RemoteService ========");
        }
        mServiceIntent = new Intent(mContext, mCls);
        mContext.bindService(mServiceIntent, mConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 停止服务
     **/
    public void stopService() {
        if (mContext != null && mConn != null) {
            mContext.unbindService(mConn);
        }
    }

    /**
     * 获取服务对象，当要调用服务中其他方法时，可以通过此服务对象调用
     **/
    public <T extends RemoteService> T getService() {
        return (T) mService;
    }

}
