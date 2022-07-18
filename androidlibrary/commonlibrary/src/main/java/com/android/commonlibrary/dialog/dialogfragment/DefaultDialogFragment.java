package com.android.commonlibrary.dialog.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;

/**
 * Title:设置默认DefaultDialogFragment
 * Description:
 *
 * Created by pei
 * Date: 2018/3/21
 */
public class DefaultDialogFragment extends AppCompatDialogFragment {

    private Context mContext;
    //自定义布局相关
    private AlertDialog.Builder mCustomerBuilder;
    private AlertDialog mDialog;
    private int mCustomerLayoutId;//自定义布局id
    private View mCustomerLayoutView;//自定义布局
    //系统布局
    private String mTitle;
    private String message;
    private String mPositiveText;
    private String mNegativeText;
    private int mBgGround;
    private boolean mBackCancel = true;//默认点击返回键关闭dialog
    private boolean mTouchOutsideCancel = true;//默认点击dialog外面屏幕，dialog关闭
    private DialogInterface.OnClickListener mPositiveOnClickListener;
    private DialogInterface.OnClickListener mNegativeOnClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
        mCustomerBuilder= new AlertDialog.Builder(mContext);
        if (mCustomerLayoutId!=0) {
            //自定义布局
            mCustomerBuilder.setView(mCustomerLayoutView);
        }else{
            //非自定义布局
            //设置标题
            if (StringUtil.isNotEmpty(mTitle)) {
                mCustomerBuilder.setTitle(mTitle);
            }
            //设置提示语
            if (StringUtil.isNotEmpty(message)) {
                mCustomerBuilder.setMessage(message);
            }
        }
        //设置确定按钮
        if (null != mPositiveText) {
            mCustomerBuilder.setPositiveButton(mPositiveText, mPositiveOnClickListener);
        }
        //设置否定按钮
        if (null != mNegativeText) {
            mCustomerBuilder.setNegativeButton(mNegativeText, mNegativeOnClickListener);
        }
        //设置返回键
        mCustomerBuilder.setOnKeyListener(new KeyBackListener());
        //创建mDialog
        mDialog = mCustomerBuilder.create();
        //设置屏幕外部点击是否可以取消
        mDialog.setCanceledOnTouchOutside(mTouchOutsideCancel);
        //设置dialog背景色
        if(mBgGround!=0){
            mDialog.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(mBgGround));
        }
        return mDialog;
    }

    /**监听返回键的类**/
    class KeyBackListener implements DialogInterface.OnKeyListener{
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return !mBackCancel;
            }
            return false;
        }
    }

    //===============自定义布局特定方法===============

    /***
     * 设置自定义布局
     *
     * 此方法设置后,title,message均在布局中实现,则不调用本类中的
     * setTitle，setMessage方法
     *
     * @return
     */
    public DefaultDialogFragment setCustomerLayoutId(int layoutId,Context context){
        this.mCustomerLayoutId=layoutId;
        //加载自定义布局
        mCustomerLayoutView = LayoutInflater.from(context).inflate(layoutId, null);
        return this;
    }

    /**根据id获取自定义布局中的控件**/
    public <T>View getView(int rId){
        if(mCustomerLayoutView!=null&&rId!=0){
            return mCustomerLayoutView.findViewById(rId);
        }
        return null;
    }

    //===============系统dialog相关方法===============

    /**
     * 设置标题
     **/
    public DefaultDialogFragment setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置信息
     **/
    public DefaultDialogFragment setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置点击返回键是否关闭dialog
     **/
    public DefaultDialogFragment setCancel(boolean canDismiss) {
        this.mBackCancel = canDismiss;
        return this;
    }

    /**
     * 设置点击屏幕外面是否关闭dialog
     **/
    public DefaultDialogFragment setCancelOnTouchOutside(boolean canDismiss) {
        this.mTouchOutsideCancel = canDismiss;
        return this;
    }

    /**设置dialog背景**/
    public DefaultDialogFragment setBackGround(int rid){
        mBgGround=rid;
        return this;
    }

    /**
     * 设置Positive点击事件
     **/
    public DefaultDialogFragment setPositiveListener(String positiveText, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveText = positiveText;
        this.mPositiveOnClickListener = onClickListener;
        return this;
    }

    /**
     * 设置Negative点击事件
     **/
    public DefaultDialogFragment setNegativeListener(String negativeText, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeText = negativeText;
        this.mNegativeOnClickListener = onClickListener;
        return this;
    }

    /***
     * 显示dialog，需要传 fragmentManager=getSupportFragmentManager()
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        try {
            String className = this.getClass().getSimpleName();
            this.show(fragmentManager, className);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("===showDialog error====" + e.getMessage());
        }
    }

}

//===============调用范例=================
//    /**弹出系统定义布局的dialog**/
//    private void showSystemDialog() {
//        new DefaultDialogFragment()
//                //设置标题
//                .setTitle("你是谁")
//                //设置内容
//                .setMessage("who are you")
//                //禁用返回键
//                .setCancel(false)
//                //禁止屏幕外部点击消失
//                .setCancelOnTouchOutside(false)
//                //设置背景色
//                .setBackGround(R.color.colorAccent)
//                //取消事件
//                .setNegativeListener("不好", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.shortShow("heihei");
//                    }
//                })
//                //确认事件
//                .setPositiveListener("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.shortShow("haha");
//                    }
//                })
//                //显示dialog
//                .showDialog(getSupportFragmentManager());
//    }
//
//    /**弹出自定义布局的dialog**/
//    private void showCustomerDialog(){
//        DefaultDialogFragment defaultDialogFragment=new DefaultDialogFragment();
//        //设置自定义布局
//        defaultDialogFragment.setCustomerLayoutId(R.layout.customer_layout, MainActivity.this)
//                //禁用返回键
//                .setCancel(false)
//                //禁止屏幕外部点击消失
//                .setCancelOnTouchOutside(false)
//                //取消事件(可不写,然后在自定义布局中写点击事件)
//                .setNegativeListener("不好a", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.shortShow("heihei");
//                    }
//                })
//                //确认事件(可不写,然后在自定义布局中写点击事件)
//                .setPositiveListener("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.shortShow("haha");
//                    }
//                })
//                //显示dialog
//                .showDialog(getSupportFragmentManager());
//        //获取自定义布局中的控件
//        TextView tv= (TextView) defaultDialogFragment.getView(R.id.tv_text);
//        //给自定义布局中的控件设置值
//        tv.setText("大神");
//    }
