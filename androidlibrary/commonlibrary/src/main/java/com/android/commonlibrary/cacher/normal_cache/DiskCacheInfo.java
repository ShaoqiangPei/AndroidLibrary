package com.android.commonlibrary.cacher.normal_cache;


import com.android.commonlibrary.app.ComContext;
import com.android.commonlibrary.util.SDCardUtil;

import java.io.File;
import java.math.BigDecimal;

/**
 * Title:DiskCache硬盘缓存
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/26
 */
public class DiskCacheInfo {

    /**
     * @return
     * @throws Exception
     *             获取当前缓存
     */
    public static String getTotalCacheSize() {
        long cacheSize = getFolderSize(ComContext.getInstance().getCacheDir());
        if (SDCardUtil.isSdcardExist()) {
            cacheSize += getFolderSize(ComContext.getInstance().getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 删除缓存
     */
    public static void clearAllCache(){
        deleteDir(ComContext.getInstance().getCacheDir());
        if(SDCardUtil.isSdcardExist()){
            deleteDir(ComContext.getInstance().getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir){
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int size = 0;
            if (children != null){
                size = children.length;
                for (int i = 0; i < size; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        if (dir == null) {
            return true;
        } else {
            return dir.delete();
        }
    }

    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
    // 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->
    // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file){
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            int size2 = 0;
            if (fileList != null) {
                size2 = fileList.length;
                for (int i = 0; i < size2; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     * 计算缓存的大小
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
