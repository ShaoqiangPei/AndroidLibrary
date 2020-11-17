package com.android.commonlibrary.dialog_fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.commonlibrary.R;

/**
 * Title:用户协议dialog
 * description:
 * autor:pei
 * created on 2020/8/21
 */
public class AgreementDialog extends AppDialogFragment {

    private TextView mTvTitle;//标题栏
    private TextView mTvContent;//内容区
    private Button mBtnCancel;//取消
    private Button mBtnConfirm;//确定

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
    private int mConfirmBackground=super.RID;//设置确认按钮背景
    private String mCancelText;//默认取消按钮文字在xml中设置
    private int mCancelTextColor=super.RID;//默认取消按钮文字颜色在xml中设置
    private float mCancelTextSize=super.RID;//默认取消按钮文字大小在xml中设置
    private int mCancelBackground=super.RID;//设置取消按钮背景

    private double mScaleWidth=0d;//弹框宽度比
    private double mScaleHeight=0d;//弹框高度比

    //协议点击监听事件
    private View.OnClickListener mOnUserListener;
    private View.OnClickListener mOnPrivacyListener;
    //确定，取消点击事件监听
    private View.OnClickListener mOnConfirmClickListener;
    private View.OnClickListener mOnCancelClickListener;
    private OnActionListener mOnActionListener;//对tvContent内容做特殊处理,如变色,下划线等

    @Override
    protected double[] getWindowSize() {
        if(mScaleWidth==0){
            mScaleWidth=0.7d;
        }
        if(mScaleHeight==0){
            mScaleHeight=super.WRAP_CONTENT;
        }
        return new double[]{mScaleWidth,mScaleHeight};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_agreement;
    }

    @Override
    protected void initData() {
        //初始化控件
        initView();
        //初始化基本数据
        initBaseData();
    }

    /**初始化控件**/
    private void initView(){
        mTvTitle= (TextView) getView(R.id.tv_title);
        mTvContent= (TextView) getView(R.id.tv_content);
        mBtnCancel= (Button) getView(R.id.btn_cancel);
        mBtnConfirm= (Button) getView(R.id.btn_confirm);
    }

