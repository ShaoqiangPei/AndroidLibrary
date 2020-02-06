package com.android.commonlibrary.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;

import com.android.commonlibrary.app.LibraryConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

/**
 * @fileName FileUtils.java
 */
public class FileUtil {

    /**创建文件夹**/
    public static void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
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
        File dirFile=new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File file=new File(dirPath+ File.separator+"test.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
