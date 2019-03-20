package com.android.commonlibrary.app;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Title:自定义的Application,继承LitePalApplication,集成LitePal数据库功能
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/12/30
 */
public class ComContext extends LitePalApplication {

    private static ComContext instance;

    public static synchronized ComContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //引入LitePal数据存储框架
        LitePal.initialize(this);
//        //引入selector框架
//        DevShapeUtils.init(this);
    }
}
