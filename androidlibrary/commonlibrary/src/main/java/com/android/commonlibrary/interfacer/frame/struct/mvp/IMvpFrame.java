package com.android.commonlibrary.interfacer.frame.struct.mvp;

import com.android.commonlibrary.interfacer.base.IFrame;

/**
 * Title: mvp项目结构框架顶级接口
 *
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public interface IMvpFrame extends IFrame {

    /**创建**/
    void attachStruct();

    /**销毁**/
    void detachStruct();

}
