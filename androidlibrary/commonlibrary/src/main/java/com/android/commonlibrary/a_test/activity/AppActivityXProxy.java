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
        if(mIPreActivityX!=null){
            PrePresenter presenter= mIPreActivityX.getPresenter();
            if(null!=presenter&&"loadMVP".equals(methodName)){
                presenter.attachView();
            }else if(null!=presenter&&"onDestroy".equals(methodName)){
                presenter.detachView();
            }
        }
        result=method.invoke(mAppActivityX,args);
        return result;
    }

    public <T extends PrePresenter>T getPresenter(){
        if(mIPreActivityX!=null){
            return mIPreActivityX.getPresenter();
        }
        return null;
    }

}
