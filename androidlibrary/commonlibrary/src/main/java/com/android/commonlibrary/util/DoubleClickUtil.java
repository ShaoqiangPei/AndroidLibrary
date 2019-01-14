package com.android.commonlibrary.util;

import android.view.View;

/**
 * Created by Admin on 2017/6/26.
 * 防止按钮连击工具类
 */
public class DoubleClickUtil {

    private static final long DEFAULT_MILLISECONDS=1000;//一秒
    private static long mLastClick;

    public static boolean isDoubleClick(){
        //大于一秒方个通过
        if (System.currentTimeMillis() - mLastClick <= DEFAULT_MILLISECONDS){
            return true;
        }
        mLastClick = System.currentTimeMillis();
        return false;
    }

    public static boolean isDoubleClick(long milliseconds){
        //大于一秒方个通过
        if (System.currentTimeMillis() - mLastClick <= milliseconds){
            return true;
        }
        mLastClick = System.currentTimeMillis();
        return false;
    }

    public static void shakeClick(final View v) {
        v.setClickable(false);
        v.postDelayed(new Runnable(){
            @Override
            public void run() {
                v.setClickable(true);
            }
        }, DEFAULT_MILLISECONDS);
    }

    public static void shakeClick(final View v, long milliseconds) {
        v.setClickable(false);
        v.postDelayed(new Runnable(){
            @Override
            public void run() {
                v.setClickable(true);
            }
        }, milliseconds);
    }
}
