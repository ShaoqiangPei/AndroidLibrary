package com.android.commonlibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Instruction:控件相关工具类
 * <p>
 * Author:pei
 * Date: 2017/7/3
 * Description:
 */
public class ViewUtil {

    /**获取控件宽高**/
    public static void getViewWidth(final View view, final OnViewListener onViewListener) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(onViewListener!=null){
                    onViewListener.onView(view.getWidth(),view.getHeight());
                }
            }
        });
    }

    public interface OnViewListener {
        void onView(int width, int height);
    }

    //===========================================================
    //                   设置view控件宽高
    //===========================================================

    /**
     * 设置view宽度
     * @param width 需要做dp2px转换
     * @param view
     */
    public static void setViewWidth(int width,View view){
        ViewGroup.LayoutParams params=view.getLayoutParams();
        params.width=width;
        view.setLayoutParams(params);
    }

    /**
     * 设置view高度
     * @param height 需要做dp2px转换
     * @param view
     */
    public static void setViewHeight(int height,View view){
        ViewGroup.LayoutParams params=view.getLayoutParams();
        params.height=height;
        view.setLayoutParams(params);
    }

    /**
     * 设置view宽高
     * @param width 需要做dp2px转换
     * @param height 需要做dp2px转换
     * @param view
     */
    public static void setViewSize(int width, int height, View view){
        ViewGroup.LayoutParams params=view.getLayoutParams();
        params.width=width;
        params.height=height;
        view.setLayoutParams(params);
    }

    //===========================================================
    //                   控件可见性
    //===========================================================

    /**
     * 获取控件可见性状态
     * @param view
     * @return
     */
    public static int  getViewVisibity(View view){
        if(view==null){
            return -1;
        }
        return view.getVisibility();
    }

    /**
     * 使控件可见
     * @param view
     */
    public static void setViewVisiable(View view){
        if(view==null){
            return;
        }
        if(view.getVisibility()!= View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 使控件可见
     */
    public static void setViewVisiable(View view, int state){
        if(view==null){
            return;
        }
        if(view.getVisibility() != state){
            view.setVisibility(state);
        }
    }


    /**
     * 使控件不可见
     * @param view
     */
    public static void setViewInvisiable(View view){
        if(view==null){
            return;
        }
        if(view.getVisibility()!= View.INVISIBLE){
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 使控件消失
     * @param view
     */
    public static void setViewGone(View view){
        if(view==null){
            return;
        }
        if(view.getVisibility()!= View.GONE){
            view.setVisibility(View.GONE);
        }
    }

    //===========================================================
    //                   ImageView相关
    //===========================================================

    public static int getColor(Context conetxt, @ColorRes int id){
        return  ContextCompat.getColor(conetxt,id);
    }

    /**
     * 设置 imageView 的 src
     * @param imv
     * @param rId
     */
    public static void setImageResource(ImageView imv,int rId){
        imv.setImageResource(rId);
    }

    /**
     * 设置 imageView 的 background
     *
     * @param imv
     * @param rId
     */
    public static void setBackgroundResource(ImageView imv,int rId){
        imv.setBackgroundResource(rId);
    }

    /**
     * 将 bitmap 设置到 imageView 上
     *
     * @param imv
     * @param bitmap
     */
    public static void setImageBitmap(ImageView imv,Bitmap bitmap){
        if(bitmap!=null) {
            imv.setImageBitmap(bitmap);
        }
    }

    /**
     * 根据图片文件路径设置图片
     *
     * @param imv
     * @param filepath
     */
    public static void setImageBitmapByFilePath(ImageView imv,String filepath){
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        setImageBitmap(imv,bitmap);
    }

    /**
     * 将 drawable 设置到 imageView 上
     *
     * @param imv
     * @param drawable
     */
    public static void setImageDrawable(ImageView imv,Drawable drawable){
        if(drawable!=null){
            imv.setImageDrawable(drawable);
        }
    }

    /**
     * 通过资源Id获取Drawable并设置到ImageView上
     *
     * @param imv
     * @param rId
     * @param context
     */
    public static void setImageDrawable(ImageView imv,int rId,Context context){
        Drawable drawable=ContextCompat.getDrawable(context,rId);
        setImageDrawable(imv,drawable);
    }

    /**
     * 通过uri设置ImageView
     *
     * @param imv
     * @param uri
     */
    public static void setImageURI(ImageView imv,Uri uri){
        if(uri!=null){
            imv.setImageURI(uri);
        }
    }

    //===========================================================
    //                   EditText相关
    //===========================================================
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

    //===========================================================
    //                   TextView相关
    //===========================================================
    /**设置TextView文字粗体(true为粗体,false为正常字体)**/
    public static void setBoldTextStyle(TextView textView, boolean bold) {
        if (textView == null){
            return;
        }
        textView.setTypeface(Typeface.defaultFromStyle(bold ? Typeface.BOLD : Typeface.NORMAL));
    }

}