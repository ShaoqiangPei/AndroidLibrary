package com.android.commonlibrary.frame.screen.autosize;

import com.android.commonlibrary.frame.af.AppFragment;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Title: 屏幕适配Fragment示例
 *
 * description: 使用时将此类拷贝到你的项目中，将DefaultApplyAutoFragment名称改为 ApplyAutoFragment
 *              然后把继承的 AppFragment 改为自己项目中的 基类Fragment(如Fragment)
 *              创建新界面时(如TestFragment)去继承 ApplyAutoFragment 以实现Fragment的屏幕适配
 *              然后要改基类特性时,去你自己项目中的基类Fragment(如BaseFragment)中去修改
 *              不要在 ApplyAutoFragment中去改基类特性
 *
 * autor:pei
 * created on 2022/7/12
 */
public abstract class DefaultApplyAutoFragment extends AppFragment implements CustomAdapt {

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

