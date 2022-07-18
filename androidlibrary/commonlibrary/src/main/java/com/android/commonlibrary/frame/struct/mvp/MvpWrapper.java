package com.android.commonlibrary.frame.struct.mvp;

import com.android.commonlibrary.interfacer.frame.struct.mvp.IMvpFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;

/**
 * Title: Mvp框架
 *
 * description:
 * autor:pei
 * created on 2022/7/13
 */
public class MvpWrapper implements IMvpFrame {

    private PrePresenter mPresenter;

    public MvpWrapper(PrePresenter presenter){
        this.mPresenter = presenter;
    }

    @Override
    public void attachStruct() {
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    @Override
    public void detachStruct() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
