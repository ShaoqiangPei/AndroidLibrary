package com.android.commonlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.commonlibrary.util.StatusBarUtil;
import com.android.commonlibrary.util.StringUtil;
import com.android.commonlibrary.util.ToastUtil;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:App基类帮助类
 *
 * Author:pei
 * Date: 2019/3/20
 */
public class AppHelper {

    private AppHelper(){}

    private static class Holder {
        private static AppHelper instance = new AppHelper();
    }

    public static AppHelper getInstance() {
        return Holder.instance;
    }

    /**设置状态栏**/
    public void setStatusBar(Context context){
        //设置状态栏背景透明
        StatusBarUtil.immersive((Activity) context);
        //设置状态栏文字变黑
        StatusBarUtil.darkMode((Activity) context);
    }

    /**获取控件值**/
    public String getTextOfView(TextView textView) {
        if(textView!=null){
            return textView.getText().toString();
        }
        return "";
    }

    /**获取非空字符串**/
    public String getNotEmptyString(String str) {
        if(StringUtil.isNotEmpty(str)){
            return str;
        }
        return "";
    }

    /**长吐司**/
    public void showToast(String msg) {
        if(StringUtil.isNotEmpty(msg)){
            ToastUtil.show(msg);
        }
    }

    /**短吐司**/
    public void showShortToast(String msg) {
        if(StringUtil.isNotEmpty(msg)){
            ToastUtil.shortShow(msg);
        }
    }

    /***
     * 用于初始化控件的
     * @param obj 为AppCompatActivity或ViewGroup或View的实例
     * @param rId 控件id
     * @return
     */
    public <T> T getView(Object obj,int rId) {
        if(obj==null){
            throw new NullPointerException("===obj不能为空,且只能传AppCompatActivity,ViewGroup,View其中之一的实例======");
        }
        View view=null;
        if (obj instanceof AppCompatActivity) {
            view = ((AppCompatActivity) obj).findViewById(rId);
        } else if (obj instanceof View) {
            //为自定义view类控件或Fragment时,需要根据xml的id获取对应布局
            //然后将此view作为obj参数传进来用于获取控件对象
            view = ((View) obj).findViewById(rId);
        } else if (obj instanceof ViewGroup) {
            view = ((ViewGroup) obj).findViewById(rId);
        }else{
            throw new ClassCastException("===obj参数类型错误======");
        }
        return (T) view;
    }

    /**
     * 通过字符串"R.id.btn"获取控件对象
     * @param rId
     * @param idType
     * @param activity
     * eg : Button btn=getView("R.id.btn","id",context);
     *
     * 若获取图片id,你也可以这样:
     * int drawableId=activity.getResources().getIdentifier("R.drawable.ic_launch", "drawable", ctx.getPackageName());
     *
     * @return
     */
    public View getView(String rId,String idType,AppCompatActivity activity){
        View view=null;
        Context ctx=activity.getBaseContext();
        int resId = activity.getResources().getIdentifier(rId, idType, ctx.getPackageName());
        view = activity.findViewById(resId);
        return view;
    }
}