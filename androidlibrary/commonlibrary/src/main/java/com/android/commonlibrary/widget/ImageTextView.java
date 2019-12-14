package com.android.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.commonlibrary.R;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Instruction:带图标的TextView
 *
 * Author:pei
 * Date: 2017/7/17
 * Description:
 */
@SuppressLint("AppCompatCustomView")
public class ImageTextView extends TextView {

    private static final int DRAWABLE_LEFT=1;//左边
    private static final int DRAWABLE_TOP=2;//上边
    private static final int DRAWABLE_RIGHET=3;//右边
    private static final int DRAWABLE_BOTTOM=4;//下边

    private Drawable mDrawable;//设置的图片
    private int mScaleWidth; // 图片的宽度
    private int mScaleHeight;// 图片的高度
    private int mPosition;// 图片的位置 1左2上3右4下
    private Context mContext;

    public ImageTextView(Context context) {
        super(context);
        this.mContext=context;
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init(context, attrs);

    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);

        mDrawable = typedArray.getDrawable(R.styleable.ImageTextView_drawable);
        mScaleWidth = typedArray
                .getDimensionPixelOffset(
                        R.styleable.ImageTextView_drawableWidth,
                        ScreenUtil.dp2px(20, mContext));
        mScaleHeight = typedArray.getDimensionPixelOffset(
                R.styleable.ImageTextView_drawableHeight,
                ScreenUtil.dp2px(20, mContext));
        //mPosition默认在左边
        mPosition = typedArray.getInt(R.styleable.ImageTextView_position, ImageTextView.DRAWABLE_LEFT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDrawable != null) {
            mDrawable.setBounds(0, 0, mScaleWidth, mScaleHeight);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mPosition) {
            case ImageTextView.DRAWABLE_LEFT://左
                this.setCompoundDrawables(mDrawable, null, null, null);
                break;
            case ImageTextView.DRAWABLE_TOP://上
                this.setCompoundDrawables(null, mDrawable, null, null);
                break;
            case ImageTextView.DRAWABLE_RIGHET://右
                this.setCompoundDrawables(null, null, mDrawable, null);
                break;
            case ImageTextView.DRAWABLE_BOTTOM://下
                this.setCompoundDrawables(null, null, null, mDrawable);
                break;
            default:
                break;
        }
    }


    /**
     * 设置左侧图片并重绘
     *
     * @param context
     */
    public void setDrawableLeft(int drawableRes, Context context) {
        mPosition=ImageTextView.DRAWABLE_LEFT;
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }

    /**
     * 设置上边图片并重绘
     *
     * @param context
     */
    public void setDrawableTOP(int drawableRes, Context context) {
        mPosition=ImageTextView.DRAWABLE_TOP;
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     *
     * @param context
     */
    public void setDrawableRight(int drawableRes, Context context) {
        mPosition=ImageTextView.DRAWABLE_RIGHET;
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }

    /**
     * 设置下边图片并重绘
     *
     * @param context
     */
    public void setDrawableBottom(int drawableRes, Context context) {
        mPosition=ImageTextView.DRAWABLE_BOTTOM;
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }

    /**设置图片宽度**/
    public void setDrawableWidth(int width){
        this.mScaleWidth=ScreenUtil.dp2px(width, mContext);
        invalidate();
    }

    /**获取图片宽度**/
    public float getDrawableWidth(){
        return ScreenUtil.px2dp(mScaleWidth,mContext);
    }

    /**设置图片高度**/
    public void setDrawableHeight(int height){
        this.mScaleHeight=ScreenUtil.dp2px(height, mContext);
        invalidate();
    }

    /**获取图片高度**/
    public float getDrawableHeight(){
        return ScreenUtil.px2dp(mScaleHeight,mContext);
    }

}

