package com.android.commonlibrary.dialog_fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.commonlibrary.util.DoubleClickUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.lang.reflect.Field;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Title:dialog父类
 * Description:
 *
 * Created by pei
 * Date: 2018/8/25
 */
public abstract class AppDialogFragment extends AppCompatDialogFragment implements View.OnClickListener{

    protected static final int RID=-1;//默认背景资源id
    public static final int WRAP_CONTENT=-2;//dialog窗口大小自适应
    public static final int MATCH_PARENT=-3;//dialog窗口全屏

    protected static final float MAX_UI_ALPHA=1.0f;//dialog显示时window最大亮度
    protected static final float DEFAULT_UI_ALPHA=0.6f;//dialog显示遮罩时的默认亮度值

    protected View mLayoutView;
    private Unbinder mUnbinder;
    protected Context mContext;
    //重写可监听返回键的处理(此对象设置后,mBackCancel方法失效)
    //即mOnDialogCancelListener不为null的时候，返回键处理由mOnDialogCancelListener控制
    //即mOnDialogCancelListener为null的时候,返回键由mBackCancel控制
    protected OnDialogCancelListener mOnDialogCancelListener;
    protected boolean mBackCancel=true;//默认点击返回键关闭dialog
    protected boolean mTouchOutsideCancel=true;//默认点击dialog外面屏幕，dialog关闭
    protected boolean mRidShadow=false;//默认dialog设置圆角背景时有阴影
    protected boolean mUIShadow=false;//默认弹出diaolog时,界面无遮罩
    protected float mUIShadowAlpha=DEFAULT_UI_ALPHA;//dialog显示遮罩时的亮度值
    protected int mBackGroundId=RID;//背景资源id，类似R.drawable.ui_shape_gray_round_corner
    protected double mScaleWidth=WRAP_CONTENT;//屏幕宽度比例
    protected double mScaleHeight=WRAP_CONTENT;//屏幕高度比例
    protected float mDestoryAlpha=MAX_UI_ALPHA;//dialog关闭时窗口亮度，默认为MAX_UI_ALPHA,即这张消失，界面恢复最亮

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double windowSize[]=getWindowSize();
        if(windowSize!=null){
            mScaleWidth=windowSize[0];
            mScaleHeight=windowSize[1];
        }
        //设置全屏
        if(mScaleWidth==MATCH_PARENT&&mScaleHeight==MATCH_PARENT){
            setStyle(STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder= ButterKnife.bind(this,mLayoutView);//绑定framgent
        //初始化
        onCreateFragmentView(inflater, container, savedInstanceState);
        return mLayoutView;
    }

    protected void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        setListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置窗口属性
        setDialog();
    }

    /**设置窗口属性**/
    private void setDialog(){
        Dialog dialog=getDialog();
        if(dialog!=null) {
            //返回键的处理
            backEvent(dialog);
            //触碰dialog外面是否消失
            dialog.setCanceledOnTouchOutside(mTouchOutsideCancel);
            //设置窗口大小
            setWindowSize(dialog);
            //设置背景相关
            setBackGround(dialog);
            //设置dialog弹出时是否显示背景遮罩
            setShade();
        }
    }

