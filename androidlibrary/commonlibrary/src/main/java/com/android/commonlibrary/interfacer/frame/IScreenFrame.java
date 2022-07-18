package com.android.commonlibrary.interfacer.frame;

import com.android.commonlibrary.interfacer.base.IFrame;

/**
 * Title: 屏幕适配框架顶级接口
 *
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public interface IScreenFrame extends IFrame {

    /**加载**/
    void attachScreen();

    /**取消**/
    void detachScreen();

}
