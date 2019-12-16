package com.android.commonlibrary.mvp_frame;


import androidx.annotation.Nullable;

import com.android.commonlibrary.activity.AppActivity;


/**
 * MVP架构 Activity基类
 **/
public abstract class PreActivity<T extends PrePresenter> extends AppActivity {

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
