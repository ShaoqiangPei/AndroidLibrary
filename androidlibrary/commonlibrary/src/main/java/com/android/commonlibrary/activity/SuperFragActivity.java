package com.android.commonlibrary.activity;

import android.view.KeyEvent;
import com.android.commonlibrary.interfacer.OnFragmentBackListener;
import com.android.commonlibrary.util.LogUtil;

/**
 * Description: AppActivity的父类,具备加载fragment的能力
 *
 *  在SuperActivity基础之上增加activity加载fragment的能力
 *  处理了Fragment和activity的返回键功能
 *
 * Author:pei
 * Date: 2019/7/16
 */
public abstract class SuperFragActivity extends SuperActivity {

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
                LogUtil.i("======fragment中拦截处理返回键========");
                //fragment中拦截处理返回键
                mOnFragmentBackListener.onBackForward(keyCode,event);
                return true;
            } else {
                LogUtil.i("======activity中拦截返回键的处理========");
                //activity中拦截返回键的处理
                return onActivityKeyDown(keyCode,event);
            }
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
    protected boolean onActivityKeyDown(int keyCode, KeyEvent event){
        return super.onKeyDown(keyCode, event);
    }

}
