package com.android.commonlibrary.app;

import android.app.Application;

/**
 * Title:androidLibrary库初始化类
 * description:
 * autor:pei
 * created on 2020/1/9
 */
public class LibraryConfig {

    private Application mApplication;

    private LibraryConfig(){}

    private static class Holder {
        private static LibraryConfig instance = new LibraryConfig();
    }

    public static LibraryConfig getInstance() {
        return Holder.instance;
    }

    /**初始化赋值(在项目的自定义Application中初始化)**/
    public void init(Application application){
        this.mApplication=application;
    }

    /**获取项目上下文**/
    public Application getApplication() {
        if(mApplication==null){
            throw new NullPointerException("====AndroidLibrary需要初始化：LibraryConfig.getInstance.init(Application application)===");
        }
        return mApplication;
    }

}
