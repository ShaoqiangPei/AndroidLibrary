package com.android.commonlibrary.cacher.normal_cache;

/**
 * Title:缓存相关配置
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/24
 */
public class CacheConfig {
    /**内存缓存大小,默认为当前应用可用最大内存的八分之一**/
    public static final int MEMORY_CACHE_SIZE=(int)Runtime.getRuntime().maxMemory() / 1024 / 8;
    /**硬盘缓存大小，默认10M**/
    public static final long DISK_CACHE_SIZE=10*1024*1024;
}
