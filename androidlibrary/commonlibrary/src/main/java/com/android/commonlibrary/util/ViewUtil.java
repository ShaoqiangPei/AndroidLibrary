package com.android.commonlibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

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
    public void setImageURI(ImageView imv,Uri uri){
        if(uri!=null){
            imv.setImageURI(uri);
        }
    }

}