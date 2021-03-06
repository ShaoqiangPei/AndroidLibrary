package com.android.commonlibrary.mvp_frame;

import com.android.commonlibrary.activity.SuperFragActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

/**
 * 具备MVP架构的Activity基类(具备加载Fragment的能力)
 **/
public abstract class AppActivity extends SuperFragActivity implements IPreActivity {

    protected PrePresenter mPresenter;

    @Override
    public void loadMVP() {
        super.loadMVP();

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    /***
     * 若要使用mvp架构,需要在子类中重写此方法并返回具体的 PrePresenter
     * @return
     */
    @Override
    public PrePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

}
