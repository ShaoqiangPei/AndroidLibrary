package com.android.commonlibrary.a_test.activity;

import com.android.commonlibrary.a_test.interfacer.IPreActivityX;
import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.mvp_frame.PrePresenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Title:AppActivity代理类
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public class AppActivityXProxy implements InvocationHandler {

    protected AppActivityX mAppActivityX;
    protected PrePresenter mPresenter;
    protected IPreActivityX mIPreActivityX;

    public Object bind(AppActivityX appActivityX){
        this.mAppActivityX=appActivityX;
        return Proxy.newProxyInstance(mAppActivityX.getClass().getClassLoader(),mAppActivityX.getClass().getInterfaces(),this);
    }

    public void setIPreActivityX(IPreActivityX iPreActivityX){
        this.mIPreActivityX=iPreActivityX;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;

        String methodName=method.getName();
        if("loadMVP".equals(methodName)){
            if(mIPreActivityX!=null) {
                mPresenter = mIPreActivityX.getPresenter();
                if (mPresenter != null) {
                    mPresenter.attachView();
                }
            }
        }else if("onDestroy".equals(methodName)){
            if (mPresenter != null) {
                mPresenter.detachView();
            }
        }
        result=method.invoke(mAppActivityX,args);
        return result;
    }

}
