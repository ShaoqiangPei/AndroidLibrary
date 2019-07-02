package com.android.commonlibrary.cacher.normal_cache;

import com.android.commonlibrary.util.AppUtil;
import com.android.commonlibrary.util.GsonUtil;
import com.android.commonlibrary.util.MD5Util;
import com.android.commonlibrary.util.SDCardUtil;
import com.android.commonlibrary.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Title:DiskLruCache管理
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/24
 */
public class DLCacheManager<T>implements CacheListener {

    private static final String EXCEPTION_TAG= DLCacheManager.class.getSimpleName();
    private static final String DATA_TYPE="object";
    private static final String ENCODE_TYPE="utf-8";
    private static final String FLAG_TRUE="true";
    private static final String FLAG_FALSE="false";

    @Override
    public void putString(String key, String value){
        String cacheKey = getCacheKey(key);
        DiskLruCache diskLruCache = getDiskLruCache();
        if (isNotEmpty(diskLruCache, cacheKey) && StringUtil.isNotEmpty(value)) {
            try {
                DiskLruCache.Editor editor = diskLruCache.edit(cacheKey);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (writeContent(value, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getString(String key) {
        String cacheKey = getCacheKey(key);
        DiskLruCache diskLruCache = getDiskLruCache();
        if (isNotEmpty(diskLruCache, cacheKey)) {
            try {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(cacheKey);
                if (snapshot != null) {
                    return readContent(snapshot.getInputStream(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void putInt(String key, int value) {
        String str = String.valueOf(value);
        putString(key, str);
    }

    @Override
    public int getInt(String key) {
        String str=getString(key);
        int value=0;
        try {
            if(StringUtil.isNotEmpty(str)){
                value=Integer.valueOf(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException(EXCEPTION_TAG+"====value integer NumberFormat error");
        }
        return value;
    }

    @Override
    public void putFloat(String key, float value) {
        String str = String.valueOf(value);
        putString(key, str);
    }

    @Override
    public float getFloat(String key) {
        String str=getString(key);
        float value=0f;
        try {
            if(StringUtil.isNotEmpty(str)){
                value=Float.valueOf(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException(EXCEPTION_TAG+"====value float NumberFormat error");
        }
        return value;
    }

    @Override
    public void putDouble(String key, double value) {
        String str = String.valueOf(value);
        putString(key, str);
    }

    @Override
    public double getDouble(String key) {
        String str=getString(key);
        double value=0d;
        try {
            if(StringUtil.isNotEmpty(str)){
                value=Double.valueOf(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException(EXCEPTION_TAG+"====value double NumberFormat error");
        }
        return value;
    }

    @Override
    public void putLong(String key, Long value) {
        String str = String.valueOf(value);
        putString(key, str);
    }

    @Override
    public long getLong(String key) {
        String str=getString(key);
        long value=0L;
        try {
            if(StringUtil.isNotEmpty(str)){
                value=Long.valueOf(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException(EXCEPTION_TAG+"====value long NumberFormat error");
        }
        return value;
    }

    @Override
    public void putBoolean(String key, boolean flag) {
          if(flag){
              putString(key, DLCacheManager.FLAG_TRUE);
          }else{
              putString(key, DLCacheManager.FLAG_FALSE);
          }
    }

    @Override
    public boolean getBoolean(String key){
        String value=getString(key);
        if(DLCacheManager.FLAG_TRUE.equals(value)){
            return true;
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
    public List getList(String key, Class cls) {
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
        String cacheKey = getCacheKey(key);
        DiskLruCache diskLruCache = getDiskLruCache();
        if (isNotEmpty(diskLruCache, cacheKey)) {
            try {
                diskLruCache.remove(cacheKey);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clear() {
         DiskCacheInfo.clearAllCache();
    }

    /**退出程序的时候要关闭DiskLruCache**/
    public void close(){
        DiskLruCache diskLruCache = getDiskLruCache();
        if(diskLruCache!=null){
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDiskCachePath() {
        String cachePath = SDCardUtil.getDiskCachePath() + DATA_TYPE;
        return cachePath;
    }

    private DiskLruCache getDiskLruCache() {
        DiskLruCache diskLruCache = null;
        try {
            File cacheDir = new File(getDiskCachePath());
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDir, AppUtil.getVersionCode(), 1, CacheConfig.DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diskLruCache;
    }

    private String getCacheKey(String key){
        String cashKey=null;
        if(StringUtil.isNotEmpty(key)){
            cashKey= MD5Util.getMD5(key);
        }
        return cashKey;
    }

    private boolean writeContent(String value,OutputStream outputStream){
        BufferedOutputStream out=null;
        try {
            if(outputStream!=null){
                out=new BufferedOutputStream(outputStream);
                byte data[]=value.getBytes(ENCODE_TYPE);
                out.write(data);
                out.flush();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String readContent(InputStream inputStream){
        Reader in=null;
        try {
            if(inputStream!=null){
                in=new InputStreamReader(inputStream);
                int len=0;
                String readData=null;
                StringBuffer buffer=new StringBuffer();
                while ((len=in.read())!=-1){
                    String str=String.valueOf((char)len);
                    buffer.append(str);
                }
                readData=buffer.toString();
                return readData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isNotEmpty(DiskLruCache diskLruCache, String key){
        if(diskLruCache!=null&& StringUtil.isNotEmpty(key)){
            return true;
        }
        return false;
    }

}
