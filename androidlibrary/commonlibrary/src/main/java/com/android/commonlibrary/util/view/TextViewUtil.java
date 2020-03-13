package com.android.commonlibrary.util.view;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

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
    public void setLeftDrawablePadding(TextView textView, int padding) {
        textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(padding);
    }

    /**
     * 设置右边返回键文本和图片间距
     * @param textView textview 控件
     * @param padding 需要做dp2px转换
     */
    public void setRightDrawablePadding(TextView textView, int padding) {
        textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(padding);
    }
}
