package com.android.commonlibrary.a_test.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.commonlibrary.a_test.interfacer.IPreActivityX;
import com.android.commonlibrary.a_test.interfacer.InitActivityX;
import com.android.commonlibrary.mvp_frame.PrePresenter;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public abstract class PreActivityX<T> extends AppActivityX implements InitActivityX,IPreActivityX {

    protected AppActivityXProxy mAppActivityXProxy;
    protected AppActivityX mAppActivityX;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAppActivityXProxy=new AppActivityXProxy();
        mAppActivityXProxy.setIPreActivityX(this);
        mAppActivityX= (AppActivityX) mAppActivityXProxy.bind(new AppActivityX());
        mAppActivityX.setInitActivityX(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void loadMVP() {
        mAppActivityX.loadMVP();
        mPresenter= mAppActivityXProxy.getPresenter();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        mAppActivityX.onDestroy();
    }

}
