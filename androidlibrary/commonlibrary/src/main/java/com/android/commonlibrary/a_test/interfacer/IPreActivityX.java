package com.android.commonlibrary.a_test.interfacer;

import com.android.commonlibrary.mvp_frame.PrePresenter;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public interface IPreActivityX {

        /**
         * 加载mvp框架的时候用，供子类重写，此处不做处理
         **/
        void loadMVP();

        <T extends PrePresenter>T getPresenter();
}
