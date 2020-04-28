package com.android.commonlibrary.a_test.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.commonlibrary.a_test.interfacer.InitActivityX;
import com.android.commonlibrary.activity.AppActivity;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public abstract class SuperActivityX extends AppActivityX implements InitActivityX{

    protected AppActivityXProxy mAppActivityXProxy;
    protected AppActivityX mAppActivityX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAppActivityXProxy=new AppActivityXProxy();
        mAppActivityX= (AppActivityX) mAppActivityXProxy.bind(SuperActivityX.this);
        mAppActivityX.setInitActivityX(this);
        super.onCreate(savedInstanceState);
    }

}
