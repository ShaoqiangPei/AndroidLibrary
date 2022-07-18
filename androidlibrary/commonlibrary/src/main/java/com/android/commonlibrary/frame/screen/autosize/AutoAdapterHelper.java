package com.android.commonlibrary.screenadapter.autosize;

import android.app.Activity;
import com.android.commonlibrary.util.ScreenUtil;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.unit.Subunits;

/**
 * Title: 头条适配帮助类
 *
 * description: Activity需要实现 CustomAdapt 接口
 * autor:pei
 * created on 2022/7/12
 */
public class AutoAdapterHelper {

    //==================fragment=======================

    /***
     * fragment适配需要在application中初始化调用
     *
     * 自定义fragment适配时设置为true
     */
    public void initForFragment(boolean flag){
        AutoSizeConfig.getInstance().setCustomFragment(flag);
    }

    //==================Activity=======================

    /**在实现 CustomAdapt 接口的Activity 的 getSizeInDp() 中调用 **/
    public float getSizeInDp(){
        int sizeInDp;
        if (AutoSizeConfig.getInstance().getScreenWidth() > AutoSizeConfig.getInstance().getScreenHeight()) {
            sizeInDp = AutoSizeConfig.getInstance().getDesignWidthInDp();
        } else {
            sizeInDp = AutoSizeConfig.getInstance().getDesignHeightInDp();
        }
        return sizeInDp;
    }

    /**在 activity 的 onConfigurationChanged方法中调用**/
    public void onConfigurationChanged(Activity activity, CustomAdapt customAdapt) {
        setScreenWidthHeight();
        setDensityByAutoSize(activity,customAdapt);
    }

    /**在 activity 的 onResume 方法中调用**/
    public void onResume(Activity activity, CustomAdapt customAdapt) {
        setScreenWidthHeight();
        setDensityByAutoSize(activity,customAdapt);
    }

    /**在 activity 的 onRestart 方法中调用**/
    public void onRestart(Activity activity, CustomAdapt customAdapt) {
        setScreenWidthHeight();
        setDensityByAutoSize(activity,customAdapt);
    }

    /**在 activity 的 getResources 方法中调用**/
    public void getResources(Activity activity, CustomAdapt customAdapt) {
        setDensityByAutoSize(activity,customAdapt);
    }


    private void setScreenWidthHeight() {
        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtil.getWidth());
        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtil.getHeight());
    }

    private void setDensityByAutoSize(Activity activity, CustomAdapt customAdapt) {
        if (activity != null && customAdapt != null) {
            float density = 0f;
            float tempDensity = activity.getResources().getDisplayMetrics().density;
            if (density != tempDensity) {
                setScreenWidthHeight();
                try {
                    AutoSizeCompat.autoConvertDensityOfCustomAdapt(activity.getResources(), customAdapt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                density = activity.getResources().getDisplayMetrics().density;
            }
        }
    }

    //==============设置副单位及配置相关===================

    /**设置副单位**/
    public void setSupportSubunitsMM(){
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

    public AutoSizeConfig getAutoSizeConfig(){
        return AutoSizeConfig.getInstance();
    }

}
