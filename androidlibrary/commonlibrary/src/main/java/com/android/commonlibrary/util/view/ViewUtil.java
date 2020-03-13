package com.android.commonlibrary.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.android.commonlibrary.util.ScreenUtil;
import com.android.commonlibrary.util.StringUtil;

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
    //                   设置资源 id
    //===========================================================

    /**
     * 获取资源 id
     * @param conetxt
     * @param id
     * @return
     */
    public static int getColor(Context conetxt, @ColorRes int id){
        return  ContextCompat.getColor(conetxt,id);
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

}