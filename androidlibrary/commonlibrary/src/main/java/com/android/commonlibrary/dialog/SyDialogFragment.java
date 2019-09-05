package com.android.commonlibrary.dialog;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.android.commonlibrary.R;

/**
 * Title:自定义通用dialogFragment，继承于AppDialogFragment
 * Description:
 * <p>
 * Created by pei
 * Date: 2018/3/12
 */
public class SyDialogFragment extends AppDialogFragment {

    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvConfirm;
    private TextView mTvCancel;

    private int mBackGroundColor= R.color.white;//默认dialog背景色
    private boolean isTitle=true;//默认有标题栏
    private String mTitleText;//默认标题栏文字在xml中设置
    private int mTitleTextColor= super.RID;//默认标题栏文字颜色在xml中设置
    private float mTitleTextSize=super.RID;//默认标题栏文字大小在xml中设置
    private String mContentText;//默认内容区文字在xml中设置
    private int mContentTextColor=super.RID;//默认内容区文字颜色在xml中设置
    private float mContentTextSize=super.RID;//默认内容区文字大小在xml中设置
    private String mConfirmText;//默认确认按钮文字在xml中设置
    private int mConfirmTextColor=super.RID;//默认确认按钮文字颜色在xml中设置
    private float mConfirmTextSize=super.RID;//默认确认按钮文字大小在xml中设置
    private String mCancelText;//默认取消按钮文字在xml中设置
    private int mCancelTextColor=super.RID;//默认取消按钮文字颜色在xml中设置
    private float mCancelTextSize=super.RID;//默认取消按钮文字大小在xml中设置

    private boolean isConfirmBtn;//默认不显示确认按钮
    private boolean isCancelBtn;//默认不显示取消按钮

    private View.OnClickListener mOnConfirmClickListener;
    private View.OnClickListener mOnCancelClickListener;


    @Override
    protected double[] getWindowSize() {
        return new double[]{0.8f,0.28f};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_sy;
    }

