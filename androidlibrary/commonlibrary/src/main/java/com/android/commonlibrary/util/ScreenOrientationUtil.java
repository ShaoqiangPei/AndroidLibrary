package com.android.commonlibrary.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * Title:设置屏幕方向
 * description:
 * autor:pei
 * created on 2020/8/16
 */
public class ScreenOrientationUtil {

    /**设置竖屏**/
    public static void setPortrait(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    /**设置竖屏**/
    public static void setLandscape(Activity activity){
        //由于横屏有两个方向的横法，而这个设置横屏的语句，假设不是默认的横屏方向，会把已经横屏的屏幕旋转180°
        //所以能够先推断是否已经为横屏了
        if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        }
    }

}
