package com.android.commonlibrary.cacher.normal_cache;

import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/10/23
 */
public interface CacheListener<T>{

    void putString(String key, String value);

    String getString(String key);

    void putInt(String key, int value);

    int getInt(String key);

    void putFloat(String key, float value);

    float getFloat(String key);

    void putDouble(String key, double value);

    double getDouble(String key);

    void putLong(String key, Long value);

    long getLong(String key);

    void putBoolean(String key, boolean value);

    boolean getBoolean(String key);

    void putObject(String key, Object value);

    Object getObject(String key, Class<T> cls);

    void putList(String key, List list);

    List<T> getList(String key, Class<T> cls);

    void putMap(String key, Map map);

    Map getMap(String key);

    void remove(String key);

    void clear();
}
