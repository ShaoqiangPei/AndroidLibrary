package com.android.commonlibrary.mvp_frame;

import com.android.commonlibrary.fragment.AppFragment;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

/**
 * MVP架构 Fragment基类
 **/
public abstract class PreFragment extends AppFragment implements IPreActivity {

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
