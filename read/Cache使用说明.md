## Cache使用说明

### 特性
cache 类是一个利用Lru,即使用时间最短，利用频率越低则会在一定时间类移除的算法做的 app缓存，集成app内存缓存与app硬盘缓存与一体，适用于减少通讯需要暂时性缓存的情况。

### 使用介绍
#### 1. 存储String
```
Cache.getInstance().putString(String key, String value);
```
#### 2. 获取String,默认取值为 null
```
Cache.getInstance().getString(String key);
```
#### 3. 存储int
```
Cache.getInstance().putInt(String key, int value);
```
#### 4. 获取int,默认取值为 0
```
Cache.getInstance().getInt(String key);
```
#### 5. 存储float
```
Cache.getInstance().putFloat(String key, float value);
```
#### 6. 获取float,默认取值为 0f
```
Cache.getInstance().getFloat(String key);
```
#### 7. 存储double
```
Cache.getInstance().putDouble(String key, double value);
```
#### 8. 获取double,默认取值为 0d
```
Cache.getInstance().getDouble(String key);
```
#### 9. 存储long
```
Cache.getInstance().putLong(String key, Long value);
```
#### 10. 获取long,默认取值为 0L
```
Cache.getInstance().getLong(String key);
```
#### 11. 存储boolean
```
Cache.getInstance().putBoolean(String key, boolean value);
```
#### 12. 获取boolean,默认取值为 false
```
Cache.getInstance().getBoolean(String key);
```
#### 13. 存储可序列化obj
obj需要实现Serializable 或 Parcelable
```
Cache.getInstance().putObject(String key, Object obj);
```
#### 14. 获取可序列化obj,默认取值为 null
obj需要实现Serializable 或 Parcelable
```
Cache.getInstance().getObject(String key, Class cls);
```
#### 15. 存储List集合
当list中装的是对象的时候,对象需要实现Serializable 或 Parcelable接口
```
Cache.getInstance().putList(String key, List list);
```
#### 16. 获取List集合
当list中装的是对象的时候,对象需要实现Serializable 或 Parcelable接口,默认获取一个空集合(list!=null&&List.size=0)
```
Cache.getInstance().getList(String key, Class cls);
```
#### 17. 存储Map
此处map只接受Map<String,String>map类型,若要存储可序列化对象，可以考虑将对象用gson转化成String，然后存储
```
Cache.getInstance().putMap(String key, Map map);
```
#### 18. 获取Map，默认取值为 null
若map中存储的是经过gson转化成的字符串对象，则需要通过gson将map中的value转化成对应的对象
```
Cache.getInstance().getMap(String key);
```
#### 19. 根据key移除对应的缓存值
```
Cache.getInstance().remove(String key);
```
#### 20. 清理缓存中所有值
```
Cache.getInstance().clear();
```
#### 21. 关闭缓存
app退出的时候调用,关闭缓存的时候会清空缓存中所有值
```
//app退出时关闭缓存流
Cache.getInstance().closeCache();
```
