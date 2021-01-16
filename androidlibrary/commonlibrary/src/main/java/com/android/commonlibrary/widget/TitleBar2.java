package com.android.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import com.android.commonlibrary.R;
import com.android.commonlibrary.util.ScreenUtil;
import com.android.commonlibrary.util.view.TextViewUtil;
import com.android.commonlibrary.util.view.ViewUtil;

/**
 * Description:自定义标题栏
 *
 * Author:pei
 * Date: 2019/3/21
 */
public class TitleBar2 extends ConstraintLayout {

    private View mLayoutView;
    private Context mContext;

    private ImageView mImvLeft;
    private AppCompatTextView mTvLeft;
    private AppCompatTextView mTvTitle;
    private AppCompatTextView mTvRight;
    private ImageView mImvRight;

    public TitleBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutView = LayoutInflater.from(context).inflate(R.layout.title_bar_layout2, this);
        this.mContext = context;

        //初始化控件
        initView();
        //初始化资源
        initAttrs();
        //初始化让所有控件都不可见
        initViewGone();
    }

    /**用于初始化控件的**/
    private <T> T getView(int rId) {
        View view = TitleBar2.this.findViewById(rId);
        return (T) view;
    }

    /**初始化控件**/
    private void initView(){
        mImvLeft=getView(R.id.imv_left);
        mTvLeft=getView(R.id.tv_left);
        mTvTitle=getView(R.id.tv_title);
        mTvRight=getView(R.id.tv_right);
        mImvRight=getView(R.id.imv_right);
    }

    /**初始化资源**/
    private void initAttrs(){

    }

    /**初始化让所有控件都不可见**/
    private void initViewGone(){
        mImvLeft.setVisibility(View.GONE);
        mTvLeft.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
        mTvRight.setVisibility(View.GONE);
        mImvRight.setVisibility(View.GONE);
    }

    public ImageView getImvLeft(){
        return mImvLeft;
    }

    public AppCompatTextView getTvLeft(){
        return mTvLeft;
    }

    public AppCompatTextView getTvTitle() {
        return mTvTitle;
    }

    public AppCompatTextView getTvRight() {
        return mTvRight;
    }

    public ImageView getImvRight(){
        return mImvRight;
    }

    /**点击事件**/
    private void setOnClickListener(View view,View.OnClickListener listener){
        if(view.getVisibility()==View.VISIBLE){
            view.setOnClickListener(listener);
        }
    }

    /**左边点击事件**/
    public void setOnClickLeft(View.OnClickListener listener){
        setOnClickListener(mImvLeft,listener);
        setOnClickListener(mTvLeft,listener);
    }

    /**中间点击事件**/
    public void setOnClickCenter(View.OnClickListener listener){
        setOnClickListener(mTvTitle,listener);
    }

    /**右边点击事件**/
    public void setOnClickRight(View.OnClickListener listener){
        setOnClickListener(mTvRight,listener);
        setOnClickListener(mImvRight,listener);
    }

    /**
     * 设置文字大小
     * @param textView TextView控件
     * @param sp 文字大小，已做sp2px处理,直接传sp值
     */
    public void setTextSize(TextView textView,float sp){
        TextViewUtil.setTextSzieBySp(textView,sp);
    }

    /***
     * 设置文字大小缩放配置(兼容不同屏幕分辨率时调用)
     *
     * @param textView
     * @param minTextSize  最小文字大小
     * @param maxTextSize  最大文字大小
     * @param sizeStep  文字大小缩放粒度(一般设置为 1)
     */
    public void setTextSizeAutoConfig(AppCompatTextView textView,int minTextSize,int maxTextSize,int sizeStep){
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,minTextSize,maxTextSize,sizeStep, TypedValue.COMPLEX_UNIT_SP);
    }

    /**
     * 设置文字颜色
     * @param textview TextView控件
     * @param color 直接传色值资源,如：R.color.color_000000
     */
    public void setTextColor(TextView textview,int color){
        textview.setTextColor(ViewUtil.getColor(mContext,color));
    }

    /***
     * 设置margins
     *
     * 已做 dp2px处理,直接传dp值
     */
    public void setMargins(View view,int left,int top,int right,int bottom){
        LayoutParams params= (LayoutParams) view.getLayoutParams();
        MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof MarginLayoutParams) {
            marginParams = (MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            //基于View本身原有的布局参数对象
            marginParams = new MarginLayoutParams(params);
        }

        left= ScreenUtil.dp2px(left,mContext);
        top= ScreenUtil.dp2px(top,mContext);
        right= ScreenUtil.dp2px(right,mContext);
        bottom= ScreenUtil.dp2px(bottom,mContext);

        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(marginParams);
    }

    /***
     * 设置paddings
     *
     * 已做 dp2px处理,直接传dp值
     */
    public void setPaddings(View view,int left,int top,int right,int bottom){
        left= ScreenUtil.dp2px(left,mContext);
        top= ScreenUtil.dp2px(top,mContext);
        right= ScreenUtil.dp2px(right,mContext);
        bottom= ScreenUtil.dp2px(bottom,mContext);

        view.setPadding(left,top,right,bottom);
    }

}