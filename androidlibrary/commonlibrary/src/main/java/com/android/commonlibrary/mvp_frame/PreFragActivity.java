package com.android.commonlibrary.mvp_frame;

import androidx.annotation.Nullable;
import com.android.commonlibrary.activity.AppFragActivity;

/**
 * MVP架构Activity基类(Activity中含Fragment加载时使用)
 **/
public abstract class PreFragActivity<T extends PrePresenter> extends AppFragActivity {

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
