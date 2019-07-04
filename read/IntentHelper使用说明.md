## IntentHelper使用说明

### 说明
IntentHelper是一个activity跳转帮助类,它提供activity与activity之间各种跳转与传值

### 使用简介
#### 1. 初始化
IntentHelper是一个单例,初始化如下：
```
IntentHelper.getInstance()
```
#### 2. 无参界面跳转
```
startAct(Context context,Class<?> cls)
```
#### 3. 带单个参数的界面跳转
parameter可以是以下类型:  
short, int, float, double, long, byte, char, String, boolean, Bundle对象, 实现Serializable接口的对象, 实现Parcelable接口的对象
```
startParameterAct(Context context,Class<?> cls, String tag, Object parameter)
```
#### 4. 用intent接收上一个界面传过来的Short,默认取值 0
```
getShortParameter(Context context,String tag)
```
#### 5. 用intent接收上一个界面传过来的int,默认取值 0
```
getIntParameter(Context context,String tag)
```
#### 6. 用intent接收上一个界面传过来的float,默认取值 0f
```
getFloatParameter(Context context,String tag)
```
#### 7. 用intent接收上一个界面传过来的double,默认取值 0d
```
getDoubleParameter(Context context,String tag)
```
#### 8. 用intent接收上一个界面传过来的long,默认取值 0L
```
getLongParameter(Context context,String tag)
```
#### 9. 用intent接收上一个界面传过来的byte,默认取值 0
```
getByteParameter(Context context,String tag)
```
#### 10. 用intent接收上一个界面传过来的char,默认取值 ' '
```
getCharParameter(Context context,String tag)
```
#### 11. 用intent接收上一个界面传过来的String,默认取值 null
```
getStringParameter(Context context,String tag)
```
#### 12. 用intent接收上一个界面传过来的boolean,默认取值 false
```
getBooleanParameter(Context context,String tag)
```
#### 13. 用intent接收上一个界面传过来的Bundle
```
getBundleParameter(Context context,String tag)
```
#### 14. 用intent接收上一个界面传过来的对象
对象需要实现Serializable
```
getSerializableObject(Context context,String tag)
```
对象需要实现Parcelable
```
getParcelableObject(Context context,String tag)
```
#### 15. 带List<Integer>list的界面跳转
```
startIntegerListAct(Context context,Class<?> cls, String tag, List<Integer> list)
```
#### 16. 用intent接收上一个界面传过来的list<Integer>list
```
getIntegerList(Context context,String tag)
```
#### 17. 带List<String>list的界面跳转
```
startStringListAct(Context context,Class<?> cls, String tag, List<String> list)
```
#### 18. 用intent接收上一个界面传过来的list<String>list
```
getStringList(Context context,String tag)
```
#### 19. 带List<Parcelable>list的界面跳转
List中的item对象需要实现Parcelable接口
```
startStringListAct(Context context,Class<?> cls, String tag, List<String> list)
```
#### 20. 用intent接收上一个界面传过来的list<Parcelable>list
List中的item对象需要实现Parcelable接口
```
getParcelableList(Context context,String tag)
```
