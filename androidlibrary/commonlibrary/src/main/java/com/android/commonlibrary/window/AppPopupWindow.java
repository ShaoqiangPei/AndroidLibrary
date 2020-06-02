package com.android.commonlibrary.window;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import com.android.commonlibrary.R;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Title:PopupWindow基类
 *
 * description:若要popwindow点击外部消失，则设置 this.setFocusable(true)
 *             若要popwindow点击外部不消失，不做setFocusable设置，也不要设置成this.setFocusable(false)
 * autor:pei
 * created on 2019/9/9
 */
public abstract class AppPopupWindow extends PopupWindow{

    //动画弹出效果
    public static final int ANIMATION_TOP_STYLE=1;//动画从控件上方弹出
    public static final int ANIMATION_LEFT_STYLE=2;//动画从控件左方弹出
    public static final int ANIMATION_BOTTOM_STYLE=3;//动画从控件下方弹出
    public static final int ANIMATION_RIGHT_STYLE=4;//动画从控件右方弹出
    public static final int ANIMATION_SCREEN_BOTTOM_STYLE=5;//动画从屏幕底部弹出
    //pop尺寸
    public static final int WRAP_CONTENT=-2;//pop窗口大小自适应
    public static final int MATCH_PARENT=-3;//pop窗口全屏

    protected View mLayoutView;
    protected Context mContext;
    protected InputMethodManager mInputManager;//键盘的隐藏，开启对象
    //宽高系数
    protected double mScaleWidth=AppPopupWindow.WRAP_CONTENT;//屏幕宽度比例
    protected double mScaleHeight=AppPopupWindow.WRAP_CONTENT;//屏幕高度比例

    public AppPopupWindow(Context context) {
        this.mContext = context;
        //设置窗口
        setWindow();
    }

    /**设置窗口**/
    private void setWindow(){
        mLayoutView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        this.setContentView(mLayoutView);
        //设置窗口大小
        setWindowSize();
        //其他设置
        setOthers();
    }

    /**设置窗口大小**/
    private void setWindowSize() {
        double windowSize[]=getWindowSize();
        if(windowSize!=null){
            this.mScaleWidth=windowSize[0];
            this.mScaleHeight=windowSize[1];
        }
        //宽度
        int width= WindowManager.LayoutParams.WRAP_CONTENT;
        if(mScaleWidth==AppPopupWindow.WRAP_CONTENT){
            width= WindowManager.LayoutParams.WRAP_CONTENT;
        }else if(0<mScaleWidth&&mScaleWidth<1){
            width= (int) (ScreenUtil.getWidth()*mScaleWidth);
        }else if(mScaleWidth==AppPopupWindow.MATCH_PARENT){
//            width= WindowManager.LayoutParams.MATCH_PARENT;
            width= ScreenUtil.getWidth();
        }else {
            throw new UnknownError("pop宽度系数设置错误");
        }
        this.setWidth(width);
        //设置高度
        int height= WindowManager.LayoutParams.WRAP_CONTENT;
        if(mScaleHeight==AppPopupWindow.WRAP_CONTENT){
            height= WindowManager.LayoutParams.WRAP_CONTENT;
        }else if(0<mScaleHeight&&mScaleHeight<1){
            height= (int) (ScreenUtil.getHeight()*mScaleHeight);
        }else if(mScaleHeight==AppPopupWindow.MATCH_PARENT){
//            height= WindowManager.LayoutParams.MATCH_PARENT;
            height=ScreenUtil.getHeight();
        }else {
            throw new UnknownError("pop高度系数设置错误");
        }
        this.setHeight(height);
    }

