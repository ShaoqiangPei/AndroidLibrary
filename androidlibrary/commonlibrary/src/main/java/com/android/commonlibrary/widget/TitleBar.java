package com.android.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.commonlibrary.R;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Description:标题栏
 *
 * Author:pei
 * Date: 2019/3/21
 */
public class TitleBar extends ConstraintLayout {

    private View mLayoutView;
    private Context mContext;

    private ImageTextView mTvLeft;
    private ImageTextView mTvRight;
    private TextView mTvTitle;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutView = LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this);
        this.mContext = context;

        initView();
        initViewGone();
    }

    /**用于初始化控件的**/
    private <T> T getView(int rId) {
        View view = TitleBar.this.findViewById(rId);
        return (T) view;
    }

    private void initView(){
        mTvLeft=getView(R.id.tv_left);
        mTvRight=getView(R.id.tv_right);
        mTvTitle=getView(R.id.tv_title);
    }

    private void initViewGone(){
        mTvLeft.setVisibility(View.GONE);
        mTvRight.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
    }



    public ImageTextView getTvLeft() {
        return mTvLeft;
    }

    public ImageTextView getTvRight() {
        return mTvRight;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    /**设置左边文本和图片间距**/
    public void setLeftDrawablePadding(int padding){
        mTvLeft.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        mTvLeft.setCompoundDrawablePadding(ScreenUtil.dp2px(padding,mContext));
    }

    /**设置右边返回键文本和图片间距**/
    public void setRightDrawablePadding(int padding){
        mTvRight.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        mTvRight.setCompoundDrawablePadding(ScreenUtil.dp2px(padding,mContext));
    }

    /**设置margins**/
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

    /**设置paddings**/
    public void setPaddings(View view,int left,int top,int right,int bottom){

        left= ScreenUtil.dp2px(left,mContext);
        top= ScreenUtil.dp2px(top,mContext);
        right= ScreenUtil.dp2px(right,mContext);
        bottom= ScreenUtil.dp2px(bottom,mContext);

        view.setPadding(left,top,right,bottom);
    }

}