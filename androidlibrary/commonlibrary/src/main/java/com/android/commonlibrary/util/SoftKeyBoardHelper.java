package com.android.commonlibrary.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Title:监听软键盘 显示/隐藏 工具类
 * description:
 * autor:pei
 * created on 2019/12/12
 */
public class SoftKeyBoardHelper {

    private View mRootView;//activity的根视图
    private int mRootViewVisibleHeight;//纪录根视图的显示高度
    private OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener;

    public SoftKeyBoardHelper(Activity activity) {
        //获取activity的根视图
        mRootView = activity.getWindow().getDecorView();
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                mRootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                System.out.println(""+visibleHeight);
                if (mRootViewVisibleHeight == 0) {
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (mRootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过300，可以看作软键盘显示了，该数值可根据需要自行调整
                if (mRootViewVisibleHeight - visibleHeight > 200) {
                    if (mOnSoftKeyBoardChangeListener != null) {
                        mOnSoftKeyBoardChangeListener.keyBoardShow(mRootViewVisibleHeight - visibleHeight);
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过300，可以看作软键盘隐藏了，该数值可根据需要自行调整
                if (visibleHeight - mRootViewVisibleHeight > 200) {
                    if (mOnSoftKeyBoardChangeListener != null) {
                        mOnSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - mRootViewVisibleHeight);
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener listener) {
        this.mOnSoftKeyBoardChangeListener = listener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);
        void keyBoardHide(int height);
    }

    /**监听软键盘的 显示/隐藏 **/
    public static void setOnListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        SoftKeyBoardHelper helper = new SoftKeyBoardHelper(activity);
        helper.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }

}
