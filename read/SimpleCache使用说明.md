## SimpleCache使用说明

### 特性
SimpleCache原理是创建一个File,然后通过File读写将我们要缓存的数据进行存储和取出，本质是 File 文件操作.在我们的app打开到退出这段时间需要对数据进行持久化缓存的时候，可以利用此类.相比 Cache 和 SpUtil 说，它的优势在于,持久化比 Cache 久,所存的内容也要比 SpUtil 来讲要多.但劣势是存储读取比较耗时，毕竟是File读写，涉及到IO操作,再就是由于它的存储删除全需要手动操作，故在使用的时候，别忘了在需要清理数据的时候及时调用相关清除方法，不然会造成不必要的bug

### 使用说明
#### 1.初始化
```
SimpleCache mSimpleCache=SimpleCache.get();
```
此方法是默认在项目的缓存目录下建 SimpleCache 的缓存文件。当然，SimpleCache还有些自定义新建缓存File路径的方法,但不经常用到，大家感兴趣的话，可以看SimpleCache源码的初始化代码。

#### 2.String存储
```
   /**
     * 保存 String数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的String数据
     */
    public void put(String key, String value);
```
#### 3.String存储,在saveTime秒内能获取到值
```
    /**
     * 保存 String数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的String数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, String value, int saveTime) 
```
#### 4.String获取,默认取值为null
当以saveTime方式存储的时候,存储的值在saveTime秒后失效获取的值为null
```
getAsString(String key);
```
#### 5.存储JSONObject
涉及JSONObject相关转化的时候,需要JSONObject相关jar包支持
[JSONObject使用方法](https://blog.csdn.net/dongzhouzhou/article/details/8664569)
[JSONObject相关jar包下载](https://download.csdn.net/download/flyme2010/10548102)
```
put(String key, JSONObject value)
```
#### 6.存储JSONObject,saveTime时间内有效
```
    /**
     * 保存 JSONObject数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的JSONObject数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, JSONObject value, int saveTime)
```
#### 7.获取JSONObject,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsJSONObject(String key);
```
#### 8.存储JSONArray
涉及JSONObject相关转化的时候,需要JSONObject相关jar包支持
[JSONObject使用方法](https://blog.csdn.net/dongzhouzhou/article/details/8664569)
[JSONObject相关jar包下载](https://download.csdn.net/download/flyme2010/10548102)
```
put(String key, JSONArray value);
```
#### 9.存储JSONArray,saveTime时间内有效
```
   /**
     * 保存 JSONArray数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的JSONArray数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, JSONArray value, int saveTime)
```
#### 10.获取JSONArray,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsJSONArray(String key)
```
#### 11.存储byte数组
```
put(String key, byte[] value)
```
#### 12.存储byte数组,saveTime时间内有效
```
     /**
     * 保存 byte数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, byte[] value, int saveTime) 
```
#### 13.获取byte数组,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsBinary(String key)
```
#### 14.存储可序列化对象obj
obj需要实现Serializable接口
```
put(String key, Serializable obj)
```
#### 15.存储可序列化对象obj,saveTime时间内有效
obj需要实现Serializable接口
```
put(String key, Serializable obj, int saveTime)
```
#### 16.获取可序列化对象obj,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsObject(String key)
```
#### 17.存储bitmap
```
put(String key, Bitmap value)
```
#### 18.存储bitmap,saveTime时间内有效
```
    /**
     * 保存 bitmap 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的 bitmap 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Bitmap value, int saveTime) 
```
#### 19.获取bitmap,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsBitmap(String key)
```
#### 20.存储drawable
```
put(String key, Drawable value)
```
#### 21.存储drawable,saveTime时间内有效
```
    /**
     * 保存 drawable 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的 drawable 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Drawable value, int saveTime)
```
#### 22.获取drawable,默认取值为null
当以saveTime方式存储的时候,超过saveTime时间段后,取值为null
```
getAsBitmap(String key)
```
#### 23.移除某个key对应的value
```
remove(String key)
```
#### 24.清除所有
```
clear()
```


