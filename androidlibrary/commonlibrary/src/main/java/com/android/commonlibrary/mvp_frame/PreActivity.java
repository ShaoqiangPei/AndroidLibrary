package com.android.commonlibrary.mvp_frame;

import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

/**
 * MVP架构 Activity基类
 **/
public abstract class PreActivity extends AppActivity implements IPreActivity {

    protected PrePresenter mPresenter;

    @Override
    public void loadMVP() {
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

//public abstract class PreActivity extends AppActivity implements IPreActivity{
//
//    private PreActivityProxy mPreActivityProxy;
//
//    @Override
//    public void loadMVP() {
//        mPreActivityProxy=new PreActivityProxy(this,this);
//        mPreActivityProxy.loadMVP();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        mPreActivityProxy.onDestroy();
//        super.onDestroy();
//    }
//}
