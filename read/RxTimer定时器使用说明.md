## RxTimer定时器使用说明

### 概述
RxTimer定时器是一款经 RxJava2.x实现的定时器。与 [TimerManager](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/TimerManager%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) 定时器不同，你可以通过 new 不同的 RxTimer 对象，来建立多个定时器。
定时器之间互不干扰。

### 使用说明
#### 一. 初始化定时器
你可以有两种方式初始化一个RxTimer定时器：  
```
        //初始化一个不设置tag的定时器
        RxTimer rxTimerOne=new RxTimer();
        
        //初始化一个带tag的定时器
        RxTimer rxTimerTwo=new RxTimer("Two");
```
以上两种方式的不同在于，不设置参数的时候是设置一个不带Tag的定时器，设置参数是初始化一个带tag的定时器，而tag的作用是用来标志一个定时器。  
当然，如果你不想那么麻烦的话，你完全可以不设置tag。倘若你以"RxTimer rxTimerOne=new RxTimer();" 方式初始化了一个RxTimer后，你又想给它设置一个Tag，
那么你可以像下面这样设置Tag：
```
      rxTimerOne.setTag("One");
```
#### 二. 延时定时器
需要执行延时操作的话，你可以用以下方法：
```
    /** milliseconds毫秒后执行next操作
     *
     * @param milliseconds：毫秒
     * @param next
     */
    public void timer(long milliseconds,final OnRxTimeListener next)
```
此方法执行完后，定时器会自行注销，你完全不需要做任何处理。
#### 二. 循环执行定时器
若有需要循环操作的动作，你可以调用以下方法：
```
    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds：毫秒
     * @param next
     */
    public void interval(long milliseconds,final OnRxTimeListener next)
```
当你需要关闭一个循环定时器，你需要做注销操作：
```
    /**
     * 取消订阅
     */
    public void cancel()
```
#### 三. 获取定时器tag
若你的RxTimer已经设置了tag，那么，你还可以代码获取该RxTimer的tag
```
    /**获取定时器tag**/
    public String getTag()
```



