## ServiceHelper使用说明

### 概述
ServiceHelper是为方便创建服务而产生的一个帮助类，目前里面的主要方法是用来创建 服务前台模式的，目的是最大限度的保持创建的服务不在app生命周期内“意外死亡”。
ServiceHelper拥有两个子类
- [LocalServiceHelper](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/LocalServiceHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)  ："非绑定式"服务帮助类
-  

### 使用说明
#### 1. ServiceHelper初始化
初始化ServiceHelper，你可以像下面这样：
```
ServiceHelper mServiceHelper=new ServiceHelper();
```
由于 ServiceHelper 仅仅是为了给"绑定式"服务和"非绑定式"服务提供公共的可调用的方法。因此我们很少直接去创建 ServiceHelper 对象，而是直接去创建其子类对象
然后调用相关方法。

#### 2. 开启前台模式
开启前台服务，必须结合通知栏使用，若你需要快速开启一个含简易通知栏的前台服务，你可以在你创建的“非绑定式”service中调用以下方法：
```
    /**
     * 开启前台运行模式(简易通知栏模式)
     * @param drawableId 设置小图标,一般设置app的icon,样式如：R.mipmap.ic_launcher
     */
    public void startForeService(Service service,int drawableId,Context context)
```
若你要开启一个自定义或者布局比较自由的通知栏 前台服务，你需要自定义一个Notification，然后调用以下方法开启一个服务前台模式
```
    /**开启服务前台模式**/
    public void startForeService(Service service, Notification notification){
```
关于通知栏的快速创建，你可以参考[NotificationHelper使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/NotificationHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)
#### 3. 关闭前台模式
如果你的服务已经开启了前台模式，那么在服务销毁的时候，你需要关闭前台模式：
```
    /**关闭服务前台模式**/
    stopForeService(Service service)
```

