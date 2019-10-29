package com.android.commonlibrary.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 网络实时监测广播
 * 
 * @author pei
 * @version1.0
 * @create 2016-04-01
 * @description
 * 权限    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/> 
 * 初始化并注册广播
 *     NetworkReceiver networkReceiver=new NetworkReceiver(Mainactivity.this);
 * 设置网络状态监听
 *     networkReceiver.setOnNetworkListener(new NetworkReceiver.OnNetworkListener());
 * 销毁
 *     networkReceiver.onDestroy(); 
 */
public class NetworkReceiver extends BroadcastReceiver{
    
	private Context mContext;
	private OnNetworkListener mOnNetworkListener;

	public NetworkReceiver(Context context){
		this.mContext=context;
		//注册广播
		registerReceiver();
	}

	/**设置网络监听**/
	public void setOnNetworkListener(OnNetworkListener listener){
		this.mOnNetworkListener=listener;
	}
	
	/**注册广播**/
    private void registerReceiver() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(NetworkReceiver.this, intentfilter);
    }
    
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		ConnectivityManager connectionManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressLint("MissingPermission")
		NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			//有网络
			if(mOnNetworkListener!=null){
				mOnNetworkListener.available(networkInfo);
			}
		} else {
			//无网络
			if(mOnNetworkListener!=null){
				mOnNetworkListener.unavailable(networkInfo);
			}
		}
	}

	/**销毁网络广播监听**/
	public void onDestroy(){
		mContext.unregisterReceiver(NetworkReceiver.this);
	}

	public interface OnNetworkListener{

		void available(NetworkInfo networkInfo);

		void unavailable(NetworkInfo networkInfo);

	}
}
