package com.android.commonlibrary.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

/**
 * Title:ImageView 工具类
 * description:
 * autor:pei
 * created on 2020/3/13
 */
public class ImageViewUtil {

    /**
     * 设置 imageView 的 src
     * @param imv
     * @param rId
     */
    public static void setImageResource(ImageView imv, int rId){
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
    public static void setImageBitmap(ImageView imv, Bitmap bitmap){
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
    public static void setImageDrawable(ImageView imv, int rId, Context context){
        Drawable drawable= ContextCompat.getDrawable(context,rId);
        setImageDrawable(imv,drawable);
    }

    /**
     * 通过uri设置ImageView
     *
     * @param imv
     * @param uri
     */
    public static void setImageURI(ImageView imv, Uri uri){
        if(uri!=null){
            imv.setImageURI(uri);
        }
    }

    /***
     * 通过 ImageView获得 Bitmap
     *
     * @param imv
     * @return
     */
    public static Bitmap getBitmapByImageView(ImageView imv){
        Bitmap bitmap=null;

        imv.setDrawingCacheEnabled(true);
        bitmap=Bitmap.createBitmap(imv.getDrawingCache());
        imv.setDrawingCacheEnabled(false);

        return bitmap;
    }

}
