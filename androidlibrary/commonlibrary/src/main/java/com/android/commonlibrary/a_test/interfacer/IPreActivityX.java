package com.android.commonlibrary.a_test.interfacer;

import com.android.commonlibrary.mvp_frame.PrePresenter;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public interface IPreActivityX {

        <T extends PrePresenter>T getPresenter();
}
