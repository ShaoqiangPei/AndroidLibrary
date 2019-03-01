### SpUtil使用说明
SpUtil 为 sharepreferences 封装工具类，具有丰富的存储功能，可读取的数据类型有-boolean、int、float、long、String,List. 存储单个String时自带加密
还能存取序列化对象。

#### 涉及基本权限及可能涉及的权限
需要在mainfast中配置基本的读写权限：
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
```
android6.0 以上需要动态申请权限，8.0需要申请文件读写Provider权限，若涉及到9.0的安装未知来源权限的话，还需要在mainfast中添加
```
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```

#### 涉及gson引用
若你只需要单独使用SpUtil工具方法，需要将整个sp文件夹拷贝到你的项目中，然后在你项目中增加gson库的引用：
```    
//gson
implementation 'com.google.code.gson:gson:2.8.5'
```

#### spUtil使用介绍
##### 1.SpUtil存储涉及加密模块，故在使用SpUtil的任何存取方法前(最好在项目的Appliction中)设置秘钥
```
//设置秘钥，秘钥mak为16位数字字母组合的字符串
SpUtil.setMAK(String mak);
```
##### 2.存储String，int，boolean，float，long数据类型
```
//存储 当数据类型为String，int，boolean，float，long中任何一种的时候，put方法具备自动转型功能
SpUtil.put(String key, Object object);
```
##### 3.存储对象，对象需要实现Serializable接口
```
putObject(String key, Object object);
```
##### 4.获取对象，对象需要实现Serializable接口
```
getObject(String key)
```
##### 5.将复杂对象转换成json数据储存,需要配合gson使用
```
putJsonObject(String key, Object obj)
```
##### 6.取JsonObject，需要配合gson使用
```
getJsonObject(String key)
```
##### 7.取String，默认取null
```
getString(String key)
```
##### 8.取int，默认取number
```
getInt(String key,int number)
```
##### 9.取int，默认取0
```
getInt(String key)
```
##### 10.取float，默认取number
```
getFloat(String key,float number)
```
##### 11.取float，默认取0
```
getFloat(String key)
```
##### 12.取long，默认取number
```
getLong(String key,long number)
```
##### 13.取long，默认取0
```
getLong(String key)
```
##### 14.取boolean，默认取flag
```
getBoolean(String key,boolean flag)
```
##### 15.取boolean，默认取false
```
getBoolean(String key)
```
##### 16.存list
```
putList(String keyName, List<?> list) 
```
##### 17.取list
```
getMap(String key);
```
##### 18.移除某个key对应的value
```
removeKey(String key)
```
##### 19.清除所有数据
```
clear()
```
##### 20.查询某个key是否已经存在
```
contains(String key)
```
