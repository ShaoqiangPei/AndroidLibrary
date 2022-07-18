package com.androidlibrary.activity;

import com.android.commonlibrary.interfacer.mvp.PrePresenter;
import com.android.commonlibrary.interfacer.mvp.PreView;

/**
 * Title:登录功能接口层
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainContract {

    public interface View extends PreView {
        void loginSuccess();
        void loginFail(String msg);
    }

    public interface Presenter extends PrePresenter {
        //登录
        void login();
    }

}