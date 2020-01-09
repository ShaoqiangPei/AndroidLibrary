package com.android.commonlibrary.util;

import android.app.Service;
import android.os.Vibrator;
import com.android.commonlibrary.app.LibraryConfig;

/**
 * 手机震动工具类
 *
 * @author Administrator
 *         权限
 *         <uses-permission android:name="android.permission.VIBRATE" />
 */
public class VibratorUtil {
    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[震动时长，静止时长，震动时长，静止时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */

    private static long[] defaultpattern = {400, 800, 400, 800};

    public static boolean isLongVibrate;

    private static Vibrator getVibrator() {
        Vibrator vibrator = (Vibrator) LibraryConfig.getInstance().getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        return vibrator;
    }

    public static void startVibrate() {
        Vibrator vibrator = getVibrator();
        vibrator.vibrate(500);
    }

    public static void startVibrate(long[] pattern, boolean isRepeat) {
        isLongVibrate = true;
        if (pattern == null) {
            pattern = defaultpattern;
        }
        Vibrator vibrator = getVibrator();
        vibrator.vibrate(pattern, isRepeat ? 1 : -1);
    }

    // 停止震动
    public static void stopVibrate() {
        isLongVibrate = false;
        Vibrator vibrator = getVibrator();
        vibrator.cancel();
    }

}
