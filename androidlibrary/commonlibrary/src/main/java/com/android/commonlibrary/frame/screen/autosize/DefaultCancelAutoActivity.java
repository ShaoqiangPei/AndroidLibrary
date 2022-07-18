package com.android.commonlibrary.frame.screen.autosize;

import com.android.commonlibrary.frame.af.AppActivity;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Title: 取消屏幕适配Activity示例
 *
 * description: 使用时将此类拷贝到你的项目中，将 DefaultCancelAutoActivity名称改为 CancelAutoActivity
 *              然后把继承的 AppActivity 改为自己项目中的 基类Activity(如BaseActivity)
 *              创建新界面时(如TestActivity)去继承 CancelAutoActivity 以实现取消activity的屏幕适配
 *              然后要改基类特性时,去你自己项目中的基类Activity(如BaseActivity)中去修改
 *              不要在 CancelAutoActivity中去改基类特性
 *
 * autor:pei
 * created on 2022/7/12
 */
public abstract class DefaultCancelAutoActivity extends AppActivity implements CancelAdapt {


}

