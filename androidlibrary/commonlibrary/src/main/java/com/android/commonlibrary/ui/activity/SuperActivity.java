package com.android.commonlibrary.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.android.commonlibrary.R;
import com.android.commonlibrary.frame.view.ButterKnifeWrapper;
import com.android.commonlibrary.interfacer.base.IAFFrame;
import com.android.commonlibrary.ui.manager.act.AppActivityManager;
import com.android.commonlibrary.interfacer.base.IAF;
import com.android.commonlibrary.interfacer.base.IActivity;
import com.android.commonlibrary.util.DoubleClickUtil;
import java.io.Serializable;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:Activity超类
 *
 * 更多关于intent跳转传值的方法可以参看IntentHelper类
 * Author:pei
 * Date: 2019/7/3
 */
public abstract class SuperActivity extends AppCompatActivity implements IAF, IActivity, IAFFrame {

    protected View mLayoutView;//总布局
    protected Activity mContext;
    protected boolean isNoTitle;//是否隐藏标题栏,默认隐藏标题栏

    private ButterKnifeWrapper mButterKnifeWrapper;//butterKnife初始化控件框架

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppActivityManager.getInstance().addActivity(this);

        if (getContentViewId() != 0) {
            //隐藏标题栏
            if(isNoTitle){
                setNoDefaultTitle();
            }
            //加载布局
            mLayoutView = LayoutInflater.from(mContext).inflate(getContentViewId(), null);
            setContentView(mLayoutView);

            //加载框架的时候用
            loadFrame();
            //初始化加载
            initData();
            setListener();
        }else{
            throw new SecurityException("====请在Activity的getContentViewId中给activity设置对应的xml文件id=======");
        }
    }

    /**设置隐藏标题栏**/
    protected void setNoDefaultTitle() {
        if (mContext != null) {
            Activity activity = mContext;
            if (activity != null) {
                if (activity instanceof AppCompatActivity) {
                    //当前为AppCompatActivity时隐藏标题栏
                    setTheme(R.style.Theme_Design_NoActionBar);
                } else {
                    //当前为activity时隐藏标题栏
                    requestWindowFeature(Window.FEATURE_NO_TITLE);
                }
            }
        }
    }

    /**加载框架的时候用，供子类重写，此处不做处理**/
    @Override
    public void loadFrame() {
        mButterKnifeWrapper =new ButterKnifeWrapper(this);
        mButterKnifeWrapper.attachView();
    }

    /**做框架必要的注销处理，供子类重写，此处不做处理**/
    @Override
    public void destoryFrame() {
        if(mButterKnifeWrapper !=null){
            mButterKnifeWrapper.detachView();
        }
    }

    @Override
    public void onClick(View v) {
        DoubleClickUtil.shakeClick(v);
    }

    @Override
    protected void onDestroy() {
        //框架销毁
        destoryFrame();
        AppActivityManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

    /**用于初始化控件的**/
    @Override
    public <T>T getView(int rId) {
        return AppHelper.getInstance().getView(mContext,rId);
    }

    /**获取控件值**/
    @Override
    public String getTextOfView(TextView textView) {
        return AppHelper.getInstance().getTextOfView(textView);
    }

    /**获取非空字符串**/
    @Override
    public String getNotEmptyString(String str) {
        return AppHelper.getInstance().getNotEmptyString(str);
    }

    /**长吐司**/
    @Override
    public void showToast(String msg) {
        AppHelper.getInstance().showToast(msg);
    }

    /**短吐司**/
    @Override
    public void showShortToast(String msg) {
        AppHelper.getInstance().showShortToast(msg);
    }

    /**无参界面跳转**/
    @Override
    public void startAct(Class<?> cls) {
       IntentHelper.getInstance().startAct(mContext,cls);
    }

    /**含一个参数的界面跳转**/
    @Override
    public void startParameterAct(Class<?> cls, String tag, Object parameter) {
        IntentHelper.getInstance().startParameterAct(mContext,cls,tag,parameter);
    }

    /**接收上一个界面传过来的int**/
    @Override
    public int getIntParameter(String tag) {
        return IntentHelper.getInstance().getIntParameter(mContext,tag);
    }

    /**接收上一个界面传过来的String**/
    @Override
    public String getStringParameter(String tag) {
        return IntentHelper.getInstance().getStringParameter(mContext,tag);
    }

    /**接收上一个界面传过来的boolean**/
    @Override
    public boolean getBooleanParameter(String tag) {
        return IntentHelper.getInstance().getBooleanParameter(mContext,tag);
    }

    /**接收上一个界面传过来的Bundle**/
    @Override
    public Bundle getBundleParameter(String tag) {
        return IntentHelper.getInstance().getBundleParameter(mContext,tag);
    }

    /**接收上一个界面传过来的对象，对象需要实现Serializable**/
    @Override
    public Serializable getSerializableObject(String tag) {
        return IntentHelper.getInstance().getSerializableObject(mContext,tag);
    }

    /**接收上一个界面传过来的对象，对象需要实现Parcelable**/
    @Override
    public Parcelable getParcelableObject(String tag) {
        return IntentHelper.getInstance().getParcelableObject(mContext,tag);
    }

    /**传一个int集合的界面跳转**/
    @Override
    public void startIntegerListAct(Class<?> cls, String tag, List<Integer> list) {
        IntentHelper.getInstance().startIntegerListAct(mContext,cls,tag,list);
    }

    /**接收上一个界面传过来的int集合**/
    @Override
    public List<Integer> getIntegerList(String tag) {
        return IntentHelper.getInstance().getIntegerList(mContext,tag);
    }

    /**传一个String集合的界面跳转**/
    @Override
    public void startStringListAct(Class<?> cls, String tag, List<String> list) {
        IntentHelper.getInstance().startStringListAct(mContext,cls,tag,list);
    }

    /**接收上一个界面传过来的String集合**/
    @Override
    public List<String> getStringList(String tag) {
        return IntentHelper.getInstance().getStringList(mContext,tag);
    }

    /**传一个object集合的界面跳转,集合中的object需要实现Parcelable接口**/
    @Override
    public void startParcelableListAct(Class<?> cls, String tag, List<? extends Parcelable> list) {
        IntentHelper.getInstance().startParcelableListAct(mContext,cls,tag,list);
    }

    /**接收上一个界面传过来的object集合,集合中的object需要实现Parcelable接口**/
    @Override
    public List<? extends Parcelable> getParcelableList(String tag) {
        return IntentHelper.getInstance().getParcelableList(mContext,tag);
    }

    /***
     * 带List<Serializable>list的界面跳转,集合中的object需要实现Serializable接口
     *
     * @param cls
     * @param bundle  若有携带有信息的bundle需要传,则此处传该bundle对象
     *                若没有bundle需要传,则此处传null就行
     * @param tag
     * @param list
     */
    @Override
    public void startSerializableListAct(Class<?> cls, Bundle bundle, String tag, List<? extends Serializable> list) {
        IntentHelper.getInstance().startSerializableListAct(mContext,cls,bundle,tag,list);
    }

    /**接收上一个界面传过来的list<Serializable>list,集合中的object需要实现Serializable接口**/
    @Override
    public List<? extends Serializable> getSerializableList(String tag) {
        return IntentHelper.getInstance().getSerializableList(mContext,tag);
    }

    /** 含有标题和内容的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message).show();
        return alertDialog;
    }

    /** 含有标题、内容、两个按钮的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener).setNegativeButton(negativeText, onNegativeClickListener).show();
        return alertDialog;
    }

    /** 含有标题、内容、图标、两个按钮的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message, int icon, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message).setIcon(icon)
                .setPositiveButton(positiveText, onPositiveClickListener).setNegativeButton(negativeText, onNegativeClickListener).show();
        return alertDialog;
    }

    /** 进度条 */
    protected ProgressDialog getProgressDialog(String title, String message, boolean cancelable) {
        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCanceledOnTouchOutside(cancelable);
        return mProgressDialog;
    }

}
