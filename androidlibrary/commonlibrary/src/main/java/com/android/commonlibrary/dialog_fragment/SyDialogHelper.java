package com.android.commonlibrary.dialog_fragment;

import android.content.Context;
import android.text.SpannableString;
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
    private static double mScaleWidth=0; //全局dialog宽度尺寸比
    private static double mScaleHeight=0; //全局dialog高度尺寸比
    private static int mConfirmTextColor=RID;//全局dialog默认确认按钮文字颜色
    private static int mCancelTextColor=RID;//全局dialog默认取消按钮文字颜色

    /***
     * 设置通用dialog—SyDialogFragment全局尺寸
     *
     * 注: 此方法为可选,若有需要可在Application中设置整个app中
     *    由SyDialogHelper弹出dialog的宽高尺寸
     *
     * @param scaleWidth 屏幕宽度比,如 0.8d 表示宽度为屏幕宽度的0.8
     * @param scaleHeight 屏幕高度比,如 0.28d 表示宽度为屏幕宽度的0.28
     */
    public static void setDialogSize(double scaleWidth,double scaleHeight){
        mScaleWidth=scaleWidth;
        mScaleHeight=scaleHeight;
    }

    /***
     * 设置SyDialogHelper弹出dialog的确认按钮颜色
     *
     * 注: 此方法为可选,若有需要可在Application中设置整个app中
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
    public static void showDialogOneBtn(Object tipMsg, String btnText, Context context, View.OnClickListener listener){
        if(mSyDialogFragment==null){
            mSyDialogFragment = getSyDialogFragment(context);
        }
        //提示语设置校验
        if(!effectTipMsg(tipMsg)){
            //setMesssageText(tipMsg)只允许设置非空 String或SpannableString
            return;
        }
        if (mSyDialogFragment.isShowing()) {
            //若已经显示则刷新提示语,不再show了
            mSyDialogFragment.setMesssageText(tipMsg);
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
    public static void showDialogOneBtn(Object tipMsg,Context context,View.OnClickListener listener){
        showDialogOneBtn(tipMsg,"确定",context,listener);
    }

    /**显示一个按钮的提示,无按钮功能，自己写按钮文字**/
    public static void showDialogOneBtn(Object tipMsg,String btnText,Context context){
        showDialogOneBtn(tipMsg,btnText,context,null);
    }

    /**显示一个按钮的提示,无按钮功能，按钮文字为"确定"**/
    public static void showDialogOneBtn(Object tipMsg,Context context){
        showDialogOneBtn(tipMsg,"确定",context);
    }

    /**显示两个按钮的提示dialog(确定，取消按钮文字和功能自定义)**/
    public static void showDialogTwoBtn(Object tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener cancelListener,View.OnClickListener confirmListener){
        if(mSyDialogFragment==null){
            mSyDialogFragment = getSyDialogFragment(context);
        }
        //提示语设置校验
        if(!effectTipMsg(tipMsg)){
            //setMesssageText(tipMsg)只允许设置非空 String或SpannableString
            return;
        }
        if (mSyDialogFragment.isShowing()) {
            //若已经显示则刷新提示语,不再show了
            mSyDialogFragment.setMesssageText(tipMsg);
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
    public static void showDialogTwoBtn(Object tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener listener){
        showDialogTwoBtn(tipMsg, cancelText, confirmText, context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSyDialogFragment.dismiss();
            }
        },listener);
    }

    /**显示两个按钮的提示dialog(确定，取消按钮的文字固定，只需自己写确定功能)**/
    public static void showDialogTwoBtn(Object tipMsg,Context context,View.OnClickListener listener){
        showDialogTwoBtn(tipMsg,"取消","确定",context,listener);
    }

    /**dialog消失**/
    public static void dismiss(){
        if(mSyDialogFragment!=null){
            mSyDialogFragment.dismiss();
        }
    }

    private static SyDialogFragment getSyDialogFragment(Context context){
        SyDialogFragment syDialogFragment= (SyDialogFragment) AppDialogFragment.createFragment(SyDialogFragment.class, context, new AppDialogFragment.OnCreateFragmentListener() {
            @Override
            public Fragment createFragment() {
                return new SyDialogFragment();
            }
        });
        //设置dialog显示尺寸
        syDialogFragment.setDialogSize(mScaleWidth,mScaleHeight);
        //设置按钮颜色
        if(mConfirmTextColor!=RID){
            syDialogFragment.setConfirmTextColor(mConfirmTextColor);
        }
        if(mCancelTextColor!=RID){
            syDialogFragment.setCancelTextColor(mCancelTextColor);
        }
        return syDialogFragment;
    }

    /**是否为有效提示语**/
    private static boolean effectTipMsg(Object tipMessage){
        if(tipMessage==null){
            throw new NullPointerException("===SyDialogHelper的syDialogFragment中设置提示语不能为 null ======");
        }
        if(tipMessage instanceof SpannableString){
            return true;
        }
        if(tipMessage instanceof String){
            return true;
        }else{
            throw new ClassCastException("===SyDialogHelper的syDialogFragment中的提示语设置只接受 String 和 SpannableString 数据类型======");
        }
    }


}
