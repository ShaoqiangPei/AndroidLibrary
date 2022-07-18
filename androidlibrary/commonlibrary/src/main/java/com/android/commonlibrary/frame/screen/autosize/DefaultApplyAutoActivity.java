package com.android.commonlibrary.frame.screen.autosize;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.android.commonlibrary.frame.af.AppActivity;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Title: 屏幕适配Activity示例
 *
 * description: 使用时将此类拷贝到你的项目中，将DefaultApplyAutoActivity名称改为 ApplyAutoActivity
 *              创建新界面时(如TestActivity)去继承 ApplyAutoActivity 以实现activity的屏幕适配
 *              然后要改基类特性时,去你自己项目中的基类Activity(如BaseActivity)中去修改
 *              不要在 ApplyAutoActivity中去改基类特性
 *
 * autor:pei
 * created on 2022/7/12
 */
public abstract class DefaultApplyAutoActivity extends DefaultSuperAutoSizeActivity {

    @Override
    public boolean isBaseOnWidth() {
        //true表示以最小屏幕宽度适配,false表示以高度适配
        return super.isBaseOnWidth();
    }

    @Override
    public float getSizeInDp() {
        //如果要适配其他屏幕尺寸dp,可以重写此类返回具体sizeInDp值,默认为 0 表示采用 mainfast.xml全局适配
        return 0;
    }

}

