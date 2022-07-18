package com.androidlibrary.test;

import com.android.commonlibrary.interfacer.base.IFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;

/**
 * Title: Mvp框架
 *
 * description:
 * autor:pei
 * created on 2022/7/13
 */
public class MvpWrapper implements IFrame {

    private PrePresenter mPresenter;

    public MvpWrapper(PrePresenter presenter){
        this.mPresenter = presenter;
    }

    @Override
    public void attach() {
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    @Override
    public void distach() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
