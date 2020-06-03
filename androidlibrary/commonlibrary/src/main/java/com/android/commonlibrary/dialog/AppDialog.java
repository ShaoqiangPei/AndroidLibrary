package com.android.commonlibrary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.android.commonlibrary.R;
import com.android.commonlibrary.util.StringUtil;

/**
 * Title:自定义dialog父类
 * description:
 * autor:pei
 * created on 2020/6/2
 */
public abstract class AppDialog extends Dialog {

    public static final int WRAP_CONTENT=-2;//dialog窗口大小自适应
    public static final int MATCH_PARENT=-3;//dialog窗口全屏

    protected Context mContext;
    protected View mLayoutView;//加载的布局View
    protected InputMethodManager mInputManager;//键盘的隐藏，开启对象

    protected double maxScaleWidth=0;//屏幕宽度最大比例系数
    protected double maxScaleHeight=0;//屏幕高度最大比例系数
    protected double mScaleWidth;//屏幕宽度比例
    protected double mScaleHeight;//屏幕高度比例

    /**使用"R.style.AppDialog"的dialog样式初始化**/
    public AppDialog(Context context, int layoutId){
        super(context, R.style.AppDialog);
        //初始化
        init(context,layoutId);
    }

    /**可传入"theme"的dialog样式初始化**/
    public AppDialog(Context context, int theme, int layoutId){
        super(context, theme);
        //初始化
        init(context,layoutId);
    }

    /**初始化**/
    private void init(Context context, int layoutId){
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        //弹出软键盘时自适应
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.mContext=context;
        //设置窗口大小比例
        double windowSize[]=getWindowSize();
        if(windowSize!=null){
            mScaleWidth=windowSize[0];
            mScaleHeight=windowSize[1];
        }
        //设置布局
        mLayoutView = LayoutInflater.from(context).inflate(layoutId, null);
        //设置窗口
        setDialogSize();
    }

    /**设置窗口大小**/
    private void setDialogSize() {
        mInputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        Window window = AppDialog.this.getWindow();
        //设置显示位置
        window.setGravity(Gravity.CENTER);
        //设置宽高
        WindowManager.LayoutParams windowParams = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        ((FragmentActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        setWindowWidth(windowParams, dm);//设置宽度
        setWindowHeight(windowParams, dm);//设置高度

        this.addContentView(mLayoutView, new ViewGroup.LayoutParams(windowParams.width, windowParams.height));
    }

    /**设置窗口宽度**/
    private void setWindowWidth(WindowManager.LayoutParams windowParams, DisplayMetrics dm) {
        if (mScaleWidth == MATCH_PARENT) {
            windowParams.width = dm.widthPixels;
        } else if (mScaleWidth == WRAP_CONTENT) {
            if (maxScaleWidth > 0) {
                windowParams.width = (int) (dm.widthPixels * maxScaleWidth);
            } else {
                windowParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        } else if (mScaleWidth > 0) {
            windowParams.width = (int) (dm.widthPixels * mScaleWidth); // 宽度设置为屏幕的0.65
        } else {
            throw new UnknownError("dialog宽度系数设置错误");
        }
    }

    /**设置窗口高度**/
    private void setWindowHeight(WindowManager.LayoutParams windowParams, DisplayMetrics dm) {
        if (mScaleHeight == MATCH_PARENT) {
            windowParams.height = dm.heightPixels;
        } else if (mScaleHeight == WRAP_CONTENT) {
            if(maxScaleHeight>0){
                windowParams.height = (int) (dm.heightPixels * maxScaleHeight);
            }else{
                windowParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        } else if (mScaleHeight > 0) {
            windowParams.height = (int) (dm.heightPixels * mScaleHeight); // 高度设置为屏幕的0.6
        } else {
            throw new UnknownError("dialog高度系数设置错误");
        }
    }

    /**设置窗口大小比例**/
    protected abstract double[] getWindowSize();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

    /**根据id获取自定义布局中的控件**/
    public View getView(int rId){
        if(mLayoutView!=null&&rId!=0){
            return mLayoutView.findViewById(rId);
        }
        return null;
    }

    /**设置dialog最大宽度系数**/
    protected void setMaxScaleWidth(double maxScaleWidth) {
        this.maxScaleWidth = maxScaleWidth;
    }

    /**设置dialog最大高度度系数**/
    protected void setMaxScaleHeight(double maxScaleHeight) {
        this.maxScaleHeight = maxScaleHeight;
    }

    /**按钮点击时调用的实现类**/
    protected class DialogOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**判断dialog是否该show**/
    public boolean shouldShow(Context context){
        if(context instanceof Activity){//context为activity
            Activity activity=null;
            try {
                activity = (Activity) context;
            } catch (Exception e) {
                e.printStackTrace();
                activity=null;
            }
            if (activity!=null&&!activity.isFinishing()&&!activity.isDestroyed()&&!isShowing()) {
                return true;
            }
        }else if(context instanceof Service){//context为服务
            if(!isShowing()){
                return true;
            }
        }
        return false;
    }

    /** 按钮点击事件(BUTTON_NEGATIVE:否定的) **/
    public void setNegativeBtn(TextView textView, String message, final OnClickListener listener) {
        if (StringUtil.isNotEmpty(message)) {
            textView.setText(message);
        }
        textView.setOnClickListener(new DialogOnclickListener() {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                listener.onClick(AppDialog.this, DialogInterface.BUTTON_NEGATIVE);
            }
        });
    }

    /** 按钮点击事件(BUTTON_NEUTRAL：中立的) **/
    public void setNeutralBtn(TextView textView, String message, final OnClickListener listener) {
        if (StringUtil.isNotEmpty(message)) {
            textView.setText(message);
        }
        textView.setOnClickListener(new DialogOnclickListener() {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                listener.onClick(AppDialog.this, DialogInterface.BUTTON_NEUTRAL);
            }
        });
    }

    /** 按钮点击事件(BUTTON_POSITIVE：积极的) **/
    public void setPositiveBtn(TextView textView, String message, final OnClickListener listener) {
        if (StringUtil.isNotEmpty(message)) {
            textView.setText(message);
        }
        textView.setOnClickListener(new DialogOnclickListener() {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                listener.onClick(AppDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        });
    }

}
