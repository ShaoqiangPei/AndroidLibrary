## LocalServiceHelper使用说明

### 概述
LocalServiceHelper作为"非绑定式"服务的帮助类，其继承于 ServiceHelper，拥有比 ServiceHelper 更加强大的功能。集成开启，关闭前台服务，启动，关闭
"非绑定式"服务等功能。便于快速新建和销毁一个"非绑定式"服务。
其父类为 ServiceHelper.要了解 ServiceHelper 功能，请参考 [ServiceHelper使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/ServiceHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
)  

### 使用说明
#### 1. 初始化
初始化LocalServiceHelper对象，你可以这样：
```
    //声明“非绑定式服务”帮助类对象
    private LocalServiceHelper mLocalServiceHelper;

    //初始化"非绑定式服务"帮助类对象
    mLocalServiceHelper=new LocalServiceHelper();
```
#### 2. 开启前台模式
你可以像下面这样在"非绑定式服务"中开启一个简易通知栏前台模式：
```
    //开启简易通知栏前台模式
    mLocalServiceHelper.startForeService(MyService.this,R.mipmap.ic_launcher,getApplicationContext());
```
若你需要开启一个“定制版”通知栏的前台模式，你可以在你自建的“非绑定式服务”中写一个类似下面的方法：
```
    //声明“非绑定式服务”帮助类对象
    private LocalServiceHelper mLocalServiceHelper;
    //声明通知栏帮助类对象
    private NotificationHelper mNotificationHelper;

        /**前台运行模式**/
    private void initFont(){
        mNotificationHelper=new NotificationHelper();
        //创建builder
        NotificationCompat.Builder builder = mNotificationHelper.init(R.mipmap.ic_launcher)//初始化,设置小图标,必须调用,一般设置app的icon
                .setTitle("")//设置标题
                .setContent("")//设置内容
                .createBuilder(getApplicationContext());//创建builder
        Notification notification = builder.build();

        mLocalServiceHelper=new LocalServiceHelper();
        //设置前台模式
        mLocalServiceHelper.startForeService(MyService.this,notification);
    }
```
然后在你自建的“非绑定式服务”的onCreate方法中调用以上的initFont方法，更多Notification相关使用，可以参考

