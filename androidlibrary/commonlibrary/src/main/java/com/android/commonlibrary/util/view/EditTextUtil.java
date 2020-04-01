package com.android.commonlibrary.util.view;

import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.android.commonlibrary.util.AppUtil;
import com.android.commonlibrary.util.StringUtil;

/**
 * Title: EditText 工具类
 * description:
 * autor:pei
 * created on 2020/3/13
 */
public class EditTextUtil {

    /**设置光标显示在输入框尾部**/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setEndEditTextCursor(EditText edtText){
        String value=edtText.getText().toString();
        if(StringUtil.isNotEmpty(value)){
            int length=value.length();
            int sdkVersion= AppUtil.getSDKVersion();
            if(sdkVersion> Build.VERSION_CODES.LOLLIPOP||sdkVersion==Build.VERSION_CODES.LOLLIPOP){
                int maxLength=getMaxLength(edtText);
                if(maxLength>0){
                    length = length>= maxLength ? maxLength : length;
                }
            }
            //将光标移至文字末尾
            edtText.setSelection(length);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static int getMaxLength(EditText editText) {
        int max = 0;
        InputFilter inputFilter[]=editText.getFilters();
        if(inputFilter!=null&&inputFilter.length>0&& inputFilter[0]!=null&&(inputFilter[0] instanceof InputFilter.LengthFilter)){
            max = ((InputFilter.LengthFilter) inputFilter[0]).getMax();
        }
        return max;
    }
}
