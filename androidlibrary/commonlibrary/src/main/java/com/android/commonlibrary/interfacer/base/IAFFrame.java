package com.android.commonlibrary.interfacer.base;

/**
 * Title: Activity,fragment加载和注销框架需要实现的接口
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public interface IAFFrame extends IFrame{

    /**加载**/
    void loadFrame();

    /**销毁**/
    void destoryFrame();
}
