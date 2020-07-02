package com.android.commonlibrary.mvp_frame;

import com.android.commonlibrary.activity.AppFragActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

/**
 * MVP架构Activity基类(Activity中含Fragment加载时使用)
 **/
public abstract class PreFragActivity extends AppFragActivity implements IPreActivity {

    protected PrePresenter mPresenter;

    @Override
    public void loadMVP() {
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
