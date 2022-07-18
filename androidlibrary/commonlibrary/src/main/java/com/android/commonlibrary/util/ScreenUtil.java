package com.android.commonlibrary.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.android.commonlibrary.app.LibraryConfig;


/**
 * 屏幕相关辅助类
 *
 * @author pei
 * @create 2016-6-13
 */
public class ScreenUtil {

    private ScreenUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static DisplayMetrics getDisplayMetrics(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        display.getMetrics(dm);

        return dm;
    }

    /**屏幕基本信息**/
    public static void printScreenInfo(){
        LogUtil.i("*************屏幕信息*************\n");
        int width=ScreenUtil.getWidth();
        int height=ScreenUtil.getHeight();
        Context context=LibraryConfig.getInstance().getApplication();
        LogUtil.i("宽="+ width+"px("+ScreenUtil.px2dp(width,context)+"dp) 高="+height+"px("+ScreenUtil.px2dp(height,context)+"dp)\n");
        int dpi=ScreenUtil.getDpi();
        float density=ScreenUtil.getDensity();
        LogUtil.i("密度值="+ dpi+"dpi  density(像素比例)="+density+"\n");
        float smallwidthDensity=getSmallwidthDensity();
        LogUtil.i("smallwidthDensity(最小宽度)="+ smallwidthDensity+"dp\n");
        LogUtil.i("**********************************\n");
    }

    /***
     * 获取屏幕dpi,如 120/160/240/320 (dpi)
     *
     * @return
     */
    public static int getDpi() {
        DisplayMetrics dm = getDisplayMetrics();
        if(dm!=null){
            return dm.densityDpi;
        }
        return 0;
    }

    /***
     * 屏幕像素比例
     *
     * eg: 0.75/1.0/1.5/2.0
     */
    public static float getDensity(){
        DisplayMetrics dm = getDisplayMetrics();
        if(dm!=null){
            return dm.density;
        }
        return 0f;
    }

    /****
     * 屏幕最小宽度
     *
     * (系统会根据当前设备屏幕的 最小宽度 来匹配 values-swdp)
     * @return
     */
    public static float getSmallwidthDensity(){
        float smallwidthDensity=0f;
        Application application=LibraryConfig.getInstance().getApplication();
        if(application!=null) {
            smallwidthDensity = application.getResources().getConfiguration().smallestScreenWidthDp;
            LogUtil.i("===通过读取配置获取:smallwidthDensity="+smallwidthDensity+"(dp)");
        }else{
            float density=ScreenUtil.getDensity();
            int width=ScreenUtil.getWidth();
            int height=ScreenUtil.getHeight();
            int smallSide = width < height ? width : height;
            if (density == 0f || width == 0) {
                throw new SecurityException("smallwidthDensity获取异常(smallwidthDensity=smallSide/density,smallwidthDensity=" + smallwidthDensity + ",请查看是否width或height或density为0)" + "\n");
            } else {
                smallwidthDensity = smallSide / density;
                LogUtil.i("===通过屏幕宽高计算:smallwidthDensity="+smallwidthDensity+"(dp)");
            }
        }
        return smallwidthDensity;
    }

    /**
     * 获取屏幕宽
     **/
    public static int getWidth() {
        DisplayMetrics dm=getDisplayMetrics();
        if(dm!=null){
            return dm.widthPixels;
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeight(){
        DisplayMetrics dm=getDisplayMetrics();
        if(dm!=null){
            return dm.heightPixels;
        }
        return 0;
    }



    /**
     * 获得状态栏的高度
     **/
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = LibraryConfig.getInstance().getApplication().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     **/
    public static Bitmap snapShotWithStatusBar(Context context) {
        View view = ((Activity) context).getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getWidth();
        int height = getHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     **/
    public static Bitmap snapShotWithoutStatusBar(Context context) {
        View view = ((Activity) context).getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getWidth();
        int height = getHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bp;
    }

    /**
     * dp转px
     **/
    public static int dp2px(float dpVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * sp转px
     **/
    public static int sp2px(float spVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * px转dp
     **/
    public static float px2dp(float pxVal, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     **/
    public static float px2sp(float pxVal, Context context) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * textSize缩放比
     **/
    public static float getTextSizeScale() {
        int width = getWidth();
        float rate = (float) width / 320;
        return rate;
    }


    /**
     * 设置当前窗口亮度
     * @param brightness
     */
    public static void setWindowBrightness(Activity activity, float brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }

}
