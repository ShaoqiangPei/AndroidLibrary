package com.android.commonlibrary.frame.screen.autosize;

import com.android.commonlibrary.frame.af.AppFragment;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Title: 取消屏幕适配Fragment示例
 *
 * description: 使用时将此类拷贝到你的项目中，将DefaultCancelAutoFragment名称改为 CancelAutoFragment
 *              然后把继承的 AppFragment 改为自己项目中的 基类Fragment(如Fragment)
 *              创建新界面时(如TestFragment)去继承 CancelAutoFragment 以实现取消Fragment的屏幕适配
 *              然后要改基类特性时,去你自己项目中的基类Fragment(如BaseFragment)中去修改
 *              不要在 CancelAutoFragment中去改基类特性
 *
 * autor:pei
 * created on 2022/7/12
 */
public abstract class DefaultCancelAutoFragment extends AppFragment implements CancelAdapt {


}

