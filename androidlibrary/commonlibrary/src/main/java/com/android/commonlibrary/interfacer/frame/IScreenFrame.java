package com.android.commonlibrary.interfacer.frame;

import com.android.commonlibrary.interfacer.base.IFrame;

/**
 * Title: 控件初始化框架顶级接口,如 butterknife 等
 *
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public interface IViewFrame extends IFrame {

    /**创建**/
    void attachView();

    /**销毁**/
    void detachView();

}
