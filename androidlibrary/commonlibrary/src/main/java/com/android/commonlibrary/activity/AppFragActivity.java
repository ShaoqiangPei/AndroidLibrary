package com.android.commonlibrary.activity;

import android.view.KeyEvent;

import com.android.commonlibrary.interfacer.OnFragmentBackListener;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.ToastUtil;

/**
 * Description: 集成Fragment时需要用到的activity
 *
 *  当需要加载Fragment并且要在activity中处理返回键的时候用此类
 *  具备AppActivity的所有特性
 *  主要用来处理Fragment和activity的返回键功能
 *
 * Author:pei
 * Date: 2019/7/16
 */
public abstract class AppFragActivity extends AppActivity{

    protected OnFragmentBackListener mOnFragmentBackListener;

    /**设置监听**/
    public void setOnFragmentBackListener(OnFragmentBackListener listener){
        this.mOnFragmentBackListener=listener;
    }

    /**重写返回键,用于处理Activity和Fragment界面的返回键逻辑**/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mOnFragmentBackListener != null) {
                //fragment中拦截处理返回键
                mOnFragmentBackListener.onBackForward(keyCode,event);
                LogUtil.i("========fragment返回键======");
            } else {
                //activity中拦截返回键的处理
                onActivityKeyDown(keyCode,event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * activity中拦截返回键的处理
     *
     * 当activity中加载有Fragment时需要处理返回键，重写此方法就行
     * @param keyCode
     * @param event
     */
    protected void onActivityKeyDown(int keyCode, KeyEvent event){

    }
}
