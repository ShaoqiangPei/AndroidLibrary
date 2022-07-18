package com.android.commonlibrary.frame.actfrag;

import com.android.commonlibrary.frame.struct.mvp.MvpWrapper;
import com.android.commonlibrary.interfacer.base.IFrame;
import com.android.commonlibrary.interfacer.frame.struct.mvp.IPresenter;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;
import com.android.commonlibrary.ui.fragment.SuperFragment;

/**
 * 具备MVP架构 Fragment基类
 **/
public abstract class AppFragment extends SuperFragment implements IPresenter {

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
