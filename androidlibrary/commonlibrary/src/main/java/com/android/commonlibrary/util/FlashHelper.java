package com.android.commonlibrary.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Title:控件闪烁帮助类
 * Description:
 * <p>
 * Created by pei
 * Date: 2018/4/22
 */
public class FlashHelper {

    private long mFlashTime=300;//闪烁默认时间间隔(毫秒)

    private FlashHelper() {}

    private static class Holder {
        private static FlashHelper instance = new FlashHelper();
    }

    public static FlashHelper getInstance() {
        return Holder.instance;
    }

    /**设置闪烁时间间隔**/
    public FlashHelper setFlashTime(long flashTime){
        this.mFlashTime=flashTime;
        return FlashHelper.this;
    }

    /**开启View闪烁效果**/
    public void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(mFlashTime);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**取消View闪烁效果**/
    public void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }

}
