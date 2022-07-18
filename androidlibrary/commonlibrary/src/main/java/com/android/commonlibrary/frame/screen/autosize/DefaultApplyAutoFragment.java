package com.android.commonlibrary.frame.screen.autosize;

import com.android.commonlibrary.frame.af.AppFragment;
import com.android.commonlibrary.ui.fragment.SuperFragment;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2022/7/12
 */
public abstract class ApplyAutoFragment extends AppFragment implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        //true表示以最小屏幕宽度适配,false表示以高度适配
        return true;
    }

    @Override
    public float getSizeInDp() {
        //如果要适配其他屏幕尺寸dp,可以重写此类返回具体sizeInDp值,默认为 0 表示采用 mainfast.xml全局适配
        return 0;
    }


}

