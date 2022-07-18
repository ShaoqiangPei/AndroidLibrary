package com.android.commonlibrary.interfacer.frame;

import com.android.commonlibrary.interfacer.base.IFrame;

/**
 * Title: 项目结构框架顶级接口,如mvp,mvvm等
 *
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public interface IStructFrame extends IFrame {

    /**创建**/
    void attachStruct();

    /**销毁**/
    void detachStruct();

}
