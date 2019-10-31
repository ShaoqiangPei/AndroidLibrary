package com.android.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Title: 带点击效果的 button
 * description:
 * autor:pei
 * created on 2019/10/31
 */
public class MaskButton extends AppCompatButton {

    private static final float[] BG_PRESSED = new float[]{1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
    private static final float[] BG_NOT_PRESSED = new float[]{1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    private boolean mTouchEffect = true;

    public MaskButton(Context context) {
        super(context);
    }

    public MaskButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**设置是否使用点击效果,默认为 true**/
    public MaskButton setTouchEffect(boolean isTouchEffect){
        this.mTouchEffect=isTouchEffect;
        return MaskButton.this;
    }

    @Override
    public void setPressed(boolean pressed) {
        updateViewPressed(pressed);
        super.setPressed(pressed);
    }

    @Override
    public void setClickable(boolean clickable) {
        updateViewClickable(clickable);
        super.setClickable(clickable);
    }

    @SuppressLint("WrongConstant")
    private void updateViewPressed(boolean pressed){
        //如果没有点击效果
        if(!mTouchEffect){
            return;
        }
        Drawable drawable=getCurrentDrawable();
        if(drawable!=null){
            if (pressed) {//点击
                //通过设置滤镜来改变图片亮度
                this.setDrawingCacheEnabled(true);
                drawable.setColorFilter(new ColorMatrixColorFilter(BG_PRESSED));
            } else {
                drawable.setColorFilter(new ColorMatrixColorFilter(BG_NOT_PRESSED));
            }
        }
    }

    private void updateViewClickable(boolean clickable){
        //如果没有点击效果
        if(!mTouchEffect){
            return;
        }
        Drawable drawable=getCurrentDrawable();
        if(drawable!=null) {
            if (clickable) {//点击
                //通过设置滤镜来改变图片亮度
                this.setDrawingCacheEnabled(true);
                drawable.setColorFilter(new ColorMatrixColorFilter(BG_NOT_PRESSED));
            } else {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                drawable.setColorFilter(new ColorMatrixColorFilter(matrix));
            }
        }
    }

    private Drawable getCurrentDrawable() {
        Drawable drawable = this.getBackground();
        if (drawable != null) {
            if (drawable instanceof ColorDrawable) {
                int color = ((ColorDrawable) drawable).getColor();
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(color);
                MaskButton.this.setBackgroundDrawable(gradientDrawable);
                drawable = this.getBackground();
            }
        }
        return drawable;
    }

}

