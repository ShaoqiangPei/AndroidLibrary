package com.android.commonlibrary.util;

import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;

import com.android.commonlibrary.app.ComContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:内存卡工具类
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/20
 */
public class SDCardUtil {

    private static final String SDCARD_TOTAL_SIZE="sdcard_total_size";
    private static final String SDCARD_FREE_SIZE="sdcard_free_size";

    /**是否存在sdcard**/
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static Map<String,Long> getSdCardSizeInfo(){
        String path=getInnerSDCardPath();
        if(StringUtil.isNotEmpty(path)){
            android.os.StatFs statfs = new android.os.StatFs(path);
            // 获取SDCard上BLOCK总数
            long nTotalBlocks = statfs.getBlockCountLong();
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSizeLong();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocksLong();
            // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
            long nFreeBlock = statfs.getFreeBlocksLong();
            // 计算SDCard 总容量大小MB
            long total=nTotalBlocks * nBlocSize;
            // 计算 SDCard 剩余大小MB
            long free=nAvailaBlock * nBlocSize;

            Map<String,Long> map=new HashMap<>();
            map.put(SDCardUtil.SDCARD_TOTAL_SIZE,total);
            map.put(SDCardUtil.SDCARD_FREE_SIZE,free);

            return map;
        }
        return null;
    }

    /**sd卡总容量大小MB**/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardTotalSize(){
        Map<String,Long> map=getSdCardSizeInfo();
        if(CollectionUtil.isNotEmpty(map)){
            return map.get(SDCardUtil.SDCARD_TOTAL_SIZE);
        }
        return 0;
    }

    /**sd卡剩余容量大小MB**/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardFreeSize(){
        Map<String,Long> map=getSdCardSizeInfo();
        if(CollectionUtil.isNotEmpty(map)){
            return map.get(SDCardUtil.SDCARD_FREE_SIZE);
        }
        return 0;
    }

    /**判断文件路径的有效性**/
    private static boolean usefulFilePath(String path) {
        if (StringUtil.isNotEmpty(path)) {
            String tempPath = path + "demo.txt";
            File file = new File(tempPath);
            if (file.exists()) {
                return true;
            } else {
                try {
                    if (file.createNewFile()) {
                        file.delete();
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }



    /**获取缓存路径(内存不足时数据会消失，应用删除的时候，数据会被清理掉)**/
    public static String getDiskCachePath() {
        String cachePath = null;
        //手机内部缓存路径
        //------/data/user/0/package_name/cache/
        String innerPath = ComContext.getInstance().getCacheDir().getAbsolutePath() + File.separator;
        if (isSdcardExist()) {
            File cacheFile = ComContext.getInstance().getExternalCacheDir();
            if (cacheFile != null) {
                //sdcard上缓存路径
                //--------/storage/emulated/0/Android/data/package_name/cache/
                String ousidePath = cacheFile.getAbsolutePath() + File.separator;
                if (usefulFilePath(ousidePath)) {
                    cachePath = ousidePath;
                } else {
                    //若此路径下无法执行读写,则是android4.4系统为防止外置sdk存储混乱，不好管理，禁用了外置sdk的读写
                    cachePath = innerPath;
                }
            } else {
                //AppContext.getInstance().getExternalCacheDir()可能为空，原因是sdcard死锁，需要重启手机
                cachePath = innerPath;
            }
        }else{
            cachePath = innerPath;
        }
        return cachePath;
    }

    /**获取存储路径(不会因为内存问题导致数据丢失，应用删除的时候，数据会被清理掉)**/
    public static String getDiskFilePath(String fileName){
        String path=null;
        if(StringUtil.isNotEmpty(fileName)) {
            String innerpath= ComContext.getInstance().getFilesDir().getAbsolutePath() + File.separator+fileName;
            if (isSdcardExist()) {
                //sdcard上缓存路径
                //--------/storage/emulated/0/Android/data/package_name/files/file_name
                String dirpath= ComContext.getInstance().getExternalFilesDir("diskdata").getAbsolutePath()+ File.separator;
                path = dirpath+fileName;
                if(!usefulFilePath(dirpath)){
                    //手机内部缓存路径
                    //------/data/user/0/package_name/files/file_name
                    path = innerpath;
                }
            } else {
                //手机内部缓存路径
                //------/data/user/0/package_name/files/file_name
                path = innerpath;
            }
        }
        return path;
    }

    /**获取内置sdcard路径(数据会保存，应用删除的时候，数据不会被清理掉)**/
    public static String getInnerSDCardPath(){
        if(isSdcardExist()){
            //------/storage/emulated/0/
            return Environment.getExternalStorageDirectory() + File.separator;
        }
        return null;
    }

}
