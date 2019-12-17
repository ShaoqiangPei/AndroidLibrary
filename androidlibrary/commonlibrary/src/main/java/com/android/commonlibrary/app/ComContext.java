package com.android.commonlibrary.app;

import android.app.Application;

/**
 * Title:自定义的Application,继承LitePalApplication,集成LitePal数据库功能
 * Description:
 *
 * Created by pei
 * Date: 2017/12/30
 */
public class ComContext extends Application{

    private static ComContext instance;

    public static synchronized ComContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
