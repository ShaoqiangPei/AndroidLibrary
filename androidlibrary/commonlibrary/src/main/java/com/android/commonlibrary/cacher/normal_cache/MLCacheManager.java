package com.android.commonlibrary.cacher.normal_cache;

import com.android.commonlibrary.util.GsonUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.collection.LruCache;

/**
 * Title:MemoryLruCache管理类
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/23
 */
public class MLCacheManager<T>implements CacheListener {

    private static final String EXCEPTION_TAG= MLCacheManager.class.getSimpleName();

    private LruCache<String, Object> mLruCache= new MemoryLruCache<>();

    @Override
    public void putString(String key, String value) {
        if (isNotEmpty(mLruCache, key) && StringUtil.isNotEmpty(value)) {
            mLruCache.put(key, value);
        }
    }

    @Override
    public String getString(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                return obj.toString();
            }
        }
        return null;
    }

    @Override
    public void putInt(String key, int value) {
        if(isNotEmpty(mLruCache, key)){
            mLruCache.put(key, value);
        }
    }

    @Override
    public int getInt(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                try {
                    int value=Integer.valueOf(obj.toString());
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new NumberFormatException(EXCEPTION_TAG+"====value Integer NumberFormat error");
                }
            }
        }
        return 0;
    }

    @Override
    public void putFloat(String key, float value) {
        if(isNotEmpty(mLruCache, key)){
            mLruCache.put(key, value);
        }
    }

    @Override
    public float getFloat(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                try {
                    Float value=Float.valueOf(obj.toString());
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new NumberFormatException(EXCEPTION_TAG+"====value Float NumberFormat error");
                }
            }
        }
        return 0;
    }

    @Override
    public void putDouble(String key, double value) {
        if(isNotEmpty(mLruCache, key)){
            mLruCache.put(key, value);
        }
    }

    @Override
    public double getDouble(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                try {
                    Double value=Double.valueOf(obj.toString());
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new NumberFormatException(EXCEPTION_TAG+"====value Double NumberFormat error");
                }
            }
        }
        return 0;
    }

    @Override
    public void putLong(String key, Long value) {
        if(isNotEmpty(mLruCache, key)){
            mLruCache.put(key, value);
        }
    }

    @Override
    public long getLong(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                try {
                    Long value=Long.valueOf(obj.toString());
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new NumberFormatException(EXCEPTION_TAG+"====value Long NumberFormat error");
                }
            }
        }
        return 0;
    }

    @Override
    public void putBoolean(String key, boolean value) {
        if(isNotEmpty(mLruCache, key)){
            mLruCache.put(key, value);
        }
    }

    @Override
    public boolean getBoolean(String key) {
        if(isNotEmpty(mLruCache, key)){
            Object obj=mLruCache.get(key);
            if(obj!=null){
                try {
                    boolean value= (boolean) obj;
                    return value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public void putObject(String key, Object value) {
        String jsonString= GsonUtil.gsonString(value);
        putString(key,jsonString);
    }

    @Override
    public Object getObject(String key, Class cls) {
        String jsonString=getString(key);
        Object obj= GsonUtil.gsonToBean(jsonString,cls);

        return obj;
    }

    @Override
    public void putList(String key, List list) {
        String jsonString = GsonUtil.listToString(list);
        putString(key, jsonString);
    }

    @Override
    public List<T>getList(String key, Class cls) {
        List<T>list=null;
        String jsonString=getString(key);
        if(StringUtil.isNotEmpty(jsonString)){
            list= GsonUtil.jsonToList(jsonString,cls);
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public void putMap(String key, Map map) {
       String jsonString= GsonUtil.MapToJsonString(map);
       putString(key,jsonString);
    }

    @Override
    public Map getMap(String key) {
        Map<String, T>map=null;
        String jsonString=getString(key);
        if(StringUtil.isNotEmpty(jsonString)){
            map= GsonUtil.gsonToMaps(jsonString);
        }
        return map;
    }

    @Override
    public void remove(String key) {
        if(isNotEmpty(mLruCache,key)){
            mLruCache.remove(key);
        }
    }

    @Override
    public void clear() {
        mLruCache.evictAll();
    }

    private boolean isNotEmpty(LruCache lruCache, String key) {
        if (lruCache != null && StringUtil.isNotEmpty(key)) {
            return true;
        }
        return false;
    }

}
