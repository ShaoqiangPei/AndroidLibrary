package com.android.commonlibrary.util.view;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import com.android.commonlibrary.util.ScreenUtil;

/**
 * Title:TextView 工具类
 * description:
 * autor:pei
 * created on 2020/3/13
 */
public class TextViewUtil {

    /**设置TextView文字粗体(true为粗体,false为正常字体)**/
    public static void setBoldTextStyle(TextView textView, boolean bold) {
        if (textView == null){
            return;
        }
        textView.setTypeface(Typeface.defaultFromStyle(bold ? Typeface.BOLD : Typeface.NORMAL));
    }

    /**
     * 设置左边文本和图片间距
     * @param textView textview 控件
     * @param padding 需要做dp2px转换
     */
    public static void setLeftDrawablePadding(TextView textView, int padding) {
        textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(padding);
    }

    /**
     * 设置右边返回键文本和图片间距
     * @param textView textview 控件
     * @param padding 需要做dp2px转换
     */
    public static void setRightDrawablePadding(TextView textView, int padding) {
        textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(padding);
    }

    /***
     * 设置文字大小缩放配置(兼容不同屏幕分辨率时调用)
     *
     * @param textView
     * @param minTextSize  最小文字大小
     * @param maxTextSize  最大文字大小
     * @param sizeStep  文字大小缩放粒度(一般设置为 1)
     */
    public static void setTextSizeAutoConfig(AppCompatTextView textView, int minTextSize, int maxTextSize, int sizeStep){
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,minTextSize,maxTextSize,sizeStep, TypedValue.COMPLEX_UNIT_SP);
    }

    /***
     * 设置文字大小(像素设置)
     *
     * @param textView
     * @param textSize 文字大小(单位：px)
     */
    public static void setTextSzieByPx(TextView textView,float textSize){
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize); //像素
    }

    /***
     * 设置文字大小(sp设置)
     *
     * @param textView
     * @param textSize 文字大小(单位：sp)
     */
    public static void setTextSzieBySp(TextView textView,float textSize){
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize); //sp
    }

    /***
     * 设置文字大小(dip设置)
     *
     * @param textView
     * @param textSize 文字大小(单位：dip)
     */
    public static void setTextSzieByDp(TextView textView,float textSize){
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSize); //dp
    }

}
