package com.android.commonlibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Title:手动注册本地广播工具类 <具备安全性>
 * description:
 * autor:pei
 * created on 2019/10/20
 */
public class AppLocalReceiver extends BroadcastReceiver {

    private static final String LOCAL_RECEIVER_TAG="local_receiver_tag";

    private Context mContext;
    private String mActivityName;
    private OnReceiverListener mOnReceiverListener;
    private LocalBroadcastManager mLocalBroadcastManager;

    public AppLocalReceiver(){}

    public AppLocalReceiver(Context context, String activityName){
        this.mContext=context;
        this.mActivityName=activityName;
        //注册广播
        regActivity();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Bundle bundle=intent.getBundleExtra(LOCAL_RECEIVER_TAG);
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
        mLocalBroadcastManager=getLocalBroadcastManager(mContext);
        mLocalBroadcastManager.registerReceiver(AppLocalReceiver.this, filter);
    }

    private LocalBroadcastManager getLocalBroadcastManager(Context context){
        if(context!=null&&mLocalBroadcastManager==null){
            mLocalBroadcastManager= LocalBroadcastManager.getInstance(context);
        }
        return mLocalBroadcastManager;
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
    public void sendToBroadcast(Context context,String sendToActivityName, Bundle bundle) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra(LOCAL_RECEIVER_TAG,bundle);
        intent.setAction(sendToActivityName);
        mLocalBroadcastManager=getLocalBroadcastManager(context);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 注销广播
     */
    public void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(AppLocalReceiver.this);
    }

    public interface OnReceiverListener{
        void receiver(String action, Bundle bundle);
    }

}

//=====================使用示例==============================
//  以MainActivity为例，当我们要在MainActivity中注册一个广播监听的时候，我们可以这样：
//  //声明广播对象
//  private AppLocalReceiver mAppLocalReceiver;
//
//   //初始化并注册本地广播
//   mAppLocalReceiver=new AppLocalReceiver(MainActivity.this,MainActivity.class.getName());
//
//   //设置本地广播监听
//   mAppLocalReceiver.setOnReceiverListener(new AppLocalReceiver.OnReceiverListener() {
//     @Override
//     public void receiver(String action, Bundle bundle) {
//        //接收逻辑处理
//        //......
//
//       }
//     });
//
//    //在MainActivity销毁时，注销广播
//    mAppLocalReceiver.onDestroy();
//
//   若要在MainActivity中发起广播，你可以像下面这样：
//   //发送广播
//   Bundle bundle=new Bundle();
//   bundle.putInt("code",0);
//   bundle.putString("pp","测试");
//   mAppLocalReceiver.sendToBroadcast(MainActivity.this,MainActivity.class.getName(),bundle);
//
//   若你要在其他界面，如 ActivityB 界面给 MainActivity 发送广播，
//   你可以在ActivityB 中像下面这样给MainActivity发送广播：
//   //发送广播
//   AppLocalReceiver receiver=new AppLocalReceiver();
//   Bundle bundle=new Bundle();
//   bundle.putInt("code",1);
//   bundle.putString("pp","测试B");
//   receiver.sendToBroadcast(ActivityB.this,MainActivity.class.getName(),bundle);