    /**其他设置**/
    @SuppressLint("WrongConstant")
    private void setOthers(){
        // this.setFocusable(true);// 可点击
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
        //处理键盘弹出隐藏问题
        mInputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //防止PopupWindow被软件盘挡住
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**获取pop宽度**/
    protected int getPopWidth(){
        int popupWidth=0;
        if(mScaleWidth==WRAP_CONTENT) {
            mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupWidth = mLayoutView.getMeasuredWidth();
        }else{
            popupWidth=this.getWidth();
        }
        return popupWidth;
    }

    /**获取pop高度**/
    protected int getPopHeight(){
        int popupHeight=0;
        if(mScaleHeight==WRAP_CONTENT) {
            mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupHeight = mLayoutView.getMeasuredHeight();
        }else{
            popupHeight=this.getHeight();
        }
        return popupHeight;
    }

    /**禁止外部点击消失**/
    private void disOuTouchable(){
        setOutsideTouchable(false);
        setTouchInterceptor((v, event) -> {
            if (!isOutsideTouchable()) {
                View mView = getContentView();
                if (null != mView)
                    mView.dispatchTouchEvent(event);
            }
            return isFocusable() && !isOutsideTouchable();
        });
    }

    protected abstract double[] getWindowSize();
    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

    /**根据id获取自定义布局中的控件**/
    public <T>View getView(int rId){
        if(mLayoutView!=null&&rId!=0){
            return mLayoutView.findViewById(rId);
        }
        return null;
    }

    /** PopupWindow背景设置 **/
    public AppPopupWindow setBackground(int color) {
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(color);
        this.setBackgroundDrawable(dw);
        return AppPopupWindow.this;
    }

    /***
     * 设置PopupWindow最大高度
     *
     * @param view
     */
    private void setMaxViewHeight(final View view,float scaleHeight){
        view.post(new Runnable(){
            public void run() {//这里获取宽高
                int maxHeight= (int) (ScreenUtil.getHeight()*scaleHeight);
                int height=view.getHeight();
                ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(ScreenUtil.getWidth()/3, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(height>maxHeight){
                    params.height=maxHeight;
                }
                view.setLayoutParams(params);
            }
        });
    }

    /**点击pop外部是否消失，默认true消失**/
    public AppPopupWindow setOuTouchCancel(boolean ouTouchCancel){
        //禁止外部点击消失
        if(!ouTouchCancel){
            disOuTouchable();
        }
        return AppPopupWindow.this;
    }

    /**
     * 获取焦点
     *
     * @param focuse:设置为true时，可以让pop实现点击pop外部pop消失
     *               还可以让点击返回键时pop消失，一般设置true
     * @return
     */
    public AppPopupWindow setFocuse(boolean focuse){
        //设置为true时，可以让pop实现点击pop外部pop消失
        //还可以让点击返回键时pop消失，一般设置true
        setFocusable(focuse);
        return AppPopupWindow.this;
    }

    /**
     * 让popupwindown背景变暗
     *
     * @param bgAlpha 0f全黑，1.0f全透明
     * @return
     */
    public AppPopupWindow setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = ((AppCompatActivity)mContext).getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0
        ((AppCompatActivity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        ((AppCompatActivity)mContext).getWindow().setAttributes(layoutParams);
        return AppPopupWindow.this;
    }

    /**设置pop显示隐藏的动画**/
    public AppPopupWindow setAnimation(int animationStyle){
        switch (animationStyle) {
            case ANIMATION_TOP_STYLE://从控件上方弹出
                setAnimationStyle(R.style.popuwindow_top_style);
                break;
            case ANIMATION_LEFT_STYLE://从控件左方弹出
                setAnimationStyle(R.style.popuwindow_left_style);
                break;
            case ANIMATION_BOTTOM_STYLE://从控件下方弹出
                setAnimationStyle(R.style.popuwindow_bottom_style);
                break;
            case ANIMATION_RIGHT_STYLE://从控件右方弹出
                setAnimationStyle(R.style.popuwindow_right_style);
                break;
            case ANIMATION_SCREEN_BOTTOM_STYLE://动画从屏幕底部弹出
                setAnimationStyle(R.style.popuwindow_screen_bottom_style);
                break;
            default:
                break;
        }
        return AppPopupWindow.this;
    }

    /**隐藏键盘**/
    public void hideKeyboard(View view){
        mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /** PopupWindow点击间隙处理，根据实际情况重写 **/
    public void onTouchdimiss() {
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mLayoutView.setOnTouchListener((view, event) -> {
            int height = mLayoutView.getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss();
                }
            }
            return false;
        });
    }

    /**
     * 显示在控件正上方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationTop(View view, float marginDp) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2)- getPopWidth()/2 , location[1] - getPopHeight() - ScreenUtil.dp2px(marginDp,mContext));
    }

    /**
     * 显示在控件正下方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationBottom(View view, float marginDp) {
        int xoff=view.getWidth()-getPopWidth();
        if(xoff!=0){
            xoff=xoff/2;
        }else{
            xoff=0;
        }
        showAsDropDown(view, xoff, ScreenUtil.dp2px(marginDp,mContext));
    }

    /**
     * 显示在控件左方
     *
     * @param view 依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationLeft(View view, float marginDp) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] - getPopWidth() - ScreenUtil.dp2px(marginDp,mContext), (location[1] + view.getHeight() / 2) - getPopHeight() / 2);
    }

    /**
     * 显示在控件右方
     *
     * @param view 依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationRight(View view, float marginDp) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() + ScreenUtil.dp2px(marginDp,mContext), (location[1] + view.getHeight() / 2) - getPopHeight() / 2);
    }

    /**
     * 从屏幕底部弹出
     *
     * @param view
     */
    public void showAtScreenBottom(View view){
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
