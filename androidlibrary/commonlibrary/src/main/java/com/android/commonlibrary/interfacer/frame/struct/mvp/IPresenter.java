package com.android.commonlibrary.interfacer.mvp;

import com.android.commonlibrary.interfacer.frame.struct.mvp.IPrePresenter;

/**
 * Title:获取Presenter对象的接口
 * description:
 * autor:pei
 * created on 2020/4/28
 */
public interface IPreActivity {

//      <T extends PrePresenter>T getPresenter();
      IPrePresenter getPresenter();
}
