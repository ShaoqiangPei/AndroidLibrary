//package com.android.commonlibrary.util;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.View;
//
//import q.rorbin.badgeview.Badge;
//import q.rorbin.badgeview.QBadgeView;
//
///**
// * Title:小圆点帮助类
// * Description:
// *   QBadgeView的gitHub地址：https://github.com/qstumn/BadgeView
// * Created by pei
// * Date: 2018/7/1
// */
//public class Badgetor {
//
//    private QBadgeView mQBadgeView;
//
//    private static final int DEFAULT_TEXT_COLOR=Color.parseColor("#ffffff");
//    private static final int DEFAULT_BACKGROUND_COLOR=Color.parseColor("#ff0000");
//    private static final int DEFAULT_STOKE_COLOR=Color.parseColor("#0000ff");
//
//    private float mTextSize=8;//默认文字大小
//    private int mTextColor= DEFAULT_TEXT_COLOR;//默认文字颜色(白色)
//    private boolean mExactMode=false;//默认不显示精确数值
//    private int mGravity= Gravity.END | Gravity.TOP;//设置Badge显示在view的右上角
//    private float mOffset=2;//默认外边距
//    private float mPadding=2;//默认内边距
//    private int mBackgroundColor=DEFAULT_BACKGROUND_COLOR;//默认背景色(红色)
//    private Drawable mBackgroundDrawable;//默认背景图片为null
//    private boolean mShadow=false;//默认不显示阴影
//
//    private boolean mStoke=false;//默认不描边
//    private int mStokeColor=DEFAULT_STOKE_COLOR;//默认描边颜色(蓝色)
//    private int mStokeWidth=2;//默认描边宽度
//    private boolean mAnimate=true;//默认开启隐藏动画
//
//    private static class Holder {
//        private static Badgetor instance = new Badgetor();
//    }
//
//    public static Badgetor getInstance() {
//        return Holder.instance;
//    }
//
//    private void initView(Context context){
//        mQBadgeView=new QBadgeView(context);
//        mQBadgeView.setBadgeTextSize(getTextSize(),true);//文字大小
//        mQBadgeView.setBadgeTextColor(getTextColor());//文字颜色
//        mQBadgeView.setBadgeGravity(getGravity());//设置Badge相对于TargetView的位置
//        mQBadgeView.setGravityOffset(getOffset(),true);//设置外边距
//        mQBadgeView.setBadgePadding(getPadding(),true);//设置内边距
//        mQBadgeView.setBadgeBackgroundColor(getBackgroundColor());//设置背景色
//        if(null!=getBackgroundDrawable()){
//            mQBadgeView.setBadgeBackground(getBackgroundDrawable());//设置背景图片
//        }
//        mQBadgeView.setShowShadow(isShadow());//设置是否显示阴影
//        if(isStoke()){
//            mQBadgeView.stroke(getStokeColor(),getStokeWidth(),true);//描边
//        }
//    }
//
//
//    /**显示数字小圆点**/
//    public void showNumPoint(View targetView, int count, Context context){
//        if(count<0){
//            return;
//        }
//        initView(context);
//        mQBadgeView.bindTarget(targetView);//在mTvTest上显示小圆点
//        mQBadgeView.setBadgeNumber(count);//在小圆点上显示数字
//        mQBadgeView.setExactMode(isExactMode());//设置是否显示精确模式数值
//    }
//
//    /**获取小圆点上的数字**/
//    public int getPointNum(){
//        int number=0;
//        if(mQBadgeView!=null){
//            number=mQBadgeView.getBadgeNumber();
//        }
//        return number;
//    }
//
//
//    /**显示文字小圆点**/
//    public void showTextPoint(View targetView, String text, Context context){
//        if(null==text|| TextUtils.isEmpty(text)){
//            return;
//        }
//        initView(context);
//        mQBadgeView.bindTarget(targetView);//在mTvTest上显示小圆点
//        mQBadgeView.setBadgeText(text);//在小圆点上显示文字
//    }
//
//    /**获取小圆点上的文字**/
//    public String getPointText(){
//        String text=null;
//        if(mQBadgeView!=null){
//            text=mQBadgeView.getBadgeText();
//        }
//        return text;
//    }
//
//    /**隐藏小圆点**/
//    public void hideBadgeView(){
//        if(mQBadgeView!=null){
//            mQBadgeView.hide(isAnimate());//隐藏Badge
//        }
//    }
//
//    /**
//     * 打开拖拽消除模式并设置监听
//     * 注：只有当设置了拖曳监听才会有拖曳效果
//     */
//    public void setOnDragStateChangedListener(Badge.OnDragStateChangedListener listener){
//        if(mQBadgeView!=null){
//            mQBadgeView.setOnDragStateChangedListener(listener);
//        }
//    }
//
//
//    public float getTextSize() {
//        return mTextSize;
//    }
//
//    /**设置文字大小**/
//    public Badgetor setTextSize(float textSize) {
//        this.mTextSize = textSize;
//        return this;
//    }
//
//    public int getTextColor() {
//        return mTextColor;
//    }
//
//    /**设置文字颜色**/
//    public Badgetor setTextColor(int textColor) {
//        this.mTextColor = textColor;
//        return this;
//    }
//
//    public boolean isExactMode() {
//        return mExactMode;
//    }
//
//    /**设置是否显示精确数值**/
//    public Badgetor setExactMode(boolean exactMode) {
//        this.mExactMode = exactMode;
//        return this;
//    }
//
//    public int getGravity() {
//        return mGravity;
//    }
//
//    /**设置BadgeView相对view的位置**/
//    public Badgetor setGravity(int gravity) {
//        this.mGravity = gravity;
//        return this;
//    }
//
//    public float getOffset() {
//        return mOffset;
//    }
//
//    /**设置外边距**/
//    public Badgetor setOffset(float offset) {
//        this.mOffset = offset;
//        return this;
//    }
//
//    public float getPadding() {
//        return mPadding;
//    }
//
//    /**设置内边距**/
//    public Badgetor setPadding(float padding) {
//        this.mPadding = padding;
//        return this;
//    }
//
//    public int getBackgroundColor() {
//        return mBackgroundColor;
//    }
//
//    /**设置背景色**/
//    public Badgetor setBackgroundColor(int backgroundColor) {
//        this.mBackgroundColor = backgroundColor;
//        return this;
//    }
//
//    public Drawable getBackgroundDrawable() {
//        return mBackgroundDrawable;
//    }
//
//    /**设置背景图片**/
//    public Badgetor setBackgroundDrawable(Drawable backgroundDrawable) {
//        this.mBackgroundDrawable = backgroundDrawable;
//        return this;
//    }
//
//    public boolean isShadow() {
//        return mShadow;
//    }
//
//    /**设置阴影**/
//    public Badgetor setShadow(boolean shadow) {
//        this.mShadow = shadow;
//        return this;
//    }
//
//    public boolean isStoke() {
//        return mStoke;
//    }
//
//    public int getStokeColor() {
//        return mStokeColor;
//    }
//
//    public int getStokeWidth() {
//        return mStokeWidth;
//    }
//
//    /**设置描边属性**/
//    public Badgetor setStoke(boolean stoke,int color,int width) {
//        this.mStoke = stoke;
//        if(stoke){
//            this.mStokeColor=color;
//            this.mStokeWidth=width;
//        }
//        return this;
//    }
//
//    public boolean isAnimate() {
//        return mAnimate;
//    }
//
//    /**设置是否开启隐藏动画**/
//    public Badgetor setAnimate(boolean animate) {
//        this.mAnimate = animate;
//        return this;
//    }
//}
//
////============================最初设置供参考，请勿删除=========================================
//
////    /**显示小圆点**/
////    private void showBadgeView(View view, int count, Context context){
////        if(count<0){
////            return;
////        }
////        mQBadgeView=new QBadgeView(context);
////        mQBadgeView.bindTarget(view);//在mTvTest上显示小圆点
//////        mQBadgeView.setBadgeText("我是水");//在小圆点上显示文字
////        mQBadgeView.setBadgeNumber(count);//在小圆点上显示数字
////        mQBadgeView.setBadgeTextSize(8,true);//文字大小
////        mQBadgeView.setBadgeTextColor(Color.parseColor("#ffffff"));//文字颜色
////        mQBadgeView.setExactMode(false);//设置是否显示精确模式数值
////        mQBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);//设置Badge相对于TargetView的位置
////        mQBadgeView.setGravityOffset(2,true);//设置外边距
////        mQBadgeView.setBadgePadding(2,true);//设置内边距
////        mQBadgeView.setBadgeBackgroundColor(Color.parseColor("#ff0000"));//设置背景色
//////        mQBadgeView.setBadgeBackground(drawable);//设置背景图片
////        mQBadgeView.setShowShadow(false);//设置是否显示阴影
//////        mQBadgeView.stroke();//描边
//////        mQBadgeView.hide(true);//隐藏Badge
////
////
//////        //打开拖拽消除模式并设置监听
//////        mQBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//////            @Override
//////            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//////
//////            }
//////        });
////    }
//
////=====================================================================
