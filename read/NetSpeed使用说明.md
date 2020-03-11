## NetSpeed使用说明

### 概述
NetSpeed是一个监测网速的工具类。内置定时器，用户只需要简单的调用即可获得当前网络链接速度。

### 使用说明
#### 一. NetSpeed方法介绍
NetSpeed主要有以下几个公开方法
```
   /**
     * 设置延时启动时间
     *
     * @param delayTime 单位毫秒,默认500毫秒
     * @return
     */
    public NetSpeed setDelayTime(long delayTime)
    
   /***
     * 设置循环时间间隔
     *
     * @param recycleTime 单位毫秒,默认1500毫秒
     * @return
     */
    public NetSpeed setRecycleTime(long recycleTime)
    
   /***
     * 开始监测网速
     *
     * @param view 显示网速的控件，TextView子类
     * @param context AppCompatActivity实例,不能是application实例
     */
    public void start(TextView view,Context context)
    
    /**取消网速监测**/
    public void cancel()
    
   /***
     * 获取某一时刻网速
     *
     * 需要结合定时器更新
     * @return
     */
    public String getNetSpeed()
    
    /**将byte自动转换为其他单位**/
    public String formatNetSpeed(long bytes)
```
#### 二. NetSpeed方法介绍
##### 2.1 简单使用
如果对于网速监测没有开始延迟时间和循环监测时间间隔的需求(即使用默认时间设置)，你可以直接在`MainActivity`中这样监测网络：
```
   //网速监测(已内置定时器,结束监测时需要调用取消方法)
   NetSpeed.getInstance().start(mTextView,MainActivity.this);//开始监测网速，传入要显示网速的控件
```
然后在界面销毁时调用：
```
   //取消网络监测
   NetSpeed.getInstance().cancel();
```
##### 2.2 配置时间参数的使用
若对网络监测时间间隔想自己设置，你可以像下面这样在`MainActivity`中调用：
```
   //网速监测(已内置定时器,结束监测时需要调用取消方法)
   NetSpeed.getInstance()
           .setDelayTime(500)//设置延迟执行时间,单位毫秒，默认500毫秒
           .setRecycleTime(1500)//设置监测时间间隔，单位毫秒,默认1500毫秒
           .start(mTextView,MainActivity.this);//开始监测网速，传入要显示网速的控件
```
当然，在界面销毁时，你仍需取消监测：
```
   //取消网络监测
   NetSpeed.getInstance().cancel();
```
#### 三. 其他
##### 3.1 获取网速
若由于上面方法传参固化，无法满足你的定制需求，你可以像下面这样获得某一时刻的网速：
```
     /***
     * 获取某一时刻网速
     *
     * 需要结合定时器更新
     * @return
     */
    public String getNetSpeed()
```
不过为了监听实时网速，你还需要自己新起一个定时器来间隔时间调用`getNetSpeed()`,以获取实时网速。  
##### 3.1 规范网速数据包单位
若有根据网速数据包大小获取含单位的字符串需求的话，可以调用以下方法：
```
    /**将byte自动转换为其他单位**/
    public String formatNetSpeed(long bytes)
```
