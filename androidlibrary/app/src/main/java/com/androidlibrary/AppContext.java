package com.androidlibrary;

import android.app.Application;
import com.android.commonlibrary.app.LibraryConfig;

public class AppContext extends Application {

    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化AndroidLibrary
        LibraryConfig.getInstance().init(this)
                //开启log调试,默认为关闭状态
                .setDebug(true);
    }


}
