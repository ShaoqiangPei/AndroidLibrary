package com.android.commonlibrary.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Title:截屏分享
 * Description:
 *   需要用户读写权限
 *   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *
 * Created by pei
 * Date: 2017/12/6
 */
public class ShotShareUtil {

    /***
     * 截屏分享，供外部调用
     *
     * 图片路径：SDCardUtil.getDiskCachePath()+fileName
     *
     * @param fileName 图片名称,如："share.png"
     * @param context
     */
    public static void shotShare(String fileName,Context context){
        if(StringUtil.isNotEmpty(fileName)) {
            //截屏
            String path = screenShot(fileName, context);
            //分享
            if (StringUtil.isNotEmpty(path)) {
                ShareImage(context, path);
            }
        }
    }

    /**
     * 获取截屏
     *
     * @param fileName 图片名称,如："share.png"
     * @param context
     * @return
     */
    private static String screenShot(String fileName,Context context){
        String imagePath=null;
        Bitmap bitmap= ScreenUtil.snapShotWithoutStatusBar(context);
        if(bitmap!=null){
            try {
                // 图片文件路径
                imagePath = SDCardUtil.getDiskCachePath()+fileName;
                File file = new File(imagePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                return imagePath;
            } catch (Exception e) {
                LogUtil.e("====screenshot:error====" + e.getMessage());
            }
        }
        return null;
    }

    /**分享**/
    private static void ShareImage(Context context, String imagePath){
        if (imagePath != null){
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            File file = new File(imagePath);
            Uri uri=null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // UpdateConfig.FILE_PROVIDER_AUTH 即是在清单文件中配置的authorities
                String authority= MainfastUtil.getProviderAuthority(context);
                uri= FileProvider.getUriForFile(context, authority,file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);// 分享的内容
            intent.setType("image/*");// 分享发送的数据类型
            Intent chooser = Intent.createChooser(intent, "Share screen shot");
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(chooser);
            }
        } else {
            ToastUtil.shortShow("先截屏，再分享");
        }
    }

}
