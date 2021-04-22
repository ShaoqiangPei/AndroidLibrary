package com.android.commonlibrary.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import com.android.commonlibrary.app.LibraryConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.DecimalFormat;

/**
 * Title:File文件工具类
 * description:
 * autor:pei
 * created on 2020/3/6
 */
public class FileUtil {

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return  返回文件夹对象
     */
    public static File createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public static boolean isFileExist(String path){
        File file = new File(path);
        return file.exists();
    }

    /***
     * 无论文件是否存在都重新创建
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 文件不存在时创建
     * @param path
     * @return
     */
    public static File createFileIfUnExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 重命名文件
     * 注: 重命名后源文件会自动删除，所以不需要我们手动去删除
     *
     * @param file 源文件
     * @param newName 新文件名(含后缀),如：test.txt
     * @return 重命名后的文件对象,为null表示重命名失败
     */
    public static File reName(final File file, final String newName) {
        if(file==null|| StringUtil.isEmpty(newName)){
            LogUtil.i("=====源文件为null或新文件名为null========");
            return null;
        }
        if(!file.exists()){
            LogUtil.i("=====源文件不存在========");
            return null;
        }
        if(newName.equals(file.getName())){
            LogUtil.i("=====重命名文件名与源文件名重复========");
            return null;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        boolean flag=file.renameTo(newFile);
        if(flag){
            return newFile;
        }
        return null;
    }

    /**删除文件**/
    public static void delFile(String path){
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    /**获取file的uri**/
    public static Uri getUriFromFile(String path) {
        File file = new File(path);
        return Uri.fromFile(file);
    }

    /**删除某目录下所有文件**/
    public static void deleteAllFiles(File root) {
        if (!root.exists()) {
            return;
        }
        if (!root.isDirectory()){
            return;
        }
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**删除某目录下所有文件**/
    public static void deleteAllFiles(String path) {
        File file=new File(path);
        if(file.exists()){
            deleteAllFiles(file);
        }
    }

    /***
     * 判断某个文件是否存在于Assets文件夹中
     *
     * @param fileName：Assets文件夹下文件名,如 order_tip.mp3
     * @return
     */
    public static boolean isExistInAssets(String fileName) {
        AssetManager assetManager = LibraryConfig.getInstance().getApplication().getAssets();
        try {
            String names[] = assetManager.list("");
            for (int i = 0; i < names.length; i++) {
                if (null != names[i] && names[i].equals(fileName.trim())) {
                    LogUtil.i(fileName + "存在于Assets文件夹下");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(fileName + "不存在于Assets文件夹下");
            return false;
        }
        return false;
    }

    /***
     * 根据 assets 文件夹下文件路径设置文件
     *
     * 原理：assets文件夹下文件无法通过直接设置文件路径读取文件
     *      (assets文件夹下文件路径为：file:///android_asset/文件名)
     *      需要将assets文件夹下文件拷贝出来,然后读取拷贝文件路径
     *      此处是将assets文件夹下文件拷贝到缓存中
     *
     * @param fileName
     * @return
     */
    public static String getcopyAssetToCachePath(String fileName,Context context){
        if(context==null){
            throw new NullPointerException("=== context不能为null ====");
        }
        if(StringUtil.isNotEmpty(fileName)) {
            //将asset文件写入缓存
            try {
                File cacheDir = context.getCacheDir();
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                File outFile = new File(cacheDir, fileName);
                if (!outFile.exists()) {
                    boolean res = outFile.createNewFile();
                    if (!res) {
                        LogUtil.e("=======创建文件失败========");
                        return null;
                    }
                } else {
                    if (outFile.length() > 10) {//表示已经写入一次
                        return outFile.getAbsolutePath();
                    }
                }
                InputStream is = context.getAssets().open(fileName);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
                return outFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            LogUtil.e("=====文件名不能为null======");
        }
        return null;
    }

    /**删除由于 读取 assets文件夹下视频文件产生的缓存 **/
    public static boolean deleteCacheFile(String filePath) {
        if(StringUtil.isNotEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                    return true;
                } else {
                    LogUtil.e("=====filePath=" + filePath + " 路径不是文件(可能是文件夹?)");
                }
            } else {
                LogUtil.e("=====filePath=" + filePath + " 文件不存在");
            }
        }else{
            LogUtil.e("=====filePath不能为null=====");
        }
        return false;
    }

    /**格式化文件大小**/
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "0B";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 写文件示例
     *
     * @param str
     * @param context
     */
    public static void writeFile(String str, Context context) {
        String dirPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dirPath = Environment.getExternalStorageDirectory() + File.separator + "LogCat";
        } else {
            dirPath = context.getFilesDir().getAbsolutePath() + File.separator + "LogCat";
        }
        //建文件夹
        createDirFile(dirPath);
        //建文件
        File file=createFileIfUnExist(dirPath+ File.separator+"test.txt");
        //写数据
        try {
            Writer out=new FileWriter(file,true);
            out.write(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
