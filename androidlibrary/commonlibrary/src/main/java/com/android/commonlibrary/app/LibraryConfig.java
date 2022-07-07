package com.android.commonlibrary.app;

import android.app.Application;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Title:androidLibrary库初始化类
 * description:
 * autor:pei
 * created on 2020/1/9
 */
public class LibraryConfig {

    private Application mApplication;
    private boolean mDebug=false;//是否开启网络打印(默认不开启)
    private boolean mShowScreenInfo=false; //是否打印设备屏幕信息(默认不开启)

    private LibraryConfig(){}

    private static class Holder {
        private static LibraryConfig instance = new LibraryConfig();
    }

    public static LibraryConfig getInstance() {
        return Holder.instance;
    }

    /**初始化赋值(在项目的自定义Application中初始化)**/
    public LibraryConfig init(Application application){
        this.mApplication=application;
        return LibraryConfig.this;
    }

    /**
     * 是否打开Log打印
     *
     * @param debug true:打开调试log,  false:关闭调试log
     * @return
     */
    public void setDebug(boolean debug){
        this.mDebug=debug;
        //设置自定义打印开关
        LogUtil.setDebug(mDebug);
    }

    /**
     * 是否打印设备屏幕信息
     *
     * 注:当 mDebug==true 且 showScreenInfo==true时,打印才生效
     *    作为配置项，需要在 setDebug(boolean debug)方法之后调用
     *
     * @param showScreenInfo true:打印屏幕信息,  false:不打印屏幕信息
     * @return
     */
    public void setShowScreenInfo(boolean showScreenInfo){
        this.mShowScreenInfo=showScreenInfo;
        //是否打印设备屏幕信息(当 mDebug==true && showScreenInfo==true时,打印才生效)
        if(mDebug&&mShowScreenInfo){
            ScreenUtil.printScreenInfo();
        }
    }



    /**获取项目上下文**/
    public Application getApplication() {
        if(mApplication==null){
            throw new NullPointerException("====AndroidLibrary需要初始化：LibraryConfig.getInstance.init(Application application)===");
        }
        return mApplication;
    }

    /**
     * 获取log打印开关
     * @return true:打开调试log,  false:关闭调试log
     */
    public boolean isDebug(){
        return mDebug;
    }

    /**
     * 获取是否具备打印设备屏幕信息能力的状态
     *
     * @return true:已打开且具备打印屏幕信息能力,  false:不具备打印屏幕信息能力
     */
    public boolean isShowScreenInfo(){
        if(mDebug){
            return mShowScreenInfo;
        }
        return false;
    }

}
