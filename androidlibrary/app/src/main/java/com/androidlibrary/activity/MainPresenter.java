package com.androidlibrary.activity;

import android.content.Context;

import com.android.commonlibrary.interfacer.frame.struct.mvp.PreView;

/**
 * Title:登录功能通讯逻辑层
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, PreView view) {
        this.mContext = context;
        this.mView = (MainContract.View) view;
    }

    @Override
    public void attachView() {//可以用来注册RxBus

    }

    @Override
    public void detachView() {//可以用来注销RxBus

    }

    @Override
    public void login() {
        //以下写通讯代码
        //......

        mView.loginSuccess();
    }

}