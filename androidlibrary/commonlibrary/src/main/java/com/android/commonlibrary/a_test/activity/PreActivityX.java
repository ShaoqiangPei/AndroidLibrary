package com.android.commonlibrary.a_test.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.commonlibrary.a_test.interfacer.IPreActivityX;
import com.android.commonlibrary.a_test.interfacer.InitActivityX;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public abstract class PreActivityX extends AppActivityX implements InitActivityX,IPreActivityX {

    protected AppActivityXProxy mAppActivityXProxy;
    protected AppActivityX mAppActivityX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAppActivityXProxy=new AppActivityXProxy();
        mAppActivityXProxy.setIPreActivityX(this);
        mAppActivityX= (AppActivityX) mAppActivityXProxy.bind(PreActivityX.this);
        mAppActivityX.setInitActivityX(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void loadMVP() {
        mAppActivityX.loadMVP();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        mAppActivityX.onDestroy();
    }

}
