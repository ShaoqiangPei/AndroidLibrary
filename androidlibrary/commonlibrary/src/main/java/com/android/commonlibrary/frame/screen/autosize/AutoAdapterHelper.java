package com.android.commonlibrary.frame.screen.autosize;

import android.app.Activity;
import android.app.Application;

import com.android.commonlibrary.util.ScreenUtil;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.unit.Subunits;

/**
 * Title: 头条适配帮助类
 *
 * description: Activity/Fragment 适配屏幕需要实现 CustomAdapt 接口
 *              Activity/Fragment 取消适配屏幕需要实现 CancelAdapt 接口
 *
 * autor:pei
 * created on 2022/7/12
 */
public class AutoAdapterHelper {

    /***
     * 适配框架初始化,在Application中调用,防止多线程
     *
     * @param application
     * @return
     */
    public AutoAdapterHelper init(Application application){
        AutoSize.initCompatMultiProcess(application);
        return this;
    }


    /***
     * fragment适配需要在application中初始化调用
     *
     * 自定义fragment适配时设置为true
     */
    public void initForFragment(boolean flag){
        AutoSizeConfig.getInstance().setCustomFragment(flag);
    }


    /**设置副单位**/
    public void setSupportSubunitsMM(){
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

    /**获取AutoSizeConfig对象**/
    public AutoSizeConfig getAutoSizeConfig(){
        return AutoSizeConfig.getInstance();
    }

}
