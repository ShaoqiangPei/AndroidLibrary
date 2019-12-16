package com.android.commonlibrary.mvp_frame;

import androidx.annotation.Nullable;

import com.android.commonlibrary.fragment.AppFragment;


/**
 * MVP架构 Fragment基类
 **/
public abstract class PreFragment<T extends PrePresenter> extends AppFragment {

    protected T mPresenter;

    @Override
    protected void loadMVP() {
        super.loadMVP();

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    @Nullable
    protected abstract T getPresenter();

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
