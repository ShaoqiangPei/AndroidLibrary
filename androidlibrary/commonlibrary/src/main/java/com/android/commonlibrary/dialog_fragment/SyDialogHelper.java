package com.android.commonlibrary.dialog_fragment;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Title:SyDialogFragment帮助类
 * description:
 * autor:pei
 * created on 2019/9/6
 */
public class SyDialogHelper {

    private static final int RID=-1;//默认背景资源id

    private static SyDialogFragment mSyDialogFragment;
    private static int mConfirmTextColor=RID;//默认确认按钮文字颜色
    private static int mCancelTextColor=RID;//默认取消按钮文字颜色

    /***
     * 设置SyDialogHelper弹出dialog的确认按钮颜色
     *
     * 注: 此方法为可选，若有需要可在Application中设置整个app中
     *    由SyDialogHelper弹出dialog的确认按钮的颜色
     *
     * @param confirmTextColor：R.color.red
     */
    public static void setConfirmTextColor(int confirmTextColor){
        mConfirmTextColor=confirmTextColor;
    }

    /***
     * 设置SyDialogHelper弹出dialog的取消按钮颜色
     *
     * 注: 此方法为可选，若有需要可在Application中设置整个app中
     *    由SyDialogHelper弹出dialog的确认按钮的颜色
     *
     * @param cancelTextColor：R.color.red
     */
    public static void setCancelTextColor(int cancelTextColor){
        mCancelTextColor= cancelTextColor;
    }

    /**显示一个按钮的提示dialog(自己写按钮文字和功能)**/
    public static void showDialogOneBtn(String tipMsg, String btnText, Context context, View.OnClickListener listener){
        if(mSyDialogFragment==null){
            mSyDialogFragment = getSyDialogFragment(context);
        }
        if (mSyDialogFragment.isShowing()) {
            //若已经显示则不再show了
            return;
        }
        mSyDialogFragment.setTitleText("提示")
                .setMesssageText(tipMsg)
                .setConfirmBtn(false)
                .setCancelBtn(btnText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSyDialogFragment.dismiss();
                        if(listener!=null) {
                            listener.onClick(v);
                        }
                    }
                })
                .setUIShadow(true)
                .setCancel(false)
                .setCancelOnTouchOutside(false)
                .showDialog(((AppCompatActivity) context).getSupportFragmentManager());
    }

    /**显示一个按钮的提示dialog(默认按钮文字为"确定",自己写按钮功能)**/
    public static void showDialogOneBtn(String tipMsg,Context context,View.OnClickListener listener){
        showDialogOneBtn(tipMsg,"确定",context,listener);
    }

    /**显示一个按钮的提示,无按钮功能，自己写按钮文字**/
    public static void showDialogOneBtn(String tipMsg,String btnText,Context context){
        showDialogOneBtn(tipMsg,btnText,context,null);
    }

    /**显示一个按钮的提示,无按钮功能，按钮文字为"确定"**/
    public static void showDialogOneBtn(String tipMsg,Context context){
        showDialogOneBtn(tipMsg,"确定",context);
    }

    /**显示两个按钮的提示dialog(确定，取消按钮文字和功能自定义)**/
    public static void showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener cancelListener,View.OnClickListener confirmListener){
        if(mSyDialogFragment==null){
            mSyDialogFragment = getSyDialogFragment(context);
        }
        if (mSyDialogFragment.isShowing()) {
            //若已经显示则不再show了
            return;
        }
        mSyDialogFragment.setTitleText("提示")
                .setMesssageText(tipMsg)
                .setCancelBtn(cancelText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSyDialogFragment.dismiss();
                        cancelListener.onClick(v);
                    }
                })
                .setConfirmBtn(confirmText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSyDialogFragment.dismiss();
                        confirmListener.onClick(v);
                    }
                })
                .setUIShadow(true)
                .setCancel(false)
                .setCancelOnTouchOutside(false)
                .showDialog(((AppCompatActivity) context).getSupportFragmentManager());
    }


    /**显示两个按钮的提示dialog(确定，取消按钮文字自定义，点击取消按钮dialog消失)**/
    public static void showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener listener){
        showDialogTwoBtn(tipMsg, cancelText, confirmText, context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSyDialogFragment.dismiss();
            }
        },listener);
    }

    /**显示两个按钮的提示dialog(确定，取消按钮的文字固定，只需自己写确定功能)**/
    public static void showDialogTwoBtn(String tipMsg,Context context,View.OnClickListener listener){
        showDialogTwoBtn(tipMsg,"取消","确定",context,listener);
    }

    private static SyDialogFragment getSyDialogFragment(Context context){
        SyDialogFragment syDialogFragment= (SyDialogFragment) AppDialogFragment.createFragment(SyDialogFragment.class, context, new AppDialogFragment.OnCreateFragmentListener() {
            @Override
            public Fragment createFragment() {
                return new SyDialogFragment();
            }
        });
        if(mConfirmTextColor!=RID){
            syDialogFragment.setConfirmTextColor(mConfirmTextColor);
        }
        if(mCancelTextColor!=RID){
            syDialogFragment.setCancelTextColor(mCancelTextColor);
        }
        return syDialogFragment;
    }


}
