package com.android.commonlibrary.mvp_frame;


import androidx.annotation.Nullable;

import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.mvp_frame.pre_interfacer.IPreActivity;
import com.android.commonlibrary.mvp_frame.pre_interfacer.PrePresenter;


/**
 * MVP架构 Activity基类
 **/
public abstract class PreActivity extends AppActivity implements IPreActivity {

    protected PrePresenter mPresenter;

    @Override
    protected void loadMVP() {
        super.loadMVP();

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

}