    /**初始化基本数据**/
    private void initBaseData(){
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
            mBtnConfirm.setText(mConfirmText);
        }
        //设置确认按钮文字颜色
        if(mConfirmTextColor!=super.RID){
            mBtnConfirm.setTextColor(mContext.getResources().getColor(mConfirmTextColor));
        }
        //设置确认按钮文字大小
        if(mConfirmTextSize!=super.RID){
            mBtnConfirm.setTextSize(mConfirmTextSize);
        }
        //设置确认按钮背景
        if(mConfirmBackground!=super.RID){
            mBtnConfirm.setBackgroundResource(mConfirmBackground);
        }
        //设置取消按钮文字
        if(mCancelText!=null){
            mBtnCancel.setText(mCancelText);
        }
        //设置取消按钮文字颜色
        if(mCancelTextColor!=super.RID){
            mBtnCancel.setTextColor(mContext.getResources().getColor(mCancelTextColor));
        }
        //设置取消按钮文字大小
        if(mCancelTextSize!=super.RID){
            mBtnCancel.setTextSize(mCancelTextSize);
        }
        //设置取消按钮背景
        if(mCancelBackground!=super.RID){
            mBtnCancel.setBackgroundResource(mCancelBackground);
        }
    }

    @Override
    protected void setListener() {
        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        //需要对tvContent内容做特殊处理才调用的监听
        if(mOnActionListener!=null){
           mOnActionListener.action(mTvContent,mOnUserListener,mOnPrivacyListener);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()== R.id.btn_confirm&&mOnConfirmClickListener!=null){
            mOnConfirmClickListener.onClick(v);
        }else if(v.getId()== R.id.btn_cancel&&mOnCancelClickListener!=null){
            mOnCancelClickListener.onClick(v);
        }
    }

    /***
     * 设置弹框显示尺寸
     *
     * 不调用的话,默认宽度为屏幕的 0.7,高度自适应
     *
     * @param scaleWidth
     * @param scaleHeight
     * @return
     */
    public AgreementDialog setDialogSize(double scaleWidth,double scaleHeight){
        this.mScaleWidth=scaleWidth;
        this.mScaleHeight=scaleHeight;
        return this;
    }

    /**用户协议点击事件**/
    public AgreementDialog setUserListener(View.OnClickListener onClickListener){
        this.mOnUserListener=onClickListener;
        return this;
    }

    /**隐私协议点击事件**/
    public AgreementDialog setPrivacyListener(View.OnClickListener onClickListener){
        this.mOnPrivacyListener=onClickListener;
        return this;
    }

    /***
     * 需要对 tvContent内容做特殊处理才调用的监听
     *
     * 注：要在设置完setUserListener(View.OnClickListener onClickListener)和
     *     setPrivacyListener(View.OnClickListener onClickListener)之后调用，
     *     以保证onUserListener和onPrivacyListener不为空
     *
     * @param listener  需要对 tvContent内容做特殊处理才调用的监听
     * @return
     */
    public AgreementDialog setOnActionListener(OnActionListener listener){
        this.mOnActionListener=listener;
        return this;
    }

    /**确认点击事件**/
    public AgreementDialog setConfirmBtn(View.OnClickListener onClickListener){
        this.mOnConfirmClickListener=onClickListener;
        return this;
    }

    /**取消点击事件**/
    public AgreementDialog setCancelBtn(View.OnClickListener onClickListener){
        this.mOnCancelClickListener=onClickListener;
        return this;
    }

    /***
     * 需要对 tvContent内容做特殊处理才调用的监听
     *
     * 要在设置完setUserListener(View.OnClickListener onClickListener)和
     *  setPrivacyListener(View.OnClickListener onClickListener)之后调用，
     *  以保证onUserListener和onPrivacyListener不为空
     */
    public interface OnActionListener{
        /***
         *
         * @param tvContent 显示用户协议弹框中具体内容的TextView
         * @param userListener 用户协议监听
         * @param privacyListener 隐私协议监听
         */
        void action(TextView tvContent,View.OnClickListener userListener,View.OnClickListener privacyListener);
    }

    /**设置dialog背景色**/
    public AgreementDialog setBackGroundColor(int color){
        this.mBackGroundColor=color;
        return this;
    }

    /**设置标题栏**/
    public AgreementDialog setTitle(boolean isTitle){
        this.isTitle=isTitle;
        return this;
    }

    /**设置标题栏文字**/
    public AgreementDialog setTitleText(String message){
        this.mTitleText=message;
        return this;
    }

    /**设置标题栏文字颜色**/
    public AgreementDialog setTitleTextColor(int color){
        this.mTitleTextColor=color;
        return this;
    }

    /**设置标题栏文字大小**/
    public AgreementDialog setTitleTextSize(float size){
        this.mTitleTextSize=size;
        return this;
    }

    /**设置内容区文字**/
    public AgreementDialog setMesssageText(String message){
        this.mContentText=message;
        return this;
    }

    /**设置内容区文字颜色**/
    public AgreementDialog setMesssageTextColor(int color){
        this.mContentTextColor=color;
        return this;
    }

    /**设置内容区文字大小**/
    public AgreementDialog setMesssageTextSize(float size){
        this.mContentTextSize=size;
        return this;
    }

    /**确认点击事件,可设置文字**/
    public AgreementDialog setConfirmBtn(String text,View.OnClickListener onClickListener){
        this.mConfirmText=text;
        setConfirmBtn(onClickListener);
        return this;
    }

    /**设置确认按钮文字颜色**/
    public AgreementDialog setConfirmTextColor(int color){
        this.mConfirmTextColor=color;
        return this;
    }

    /**设置确认按钮文字大小**/
    public AgreementDialog setConfirmTextSize(float size){
        this.mConfirmTextSize=size;
        return this;
    }

    /***
     * 设置确认按钮背景
     *
     * @param confirmBackground  R.drawable.c ,  R.color.red
     * @return
     */
    public AgreementDialog setConfirmBackground(int confirmBackground){
        this.mConfirmBackground=confirmBackground;
        return this;
    }

    /**取消点击事件,可设置文字**/
    public AgreementDialog setCancelBtn(String text,View.OnClickListener onClickListener){
        this.mCancelText=text;
        setCancelBtn(onClickListener);
        return this;
    }

    /**设置取消按钮文字颜色**/
    public AgreementDialog setCancelTextColor(int color){
        this.mCancelTextColor=color;
        return this;
    }

    /**设置取消按钮文字大小**/
    public AgreementDialog setCancelTextSize(float size){
        this.mCancelTextSize=size;
        return this;
    }

    /***
     * 设置确认按钮背景
     *
     * @param cancelBackground  R.drawable.c ,  R.color.red
     * @return
     */
    public AgreementDialog setCancelmBackground(int cancelBackground){
        this.mCancelBackground=cancelBackground;
        return this;
    }

}
