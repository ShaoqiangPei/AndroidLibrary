package com.android.commonlibrary.frame.screen.autosize;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.android.commonlibrary.frame.af.AppActivity;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2022/7/12
 */
public abstract class ApplyAutoActivity extends AppActivity implements CustomAdapt {

    private AutoAdapterHelper mAutoAdapterHelper=new AutoAdapterHelper();

    @Override
    public boolean isBaseOnWidth() {
        //true表示以最小屏幕宽度适配,false表示以高度适配
        return true;
    }

    @Override
    public float getSizeInDp() {
        //如果要适配其他屏幕尺寸dp,可以重写此类返回具体sizeInDp值,默认为 0 表示采用 mainfast.xml全局适配
        return mAutoAdapterHelper.getSizeInDp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mAutoAdapterHelper.onConfigurationChanged(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoAdapterHelper.onResume(this,this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAutoAdapterHelper.onRestart(this,this);
    }

    @Override
    public Resources getResources() {
        mAutoAdapterHelper.getResources(this,this);
        return super.getResources();
    }


}