    /**返回键的处理**/
    private void backEvent(Dialog dialog){
        //返回键是否消失
        //dialog.setCancelable(mBackCancel);
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK){//返回键的处理
                if(mOnDialogCancelListener!=null){
                    //此处可自定义监听对dialog返回键的处理
                    return mOnDialogCancelListener.onCancel(dialogInterface,keyCode,keyEvent);
                }else{
                    return !mBackCancel;
                }
            }
            return false;
        });
    }

    /**设置窗口大小**/
    private void setWindowSize(Dialog dialog){
        Window window = dialog.getWindow();
        if(window!=null) {
            //初始化宽高,默认设置全屏
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;//此代码必须加,不然会在dialog弹出时出现背景遮罩
            windowParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            windowParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(windowParams);

            if(mScaleWidth!=MATCH_PARENT&&mScaleHeight!=MATCH_PARENT){
                DisplayMetrics dm = new DisplayMetrics();
                ((FragmentActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
                setWindowWidth(windowParams,dm);//设置宽度
                setWindowHeight(windowParams,dm);//设置高度
                window.setLayout(windowParams.width,windowParams.height);
            }

        }
    }

    /**设置窗口宽度**/
    private void setWindowWidth(WindowManager.LayoutParams windowParams,DisplayMetrics dm) {
        if (mScaleWidth == WRAP_CONTENT) {
            windowParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (mScaleWidth > 0) {
            windowParams.width = (int) (dm.widthPixels * mScaleWidth); // 宽度设置为屏幕的0.65
        } else {
            throw new UnknownError("dialog宽度系数设置错误");
        }
    }

    /**设置窗口高度**/
    private void setWindowHeight(WindowManager.LayoutParams windowParams,DisplayMetrics dm) {
        if (mScaleHeight == WRAP_CONTENT) {
            windowParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (mScaleHeight > 0) {
            windowParams.height = (int) (dm.heightPixels * mScaleHeight); // 高度设置为屏幕的0.6
        } else {
            throw new UnknownError("dialog高度系数设置错误");
        }
    }

    /**设置背景相关**/
    private void setBackGround(Dialog dialog){
        Window window = dialog.getWindow();
        if(window!=null){
            //当dialog设置圆角背景的时候，去掉边角阴影
            if (mRidShadow) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            //设置背景
            if (mBackGroundId != RID) {
                window.setBackgroundDrawableResource(mBackGroundId);
            }
        }
    }

    /**设置dialog弹出时是否显示背景遮罩**/
    private void setShade(){
        if(mUIShadow){
            setActivityUIAlpha(mUIShadowAlpha);
        }
    }

    /***
     * 设置窗口明亮度
     *
     * dialog弹出时界面变暗设置0.6f,dialog消失时设置1.0f
     * @param alpha
     * @return
     */
    private AppDialogFragment setActivityUIAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((FragmentActivity)mContext).getWindow().getAttributes();
        lp.alpha =alpha;//dialog弹出时界面变暗设置0.6f,dialog消失时设置1.0f
        ((FragmentActivity)mContext).getWindow().setAttributes(lp);
        ((FragmentActivity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return AppDialogFragment.this;
    }

    @Override
    public void onClick(View v) {
        DoubleClickUtil.shakeClick(v);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Field mDismissed = this.getClass().getSuperclass().getDeclaredField("mDismissed");
            Field mShownByMe = this.getClass().getSuperclass().getDeclaredField("mShownByMe");
            mDismissed.setAccessible(true);
            mShownByMe.setAccessible(true);
            mDismissed.setBoolean(this, false);
            mShownByMe.setBoolean(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
        //背景恢复亮度
        mUIShadow=false;
        //dialog关闭时,设置窗口透明度,默认为1.0f，即关闭dialog后，界面遮罩消失
        setActivityUIAlpha(mDestoryAlpha);
        super.onDestroy();
    }

    protected abstract double[] getWindowSize();
    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract void setListener();

    /**监听返回键处理的接口**/
    public interface OnDialogCancelListener{
        boolean onCancel(DialogInterface dialog, int keyCode, KeyEvent event);
    }

    /**返回键监听**/
    public AppDialogFragment setOnDialogCancelListener(OnDialogCancelListener listener){
        this.mOnDialogCancelListener=listener;
        return this;
    }

    /**根据id获取自定义布局中的控件**/
    public <T>View getView(int rId){
        if(mLayoutView!=null&&rId!=0){
            return mLayoutView.findViewById(rId);
        }
        return null;
    }


    /**
     * 设置dialog背景色
     *
     * @param rId:背景资源id，类似R.drawable.ui_shape_gray_round_corner
     * @return
     */
    public AppDialogFragment setBackGroundId(int rId){
        this.mBackGroundId=rId;
        return this;
    }

    /**
     * 当dialog设置圆角背景的时候，会留下边角阴影
     * 设置true的时候，可去除边角,默认为false，即有边角阴影
     * @return
     */
    public AppDialogFragment setRidShadow(boolean ridShadow){
        this.mRidShadow=ridShadow;
        return this;
    }

    /***
     * 设置dialog弹出时是否有遮罩覆盖界面
     * 默认为false，即无遮罩
     */
    public AppDialogFragment setUIShadow(boolean uiShadow){
        this.mUIShadow=uiShadow;
        return this;
    }

    /***
     * 设置dialog弹出时是否有遮罩覆盖界面
     * 默认为false，即无遮罩
     * uiShadowAlpha:遮罩弹出时的屏幕亮度(范围0f-1f,0f为黑色,1f为透明),默认为DEFAULT_UI_ALPHA,即0.6f
     */
    public AppDialogFragment setUIShadow(boolean uiShadow,float uiShadowAlpha){
        this.mUIShadow=uiShadow;
        this.mUIShadowAlpha=uiShadowAlpha;
        return this;
    }

    /***
     * 设置dialog在消失时,去掉背景后面的遮罩透明度，默认为MAX_UI_ALPHA
     * 即正常情况下，关闭dialog，遮罩完全消失。
     *
     * 注:当界面上弹出多个dialog，而在关闭最上面的dialog时仍想保留dialog遮罩的时候
     *    可以调用此方法给界面设置带透明度的遮罩，除此情况以外,不需调用此方法，默认为MAX_UI_ALPHA,即消失时去掉背景遮罩就好
     *
     * @param alpha 0f-1.0f  0为全黑，1.0f为全透明，即最亮
     *              当取值不在0-1之间，则取默认透明度MAX_UI_ALPHA,即dialog消失时去掉背景遮罩
     * @return
     */
    public AppDialogFragment setDestoryAlpha(float alpha){
        if(alpha<0||alpha>1){
            this.mDestoryAlpha= MAX_UI_ALPHA;
        }else{
            this.mDestoryAlpha=alpha;
        }
        return this;
    }

    /**设置点击返回键是否关闭dialog**/
    public AppDialogFragment setCancel(boolean canDismiss) {
        this.mBackCancel = canDismiss;
        return this;
    }

    /**设置点击屏幕外面是否关闭dialog**/
    public AppDialogFragment setCancelOnTouchOutside(boolean canDismiss){
        this.mTouchOutsideCancel=canDismiss;
        return this;
    }

    /**dialog是否已经显示**/
    public boolean isShowing() {
        Dialog dialog = this.getDialog();
        if (dialog != null && dialog.isShowing()) {
            return true;
        }
        return false;
    }

    /***
     * 显示dialog，需要传 fragmentManager=getSupportFragmentManager()
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager){
        try {
            String className = this.getClass().getSimpleName();
            if(!isAdded()) {
                this.show(fragmentManager, className);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("===showDialog error====" + e.getMessage());
        }
    }

    /**用于子类在使用时创建DialogFragment对象**/
    public static AppDialogFragment createFragment(Class<?>cls,Context context,OnCreateFragmentListener listener){
        String className = cls.getClass().getSimpleName();
        FragmentManager fragmentManager=((FragmentActivity)context).getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentByTag(className);
        if(fragment==null){
            fragment=listener.createFragment();
        }
        return (AppDialogFragment) fragment;
    }

    public interface OnCreateFragmentListener{
        Fragment createFragment();
    }

    /**获取editText的值**/
    public String getTextOfView(TextView textView){
        if (textView == null) {
            return null;
        }
        if (textView.getText() == null) {
            return null;
        }
        if (StringUtil.isEmpty(textView.getText().toString())) {
            return "";
        }
        return textView.getText().toString().trim();
    }


}

//==============================自定义dialogFragment的使用范例==================================
//public class MyFragmentDialog extends AppDialogFragment{
//
//    @BindView(R.id.btn_test)
//    Button mBtnTest;
//
//    @Override
//    protected double[] getWindowSize() {
//        return new double[]{0.5,0.5};
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.dialog_fragment_test;
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void setListener() {
//
//    }
//}
