package com.android.commonlibrary.activity_proxy;

import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreLoad;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

///**
// * Title:MVP架构 Activity基类代理
// * description:
// * autor:pei
// * created on 2020/7/2
// */
//public class PreActivityProxy implements IPreLoad{
//
//    private IPreLoad mIPreLoad;
//    protected PrePresenter mPresenter;
//
//    public PreActivityProxy(IPreLoad iPreLoad,IPreActivity iPreActivity){
//        this.mIPreLoad=iPreLoad;
//        this.mPresenter=iPreActivity.getPresenter();
//    }
//
//    @Override
//    public void loadMVP() {
//        mIPreLoad.loadMVP();
//
//        if (mPresenter != null) {
//            mPresenter.attachView();
//        }
//    }
//
//    public void onDestroy() {
//        if (mPresenter != null) {
//            mPresenter.detachView();
//        }
//    }
//
//}
