package com.android.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.android.commonlibrary.R;


/**
 * Title:带删除按钮的输入框
 * description:
 * autor:pei
 * created on 2019/11/8
 */
public class ClearEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {


    private OnEditerListener mOnEditerListener;
    /**
     * 画线
     */
    private Paint mPaint;
    private int color;
    /**
     * 右边可以清除的小图标
     */
    private Drawable mClearDrawable;
    /**
     * 右边可以清除的小图标
     */
    private Drawable mLeftDrawable;
    /**
     * 标记
     */
    private static final int DRAWABLE_LEFT = 0; //左边图标
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;

    private int mRightDrawableWidth;
    private int mRightDrawableHeight;

    private int mLeftDrawableWidth;
    private int mLeftDrawableHeight;

    public ClearEditText(Context context) {
        super(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnEditerListener(OnEditerListener listener) {
        this.mOnEditerListener = listener;
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        //画线
        drawLine();
        //获取自定义属性值
        initTypedVaule(context, attrs);
        //设置图标
        setDrawable(context);
        //设置监听
        this.setOnFocusChangeListener(this);
        this.addTextChangedListener(this);
        //默认状态
        updateDrawable(true);
    }

    private void drawLine() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3.0f);
        color = Color.parseColor("#a9b7b7");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
    }

    private void initTypedVaule(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.clearEditText);
        //右侧图标尺寸
        mRightDrawableWidth = array.getDimensionPixelSize(R.styleable.clearEditText_rightDrawable_width, dip2px(context, 15));
        mRightDrawableHeight = array.getDimensionPixelSize(R.styleable.clearEditText_rightDrawable_height, dip2px(context, 15));
        //左侧图标尺寸
        mLeftDrawableWidth = array.getDimensionPixelSize(R.styleable.clearEditText_leftDrawable_width, dip2px(context, 20));
        mLeftDrawableHeight = array.getDimensionPixelSize(R.styleable.clearEditText_leftDrawable_height, dip2px(context, 20));
    }

    private void setDrawable(Context context) {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片:左上右下（0123）
        mClearDrawable = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (mClearDrawable == null) {
            //获取默认图标
            mClearDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear_edit_delete);
        }
        //右侧图标尺寸
        mClearDrawable.setBounds(0, 0, mRightDrawableWidth, mRightDrawableHeight);
        //        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //左边图标尺寸
        mLeftDrawable = getCompoundDrawables()[DRAWABLE_LEFT];
        if (mLeftDrawable != null) {
            mLeftDrawable.setBounds(0, 0, mLeftDrawableWidth, mLeftDrawableHeight);
            //            mLeftDrawable.setBounds(0, 0,mLeftDrawable.getIntrinsicWidth(), mLeftDrawable.getIntrinsicHeight());
        }
    }

    // 更新删除图片状态: 当内容不为空，而且获得焦点，才显示右侧删除按钮
    private void updateDrawable(boolean hasFocus) {
        if (length() > 0 && hasFocus) {
            setCompoundDrawables(mLeftDrawable,
                    getCompoundDrawables()[DRAWABLE_TOP], mClearDrawable, getCompoundDrawables()[DRAWABLE_BOTTOM]);
        } else {
            setCompoundDrawables(mLeftDrawable,
                    getCompoundDrawables()[DRAWABLE_TOP], null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
        Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
                    - rightIcon.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        updateDrawable(hasFocus);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //更新状态
        updateDrawable(true);
        if (mOnEditerListener != null) {
            mOnEditerListener.afterTextChanged(ClearEditText.this, s);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void finalize() throws Throwable {
        mClearDrawable = null;
        super.finalize();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public interface OnEditerListener {
        void afterTextChanged(View view, Editable s);
    }

}
