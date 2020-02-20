package com.android.commonlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * Title:底部虚拟键盘浸入式工具类
 * description:
 * autor:pei
 * created on 2020/2/20
 */
public class NavigationBarUtil {

    private static final String NAVIGATION="navigationBarBackground";

    /**
     * 初始化时调用
     *
     * 注:rootView需要传具体的根view对象。
     *   例如当你界面对应的xml中根布局为ConstraintLayout,
     *   则此处rootView需要传ConstraintLayout实例。
     *
     * @param rootView 界面的根布局view对象
     * @param context 上下文
     */
    public static void initNavigationBar(View rootView, Context context){
        if(checkDeviceHasNavigationBar(context)){
            int barHeight=getNavigationHeight(context);
            rootView.setPadding(0,0,0,barHeight);
        }
    }

    /**
     * 手机底部NavigationBar透明
     * 在actiivty的onWindowFocusChanged()中调用
     *
     * @param context 上下文
     * @param color 底部 颜色,如:Color.TRANSPARENT
     */
    public static void onWindowFocusChanged(Context context, int color){
        if(isNavigationBarExist(context)){
            //非全面屏
            //手机底部虚拟键盘透明化
            transparentNavigationBar(context,color);
        }
    }


    /**隐藏底部虚拟按键**/
    public static void hideNavigationBar(Context context) {
        //隐藏虚拟按键
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = ((Activity) context).getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = ((Activity) context).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**显示虚拟按键**/
    public static void showNavigationBar(Context context) {
        //显示虚拟键盘
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //低版本sdk
            View v = ((Activity) context).getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = ((Activity) context).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 使手机底部虚拟键盘透明化
     *
     * 在actiivty的onWindowFocusChanged()中调用
     */
    private static void transparentNavigationBar(Context context, int color) {
        View mDecorView = ((Activity)context).getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity)context).getWindow().setNavigationBarColor(color);
        }
    }

    /**判断当前手机底部是否有虚拟键盘**/
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /***
     * 判断当前手机是否有底部虚拟NavigationBar
     * 若调用的话，需要在在actiivty的onWindowFocusChanged()中调用
     *
     * 注:目前全面屏手机无底部虚拟NavigationBar，非全面屏手机则有
     * @return true:该手机为非全面屏手机,有底部虚拟NavigationBar
     *         false:该手机为全面屏手机,无底部虚拟NavigationBar
     */
    private static boolean isNavigationBarExist(Context context) {
        ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        if(viewGroup!=null){
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if(viewGroup.getChildAt(i).getId()!= View.NO_ID
                        &&NAVIGATION.equals(context.getResources().getResourceEntryName(viewGroup.getChildAt(i).getId()))){
                    return true;
                }
            }
        }
        return false;
    }

    /**获取底部虚拟键盘的高度**/
    private static int getNavigationHeight(Context context) {
        if (context ==null) {
            return 0;
        }
        Resources resources =context.getResources();
        int resourceId =resources.getIdentifier("navigation_bar_height",
                "dimen","android");
        int height =0;
        if (resourceId >0) {
            //获取NavigationBar的高度
            height =resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }

}
