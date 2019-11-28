package com.android.commonlibrary.service.remote_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/***
 * 绑定式服务
 * 
 * @author pei
 * @create 2016-4-28
 *
 */
public abstract class RemoteService extends Service{
    
	private IBinder mBinder=new RemoteBinder();
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public class RemoteBinder extends Binder {
		public <T extends RemoteService>T getService() {
			return (getSelfService() == null) ? (T) RemoteService.this : (T) getSelfService();
		}
	}

	/**子类需要重写**/
	protected abstract RemoteService getSelfService();

}
