package com.android.commonlibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Title:手动注册广播工具类
 * description:
 * autor:pei
 * created on 2019/10/20
 */
public class AppReceiver extends BroadcastReceiver {

    private static final String RECEIVER_TAG="RECEIVER_TAG";

    private Context mContext;
    private String mActivityName;
    private OnReceiverListener mOnReceiverListener;

    public AppReceiver(){}

    public AppReceiver(Context context, String activityName){
        this.mContext=context;
        this.mActivityName=activityName;

        //注册广播
        regActivity();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Bundle bundle=intent.getBundleExtra(RECEIVER_TAG);
        if(mOnReceiverListener!=null){
            mOnReceiverListener.receiver(action,bundle);
        }
    }

    /**
     * 注册广播
     *
     * @param
     */
    private void regActivity() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(mActivityName);
        mContext.registerReceiver(AppReceiver.this, filter);
    }

    /**设置监听**/
    public void setOnReceiverListener(OnReceiverListener listener){
        this.mOnReceiverListener=listener;
    }

    /**
     * 发送广播
     *
     * @param
     */
    public void sendToBroadcast(Context ctx, String sendToActivityName, Bundle bundle) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra(RECEIVER_TAG,bundle);
        intent.setAction(sendToActivityName);
        ctx.sendBroadcast(intent);
    }

    /**
     * 注销广播
     */
    public void onDestroy() {
        if (mContext != null) {
            mContext.unregisterReceiver(AppReceiver.this);
        }
    }

    public interface OnReceiverListener{
        void receiver(String action, Bundle bundle);
    }

}

//=====================使用示例==============================
//以MainActivity为例，当我们要在MainActivity中注册一个广播监听的时候，我们可以这样：
////声明广播对象
//private AppReceiver mAppReceiver;
//
////初始化(含注册广播逻辑)mAppReceiver对象
//   mAppReceiver=new AppReceiver(MainActivity.this,MainActivity.class.getName());
//
//        //接收广播
//        mAppReceiver.setOnReceiverListener(new AppReceiver.OnReceiverListener() {
//@Override
//public void receiver(String action, Bundle b) {
//        //接收数据，并做相关逻辑处理
//        //......
//        }
//        });
//
//        //在MainActivity销毁时，注销广播
//        mAppReceiver.onDestroy();
//
//若要在MainActivity中发起广播，你可以像下面这样：
////发送广播
//Bundle bundle=new Bundle();
//bundle.putInt("code",0);
//bundle.putString("key","我是MainActivity自己发送的值");
//mAppReceiver.sendToBroadcast(MainActivity.this,MainActivity.class.getName(),bundle);
//
//   若你要在其他界面，如 ActivityB 界面给 MainActivity 发送广播，
//   你可以在ActivityB 中像下面这样给MainActivity发送广播：
//   //发送广播
//   AppReceiver receiver=new AppReceiver();
//   Bundle bundle=new Bundle();
//   bundle.putInt("code",1);
//   bundle.putString("key","我是B发过来的值");
//   receiver.sendToBroadcast(this,MainActivity.class.getName(),bundle);