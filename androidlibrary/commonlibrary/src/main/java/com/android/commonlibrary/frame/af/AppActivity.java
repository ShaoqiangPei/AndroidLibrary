package com.android.commonlibrary.frame.actfrag;

import com.android.commonlibrary.frame.struct.mvp.MvpWrapper;
import com.android.commonlibrary.interfacer.base.IFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.IPresenter;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;
import com.android.commonlibrary.ui.activity.SuperFragActivity;

/**
 * Title: 具备MVP架构的Activity基类(具备加载Fragment的能力)
 * description:
 * autor:pei
 * created on 2022/7/13
 */
public abstract class AppActivity extends SuperFragActivity implements IPresenter {

    //控件初始化框架,如bund
    protected IFrame mViewFrame;
    //项目框架,如 mvp，mvvm等
    protected IFrame mStructFrame;

    @Override
    public void attach() {
        super.attach();

        mStructFrame=getFrame();
        mStructFrame.attach();
    }

    @Override
    public void distach() {
        super.distach();
        mStructFrame.distach();
    }

    private IFrame getFrame(){
        return new MvpWrapper(getPresenter());
    }


    /***
     * 若要使用mvp架构,需要在子类中重写此方法并返回具体的 PrePresenter
     * @return
     */
    @Override
    public PrePresenter getPresenter() {
        return null;
    }


}
