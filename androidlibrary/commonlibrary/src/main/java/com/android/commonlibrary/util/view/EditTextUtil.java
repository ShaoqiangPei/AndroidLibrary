package com.android.commonlibrary.util.view;

import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.commonlibrary.R;
import com.android.commonlibrary.util.AppUtil;
import com.android.commonlibrary.util.StringUtil;

import java.lang.reflect.Field;

/**
 * Title: EditText 工具类
 * description:
 * autor:pei
 * created on 2020/3/13
 */
public class EditTextUtil {

    //数字
    public static final String DIGIT_NUMBER="0123456789";
    //小写字母
    public static final String DIGIT_LOWER_LETTER="abcdefghijklmnopqrstuvwxyz";
    //大写字母
    public static final String DIGIT_CAPITAL_LETTER="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //字母
    public static final String DIGIT_LETTER=DIGIT_LOWER_LETTER+DIGIT_CAPITAL_LETTER;
    //数字+小写字母
    public static final String DIGIT_NUMBER_LOWER_LETTER=DIGIT_NUMBER+DIGIT_LOWER_LETTER;
    //数字+大写字母
    public static final String DIGIT_NUMBER_CAPITAL_LETTER=DIGIT_NUMBER+DIGIT_CAPITAL_LETTER;
    //数字+字母
    public static final String DIGIT_NUMBER_LETTER=DIGIT_NUMBER+DIGIT_LETTER;

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

    /***
     * 设置光标颜色
     *
     * 或者直接在xml中设置：  android:textCursorDrawable="@drawable/ic"
     *
     * @param editText
     * @param drawableId：R.drawable.ic
     */
    public static void setTextCursorDrawable(EditText editText, int drawableId) {
        if (editText != null && drawableId != 0) {
            try {
                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                f.set(editText, drawableId);
            } catch (Exception ignored) {

            }
        }
    }

    /***
     * 设置输入框 digits 属性
     *
     * 注: android:digits=""的代码实现
     */
    public static void setDigits(EditText editText,String digit) {
        if (StringUtil.isNotEmpty(digit)) {
            editText.setKeyListener(DigitsKeyListener.getInstance(digit));
        }
    }

}