    @Override
    protected void initData() {
        //初始化控件
        initView();
        //设置默认背景色
        super.mBackGroundId=mBackGroundColor;
        super.setBackGroundId(mBackGroundId);
        //标题栏显隐
        if(!isTitle){
            mTvTitle.setVisibility(View.GONE);
        }
        //设置标题栏文字
        if(mTitleText!=null){
            mTvTitle.setText(mTitleText);
        }
        //标题栏文字颜色
        if(mTitleTextColor!=super.RID){
            mTvTitle.setTextColor(mContext.getResources().getColor(mTitleTextColor));
        }
        //设置标题栏文字大小
        if(mTitleTextSize!=super.RID){
            mTvTitle.setTextSize(mTitleTextSize);
        }
        //设置内容区文字
        if(mContentText!=null){
            mTvContent.setText(mContentText);
            //设置内容超长时可滑动显示
            mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        //内容区文字颜色
        if(mContentTextColor!=super.RID){
            mTvContent.setTextColor(mContext.getResources().getColor(mContentTextColor));
        }
        //内容区文字大小
        if(mContentTextSize!=super.RID){
            mTvContent.setTextSize(mContentTextSize);
        }
        //设置确认按钮文字
        if(mConfirmText!=null){
            mTvConfirm.setText(mConfirmText);
        }
        //设置确认按钮文字颜色
        if(mConfirmTextColor!=super.RID){
            mTvConfirm.setTextColor(mContext.getResources().getColor(mConfirmTextColor));
        }
        //设置确认按钮文字大小
        if(mConfirmTextSize!=super.RID){
            mTvConfirm.setTextSize(mConfirmTextSize);
        }
        //设置取消按钮文字
        if(mCancelText!=null){
            mTvCancel.setText(mCancelText);
        }
        //设置取消按钮文字颜色
        if(mCancelTextColor!=super.RID){
            mTvCancel.setTextColor(mContext.getResources().getColor(mCancelTextColor));
        }
        //设置取消按钮文字大小
        if(mCancelTextSize!=super.RID){
            mTvCancel.setTextSize(mCancelTextSize);
        }
        //确认按钮显隐
        if(isConfirmBtn){
            mTvConfirm.setVisibility(View.VISIBLE);
        }else{
            mTvConfirm.setVisibility(View.GONE);
        }
        //取消按钮显隐
        if(isCancelBtn){
            mTvCancel.setVisibility(View.VISIBLE);
        }else{
            mTvCancel.setVisibility(View.GONE);
        }

    }

    /**初始化控件**/
    private void initView(){
        mTvTitle= (TextView) getView(R.id.tv_title);
        mTvContent= (TextView) getView(R.id.tv_content);
        mTvConfirm= (TextView) getView(R.id.tv_confirm);
        mTvCancel= (TextView) getView(R.id.tv_cancel);
    }

    @Override
    protected void setListener(){
        if(mTvConfirm.getVisibility()==View.VISIBLE){
            mTvConfirm.setOnClickListener(this);
        }
        if(mTvCancel.getVisibility()==View.VISIBLE){
            mTvCancel.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()== R.id.tv_confirm&&mOnConfirmClickListener!=null){
            mOnConfirmClickListener.onClick(v);
        }else if(v.getId()== R.id.tv_cancel&&mOnCancelClickListener!=null){
            mOnCancelClickListener.onClick(v);
        }
    }

    /**设置dialog背景色**/
    public SyDialogFragment setBackGroundColor(int color){
        this.mBackGroundColor=color;
        return this;
    }

    /**设置标题栏**/
    public SyDialogFragment setTitle(boolean isTitle){
        this.isTitle=isTitle;
        return this;
    }

    /**设置标题栏文字**/
    public SyDialogFragment setTitleText(String message){
        this.mTitleText=message;
        return this;
    }

    /**设置标题栏文字颜色**/
    public SyDialogFragment setTitleTextColor(int color){
        this.mTitleTextColor=color;
        return this;
    }

    /**设置标题栏文字大小**/
    public SyDialogFragment setTitleTextSize(float size){
        this.mTitleTextSize=size;
        return this;
    }

    /**设置内容区文字**/
    public SyDialogFragment setMesssageText(String message){
        this.mContentText=message;
        return this;
    }

    /**设置内容区文字颜色**/
    public SyDialogFragment setMesssageTextColor(int color){
        this.mContentTextColor=color;
        return this;
    }

    /**设置内容区文字大小**/
    public SyDialogFragment setMesssageTextSize(float size){
        this.mContentTextSize=size;
        return this;
    }

    /**确认点击事件**/
    public SyDialogFragment setConfirmBtn(View.OnClickListener onClickListener){
        isConfirmBtn=true;
        this.mOnConfirmClickListener=onClickListener;
        return this;
    }

    /**确认点击事件,可设置文字**/
    public SyDialogFragment setConfirmBtn(String text,View.OnClickListener onClickListener){
        this.mConfirmText=text;
        setConfirmBtn(onClickListener);
        return this;
    }

    /**设置确认按钮文字颜色**/
    public SyDialogFragment setConfirmTextColor(int color){
        this.mConfirmTextColor=color;
        return this;
    }

    /**设置确认按钮文字大小**/
    public SyDialogFragment setConfirmTextSize(float size){
        this.mConfirmTextSize=size;
        return this;
    }

    /**取消点击事件**/
    public SyDialogFragment setCancelBtn(View.OnClickListener onClickListener){
        isCancelBtn=true;
        this.mOnCancelClickListener=onClickListener;
        return this;
    }

    /**取消点击事件,可设置文字**/
    public SyDialogFragment setCancelBtn(String text,View.OnClickListener onClickListener){
        this.mCancelText=text;
        setCancelBtn(onClickListener);
        return this;
    }

    /**设置取消按钮文字颜色**/
    public SyDialogFragment setCancelTextColor(int color){
        this.mCancelTextColor=color;
        return this;
    }

    /**设置确认按钮文字大小**/
    public SyDialogFragment setCancelTextSize(float size){
        this.mCancelTextSize=size;
        return this;
    }

}
//==================使用范例================
//        mSyDialogFragment=new SyDialogFragment();
//
//        mSyDialogFragment.setBackGroundColor(R.color.white)//默认背景白色，在SyDialogFragment中设置
//        .setTitle(true)//默认显示标题栏
//        .setTitleText("申请")//默认显示"提示"，在xml中设置
//        .setTitleTextColor(R.color.blue)//默认黑色，在xml中设置
//        .setTitleTextSize(18f)//默认14sp,在xml中设置
//        .setMesssageText("你确定退出吗？")//默认显示"提示"，在xml中设置
//        .setMesssageTextColor(R.color.black)//默认#666666，在xml中设置
//        .setMesssageTextSize(15f)//默认12sp，在xml中设置
//        .setCancelTextColor(R.color.color_1f1b24)//默认#68c81c，在xml中设置
//        .setCancelTextSize(16f)//默认14sp,在xml中设置
//        .setCancelBtn("cancel", new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {//默认文字为“取消”，在xml中设置
//                  ToastUtil.shortShow("取消");
//                  mSyDialogFragment.dismiss();
//              }
//        })
//        .setConfirmTextColor(R.color.red)//默认#68c81c，在xml中设置
//        .setConfirmTextSize(16f)//默认14sp,在xml中设置
//        .setConfirmBtn("ok", new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {//默认文字为“确定”，在xml中设置
//                  ToastUtil.shortShow("确认");
//                  mSyDialogFragment.dismiss();
//              }
//        })
//        .setUIShadow(false)//默认不弹出背景遮罩
//        .setCancel(false)//默认true
//        .setCancelOnTouchOutside(false)//默认true
//        .showDialog(getSupportFragmentManager());