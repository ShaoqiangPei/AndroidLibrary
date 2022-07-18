package com.android.commonlibrary.dialog_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import dmax.dialog.SpotsDialog;

/**
 * Created by Admin on 2017/5/16.
 * 加载网络dialog
 * 需要引用：compile 'com.github.d-max:spots-dialog:0.7@aar'
 *
 * github地址：https://github.com/d-max/spots-dialog
 */
public class LoadingDialog {

    private AlertDialog mDialog;
    private String mTitle;
    private boolean mDismiss;//是否使用

    private LoadingDialog() {}

    private static class DbHolder {
        private static LoadingDialog instance = new LoadingDialog();
    }

    public static LoadingDialog getInstance() {
        return DbHolder.instance;
    }

    /**显示网络加载框**/
    public void showLoading(Context context) {
        if (mDismiss) {
            //设置mDismiss=true时,表示不弹出网络加载loading
            return;
        }
        if (mDialog == null) {
            mDialog = new SpotsDialog(context, getTitle());
        }
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        //显示dialog
        mDialog.show();
    }

    /**隐藏网络加载框**/
    public void hideLoading(){
        if(mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
        setTitle(null);
        mDismiss=false;
    }

    /**
     * 设置是否显示网络加载框
     * @param dismiss true：不显示网络加载。false：显示网络加载。默认为false，即显示加载loading
     * @return
     */
    public LoadingDialog setDismiss(boolean dismiss){
        this.mDismiss=dismiss;
        return this;
    }

    /**设置loading提示语**/
    public LoadingDialog setTitle(String title){
        mTitle=title;
        return this;
    }

    /**
     * 判断dialog是否该show
     * @param dialog
     * @param context 为 activity 上下文
     * @return
     */
    public boolean shouldShow(Dialog dialog, Context context){
        try {
            Activity activity=getActivity(context);
            if (activity!=null&&!activity.isFinishing()&&!activity.isDestroyed()&&dialog!=null&&!dialog.isShowing()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getTitle(){
        if(TextUtils.isEmpty(mTitle)){
            mTitle="正在加载...";
        }
        return mTitle;
    }

    private Activity getActivity(Context context) {
        Activity activity = null;
        try {
            if (context != null) {
                activity = (Activity) context;
            }
        } catch (Exception e) {
            e.printStackTrace();
            activity = null;
        }
        return activity;
    }


}
