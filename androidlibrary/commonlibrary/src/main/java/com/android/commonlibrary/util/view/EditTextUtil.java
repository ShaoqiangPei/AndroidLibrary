package com.android.commonlibrary.util.view;

import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import com.android.commonlibrary.util.StringUtil;

/**
 * Title: EditText 工具类
 * description:
 * autor:pei
 * created on 2020/3/13
 */
public class EditTextUtil {

    /**设置光标显示在输入框尾部**/
    public static void setEndEditTextCursor(EditText edtText){
        String value=edtText.getText().toString();
        if(StringUtil.isNotEmpty(value)){
            //将光标移至文字末尾
            edtText.setSelection(value.length());
        }
    }

    /**设置输入框最大数字输入长度**/
    public static void setEditMaxNumLength(EditText edtText,int maxLength){
        edtText.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型为数字
        setEditMaxLength(edtText,maxLength);
    }

    /**设置输入框最大输入长度**/
    public static void setEditMaxLength(EditText edtText,int maxLength){
        edtText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }
}
