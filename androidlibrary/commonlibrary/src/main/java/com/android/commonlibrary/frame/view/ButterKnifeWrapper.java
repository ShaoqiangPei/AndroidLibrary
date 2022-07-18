package com.android.commonlibrary.frame.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.commonlibrary.interfacer.base.IAF;
import com.android.commonlibrary.interfacer.base.IFrame;
import com.android.commonlibrary.interfacer.frame.IViewFrame;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Title: Unbinder 初始化控件 框架
 *
 * description:
 * autor:pei
 * created on 2022/7/14
 */
public class UnbinderWrapper implements IViewFrame {

    private Unbinder mUnbinder;
    private Object mTarget;
    private View mView;

    /**Activity初始化时调用**/
    public UnbinderWrapper(@NonNull Object target){
        this.mTarget=target;
    }

    /**Fragment初始化时调用**/
    public UnbinderWrapper(@NonNull Object target,@NonNull View view){
        this.mTarget=target;
        this.mView=view;
    }

    @Override
    public void attachView() {
        if (mTarget != null && mView != null && mTarget instanceof IAF) {
            if (mTarget instanceof Activity) {
                mUnbinder = ButterKnife.bind((Activity) mTarget); //绑定Activity
            } else if (mTarget instanceof Fragment) {
                mUnbinder = ButterKnife.bind((Fragment) mTarget, mView); //绑定framgent
            }
        }
    }

    @Override
    public void detachView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
