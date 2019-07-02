package com.android.commonlibrary.cacher.normal_cache;


import androidx.collection.LruCache;

/**
 * Title:内存缓存
 * Description:近期最少使用算法
 * <p>
 * Created by pei
 * Date: 2017/10/23
 */
public class MemoryLruCache<K,V> extends LruCache<K,V> {

    // 构造方法传入当前应用可用最大内存的八分之一
    public MemoryLruCache() {
        super(CacheConfig.MEMORY_CACHE_SIZE);
    }

    @Override
    protected int sizeOf(K key, V value) {
        return super.sizeOf(key, value);
    }

    //当缓存被移除时调用，第一个参数是表明缓存移除的原因，true表示被LruCache移除，false表示被主动remove移除，可不重写
    @Override
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    // 当get方法获取不到缓存的时候调用，如果需要创建自定义默认缓存，可以在这里添加逻辑，可不重写
    @Override
    protected V create(K key) {
        return super.create(key);
    }

}
