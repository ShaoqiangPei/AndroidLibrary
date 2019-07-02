package com.android.commonlibrary.cacher.normal_cache;


import com.android.commonlibrary.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * Title:缓存使用(此缓存类提供给外部使用)
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/30
 */
public class Cache<T>implements CacheListener {

    private MLCacheManager mlCacheManager;
    private DLCacheManager mDlCacheManager;

    private Cache(){
        mlCacheManager=new MLCacheManager();
        mDlCacheManager=new DLCacheManager();
    }

    private static class Holder {
        private static Cache instance = new Cache();
    }

    public static Cache getInstance(){
        return Holder.instance;
    }

    @Override
    public void putString(String key, String value) {
        if(StringUtil.isNotEmpty(key)&& StringUtil.isNotEmpty(value)){
            mlCacheManager.putString(key,value);
            mDlCacheManager.putString(key,value);
        }
    }

    @Override
    public String getString(String key) {
        String value = mlCacheManager.getString(key);
        if(StringUtil.isEmpty(value)){
            value=mDlCacheManager.getString(key);
        }
        return value;
    }

    @Override
    public void putInt(String key, int value) {
        if(StringUtil.isNotEmpty(key)){
            mlCacheManager.putInt(key,value);
            mDlCacheManager.putInt(key,value);
        }
    }

    @Override
    public int getInt(String key) {
        int value=0;
        if(StringUtil.isNotEmpty(key)){
            value=mlCacheManager.getInt(key);
            if(value==0){
                value=mDlCacheManager.getInt(key);
            }
        }
        return value;
    }

    @Override
    public void putFloat(String key, float value) {
        if(StringUtil.isNotEmpty(key)){
            mlCacheManager.putFloat(key,value);
            mDlCacheManager.putFloat(key,value);
        }
    }

    @Override
    public float getFloat(String key) {
        float value=0f;
        if(StringUtil.isNotEmpty(key)){
            value=mlCacheManager.getFloat(key);
            if(value==0){
                value=mDlCacheManager.getFloat(key);
            }
        }
        return value;
    }

    @Override
    public void putDouble(String key, double value) {
        if(StringUtil.isNotEmpty(key)){
            mlCacheManager.putDouble(key,value);
            mDlCacheManager.putDouble(key,value);
        }
    }

    @Override
    public double getDouble(String key) {
        double value=0d;
        if(StringUtil.isNotEmpty(key)){
            value=mlCacheManager.getDouble(key);
            if(value==0){
                value=mDlCacheManager.getDouble(key);
            }
        }
        return value;
    }

    @Override
    public void putLong(String key, Long value) {
        if(StringUtil.isNotEmpty(key)){
            mlCacheManager.putLong(key,value);
            mDlCacheManager.putLong(key,value);
        }
    }

    @Override
    public long getLong(String key) {
        long value=0L;
        if(StringUtil.isNotEmpty(key)){
            value=mlCacheManager.getLong(key);
            if(value==0){
                value=mDlCacheManager.getLong(key);
            }
        }
        return value;
    }

    @Override
    public void putBoolean(String key, boolean value) {
        if(StringUtil.isNotEmpty(key)){
            mlCacheManager.putBoolean(key,value);
            mDlCacheManager.putBoolean(key,value);
        }
    }

    @Override
    public boolean getBoolean(String key) {
        boolean flag=false;
        if(StringUtil.isNotEmpty(key)){
            flag=mlCacheManager.getBoolean(key);
            if(!flag){
                flag=mDlCacheManager.getBoolean(key);
            }
        }
        return flag;
    }

    @Override
    public void putObject(String key, Object value) {
        if(StringUtil.isNotEmpty(key)&& value!=null){
            mlCacheManager.putObject(key,value);
            mDlCacheManager.putObject(key,value);
        }
    }

    @Override
    public Object getObject(String key, Class cls) {
        if(StringUtil.isNotEmpty(key)){
            Object obj=mlCacheManager.getObject(key,cls);
            if(obj==null){
                obj=mDlCacheManager.getObject(key,cls);
            }
            return obj;
        }
        return null;
    }

    @Override
    public void putList(String key, List list) {
       if(StringUtil.isNotEmpty(key)&&list!=null&&!list.isEmpty()){
           mlCacheManager.putList(key,list);
           mDlCacheManager.putList(key,list);
       }
    }

    @Override
    public List<T>getList(String key, Class cls) {
        List<T>list=null;
        if(StringUtil.isNotEmpty(key)){
            list=mlCacheManager.getList(key,cls);
        }
        if(list.isEmpty()){
            list=mDlCacheManager.getList(key,cls);
        }
        return list;
    }

    @Override
    public void putMap(String key, Map map) {
        if(StringUtil.isNotEmpty(key)&&map!=null){
            mlCacheManager.putMap(key,map);
            mDlCacheManager.putMap(key,map);
        }
    }

    @Override
    public Map getMap(String key) {
        Map<String, T>map=null;
        if(StringUtil.isNotEmpty(key)){
            map=mlCacheManager.getMap(key);
        }
        if(map==null){
            map=mDlCacheManager.getMap(key);
        }
        return map;
    }

    @Override
    public void remove(String key) {
       mlCacheManager.remove(key);
       mDlCacheManager.remove(key);
    }

    @Override
    public void clear() {
       mlCacheManager.clear();
       mDlCacheManager.clear();
    }

    /**app退出时关闭缓存流**/
    public void closeCache(){
        clear();
        mDlCacheManager.close();
    }

}
