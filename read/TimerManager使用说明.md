## TimerManager使用说明

### 概述
TimerManager类是对 Timer 使用的一个封装，用户可以方便的使用到 timer 相关的定时，延时功能。
需要注意的是：TimerManager只能建立一个定时器。若需要使用多个定时器，可以查看另一个定时器类 [RxTimer](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/RxTimer%E5%AE%9A%E6%97%B6%E5%99%A8%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)  

### 使用说明
#### 一. TimerManager初始化
TimerManager是一个单例，你可以像下面这样初始化一个TimerManager对象
```
TimerManager timerManager=TimerManager.getInstance();
```
但是我们一般都直接用
```
TimerManager.getInstance()
```
#### 二. TimerManager中提供的方法
TimerManager作为一个定时器工具类，提供以下几个公开方法：
```
/**启动定时器循环调用(context设置为null时可执行非ui的逻辑，context不为null时可更新ui)**/
startRecycle(Context context,OnTimerListener onTimerListener)

/**启动定时器延时调用(context设置为null时可执行非ui的逻辑，context不为null时可更新ui)**/
startDelay(Context context,OnTimerListener onTimerListener)

/**停止定时器**/
cancel() 

/**设置时间间隔(若不设置，则默认时间间隔为1秒)**/
setDelayTime(long delayTime) 
```
#### 三. 调用示例
##### 3.1 延时执行 
若要启动一个延时执行的业务逻辑，你可以像下面这样：
```
        //定时器延时
        TimerManager.getInstance()
                .setDelayTime(1000)//设置时间间隔，默认1000(即1秒)
                //context设置为null时可执行非ui的逻辑，context不为null时可执行更新ui逻辑
                .startDelay(MainActivity.this,new TimerManager.OnTimerListener() {
                    @Override
                    public void schedule() {
                        //延时执行逻辑
                        //......
                    }
                });
```
延时执行的话，是不需要去取消定时器的，因为它执行完毕后就会自动注销掉。
##### 3.2 循环执行 
你可以像下面这样启动一个循环执行的定时器
```
//定时器循环
TimerManager.getInstance()
        .setDelayTime(1000)//设置时间间隔，默认1000(即1秒)
        //context设置为null时可执行非ui的逻辑，context不为null时可执行更新ui逻辑
        .startRecycle(MainActivity.this,new TimerManager.OnTimerListener() {
             @Override
             public void schedule() {
                 //循环执行逻辑
                 //......
             }
         });

``` 
循环执行的定时器，在使用完毕后，需要手动取消定时器
##### 3.3 定时器取消
在使用玩定时器后，需要释放资源，取消定时器方法如下：
```
        //取消定时器
        TimerManager.getInstance().cancel();
```
