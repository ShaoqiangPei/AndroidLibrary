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
然后在你自建的“非绑定式服务”的onCreate方法中调用以上的initFont方法，更多Notification相关使用，可以参考[NotificationHelper使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/NotificationHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)
#### 3. 关闭前台模式
在自建的“非绑定式”服务销毁的时候，你需要关闭前台模式：
```
        //退出前台运行模式
        mLocalServiceHelper.stopForeService(MyService.this);
```
#### 4. “非绑定式”服务范例
下面贴出一个“非绑定式”服务样例代码：
```
/**
 * Title:"非绑定式服务"
 * description:
 * autor:pei
 * created on 2019/11/18
 */
public class MyService extends Service {

    //声明“非绑定式服务”帮助类对象
    private LocalServiceHelper mLocalServiceHelper;
//    //声明通知栏帮助类对象
//    private NotificationHelper mNotificationHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.i("========创建非绑定式服务===");

//        initFont();

        //初始化"非绑定式服务"帮助类对象
        mLocalServiceHelper=new LocalServiceHelper();
        //开启简易通知栏前台模式
        mLocalServiceHelper.startForeService(MyService.this,R.mipmap.ic_launcher,getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.i("========start非绑定式服务===");

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    /**前台运行模式**/
//    private void initFont(){
//        mNotificationHelper=new NotificationHelper();
//        //创建builder
//        NotificationCompat.Builder builder = mNotificationHelper.init(R.mipmap.ic_launcher)//初始化,设置小图标,必须调用,一般设置app的icon
//                .setTitle("")//设置标题
//                .setContent("")//设置内容
//                .createBuilder(getApplicationContext());//创建builder
//        Notification notification = builder.build();
//
//        mLocalServiceHelper=new LocalServiceHelper();
//        //设置前台模式
//        mLocalServiceHelper.startForeService(MyService.this,notification);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //退出前台运行模式
        mLocalServiceHelper.stopForeService(MyService.this);

        LogUtil.i("========退出非绑定式服务===");
    }
}
```
之后，你需要在manifast.xml中注册你的"非绑定式"服务MyService：
```
 </application>
  //其他代码省略
  //......
        <service android:name=".MyService">
            <intent-filter>
                <action android:name="com.p.ui.MyService"/>
            </intent-filter>
        </service>
  </application>
```
一般我们在action标签中的name属性写你自建“非绑定式服务”的全路径(这里我写的是MyService全路径)，用以保证service注册的唯一性。
#### 5.启动与关闭不含参的“非绑定式服务”
在MainActivity中启动一个"非绑定式"服务，你可以这样：
```
  //“非绑定式”服务帮助类
  private LocalServiceHelper mLocalServiceHelper;

  //初始化对象
  mLocalServiceHelper=new LocalServiceHelper();
  //开启一个不传参的 "非绑定式"服务
  mLocalServiceHelper.startLocalService(MainActivity.this,MyService.class,"com.p.ui.MyService");
```
其中"com.p.ui.MyService"要与MyService在manifast.xml中配置的action标签下的name属性一致。
MainActivity中关闭一个不传参的 "非绑定式"服务，你可以这样：
```
  //销毁服务
  mLocalServiceHelper.stopLocalService(MainActivity.this);
```
#### 6.启动与关闭含参的“非绑定式服务”
若你需要在MainActivity中启动一个含参的“非绑定式服务”，你可以像下面这样：
```
    private Intent mIntent;
    //“非绑定式”服务帮助类
    private LocalServiceHelper mLocalServiceHelper;

    mLocalServiceHelper=new LocalServiceHelper();
    //启动含参的"非绑定式服务"
    mIntent=new Intent();
    mIntent.putExtra("key",1);
    mLocalServiceHelper.startLocalServiceByIntent(MainActivity.this,mIntent,MyService.class,"com.p.ui.MyService");
```
然后，你就可以在MyService的onStartCommand(Intent intent, int flags, int startId)方法中接收参数处理了。
在MainActivity界面销毁的时候，注销你的含参“非绑定式”服务：
```
   //注销含参"非绑定式"服务
   mLocalServiceHelper.stopLocalServiceByIntent(MainActivity.this,mIntent);
```
#### 7.“非绑定式服务”主动关闭自己
在“非绑定式服务”中，若有需要，还可以主动销毁自己，下面是LocalServiceHelper提供的主动销毁自己的两个方法：
```
   /***
     * "非绑定式"服务主动关闭自己
     */
    public void stopLocalBySelf(Service service)
    
    /***
     * "非绑定式"服务主动关闭自己
     */
    public void stopLocalSelfById(Service service,int startId)
```
