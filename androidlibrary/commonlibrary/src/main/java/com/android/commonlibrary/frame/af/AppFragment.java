package com.android.commonlibrary.frame.af;

import com.android.commonlibrary.frame.struct.mvp.MvpWrapper;
import com.android.commonlibrary.interfacer.base.IFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.IMvpFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.IPresenter;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;
import com.android.commonlibrary.ui.fragment.SuperFragment;

/**
 * 具备MVP架构 Fragment基类
 **/
public abstract class AppFragment extends SuperFragment implements IPresenter {

    //项目框架,如 mvp，mvvm等
    protected IFrame mStructFrame;

    @Override
    public void loadFrame() {
        super.loadFrame();
        //加载项目框架
        mStructFrame = getFrame();
        ((IMvpFrame)mStructFrame).attachStruct();
    }

    @Override
    public void destoryFrame() {
        super.destoryFrame();
        //注销项目框架
        ((IMvpFrame)mStructFrame).detachStruct();
    }

    private IFrame getFrame() {
        //采用mvp项目架构
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
