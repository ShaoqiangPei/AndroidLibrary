package com.android.commonlibrary.frame.screen.autosize;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.android.commonlibrary.frame.af.AppActivity;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.utils.ScreenUtils;

/**
 * Title: 处理适配中出现不生效问题
 *
 * description: 使用时将此类拷贝到你的项目中，将DefaultSuperAutoSizeActivity名称改为 SuperAutoSizeActivity
 *              然后把继承的 AppActivity 改为自己项目中的 基类Activity(如BaseActivity)
 *
 * autor:pei
 * created on 2022/7/14
 */
public abstract class DefaultSuperAutoSizeActivity extends AppActivity implements CustomAdapt {

    private float mDensity;

    @Override
    public boolean isBaseOnWidth() {
        //true表示以最小屏幕宽度适配,false表示以高度适配
        return true;
    }

    @Override
    public float getSizeInDp() {
        //如果要适配其他屏幕尺寸dp,可以重写此类返回具体sizeInDp值,默认为 0 表示采用 mainfast.xml全局适配
        int sizeInDp;
        if (AutoSizeConfig.getInstance().getScreenWidth() > AutoSizeConfig.getInstance().getScreenHeight()) {
            sizeInDp = AutoSizeConfig.getInstance().getDesignWidthInDp();
        } else {
            sizeInDp = AutoSizeConfig.getInstance().getDesignHeightInDp();
        }
        return sizeInDp;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setScreenWidthHeight();
        setDensityByAutoSize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScreenWidthHeight();
        setDensityByAutoSize();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setScreenWidthHeight();
        setDensityByAutoSize();
    }

    @Override
    public Resources getResources() {
        setDensityByAutoSize();
        return super.getResources();
    }

    public void setScreenWidthHeight() {
        int[] screenSize = ScreenUtils.getScreenSize(getApplicationContext());
        int width = screenSize[0];
        int height = screenSize[1];
        AutoSizeConfig.getInstance().setScreenWidth(width);
        AutoSizeConfig.getInstance().setScreenHeight(height);
    }

    private void setDensityByAutoSize() {
        float tempDensity = super.getResources().getDisplayMetrics().density;
        if (mDensity != tempDensity) {
            setScreenWidthHeight();
            try {
                AutoSizeCompat.autoConvertDensityOfCustomAdapt(super.getResources(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDensity = super.getResources().getDisplayMetrics().density;
        }
    }
}

