package com.android.commonlibrary.util;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Title:屏幕亮度工具类
 *
 * description:  能设置屏幕亮度，还能设置屏幕是否常亮
 *               需要权限  <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
 *
 * autor:pei
 * created on 2020/8/16
 */
public class ScreenBrightUtil {

    /***
     * 获取屏幕亮度
     *
     * 注：屏幕亮度最亮255，最暗0
     */
    public static int getScreenBrightness(Context context){
        ContentResolver contentResolver=context.getContentResolver();
        int defaultValue=-1;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defaultValue);
    }


    /***
     * 设置屏幕亮度
     *
     * 注：屏幕亮度最亮255，最暗0
     */
    public static void setScreenBrightness(int brightness, Context context) {
        if(brightness<0||brightness>255){
            throw new SecurityException("=======亮度值需设置在 0-255 之间的int值=======");
        }
        //设置为手动模式
        setBrightnessManualMode(context);
        //设置屏幕亮度
        Window window = ((AppCompatActivity)context).getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    /**设置屏幕最亮**/
    public static void setMaxBrightness(Context context){
        //设置为手动模式
        setBrightnessManualMode(context);
        //设置屏幕亮度
        setScreenBrightness(255,context);
    }

    /**设置屏幕最暗**/
    public static void setMinBrightness(Context context){
        //设置为手动模式
        setBrightnessManualMode(context);
        //设置屏幕亮度
        setScreenBrightness(0,context);
    }

    /***
     * 设置屏幕常亮
     * 需要在界面调用 "setContentView(R.layout.布局id);" 之前调用。
     * @param context
     */
    public static void setScreenKeepOn(Context context){
        ((AppCompatActivity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /***
     * 清除屏幕常亮
     *
     * 常在activity的onPause()周期内调用(仅供参考)
     * @param context
     */
    public static void cancelScreenKeepOn(Context context){
        ((AppCompatActivity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    /***
     * 设置亮度调节模式为手动模式
     *
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC：自动调节模式
     * SCREEN_BRIGHTNESS_MODE_MANUAL：手动调节模式
     */
    private static void setBrightnessManualMode(Context context){
        ContentResolver contentResolver=context.getContentResolver();
        int model= 0;
        try {
            model = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(model== Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC){
            Settings.System.putInt(
                    contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
    }

}
