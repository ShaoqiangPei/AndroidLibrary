## RemoteServiceHelper使用说明

### 概述
RemoteServiceHelper作为远程服务(即绑定式服务)的帮助类，继承自 ServiceHelper，拥有比 ServiceHelper 更加强大的功能，集 开启/关闭 服务前台模式，
绑定/解绑 绑定式服务，获取绑定式服务对象 等方法于一身。能快速，便捷的创建一个绑定的式服务。

### 使用说明
RemoteServiceHelper 作为自建 “绑定式”服务的帮助类，主要用于协助一个开发者创建的“绑定式”服务。
若你项目中需要创建一个“绑定式”服务MyService，你可以继承"绑定式"服务的基类RemoteService
(RemoteService使用请参考：[RemoteService使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/RemoteService%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md))，
来写你自己的服务：
```
public class MyService extends RemoteService{

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.i("====== MyService onCreate =======");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        LogUtil.i("====== MyService onBind =======");
        return super.onBind(arg0);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i("====== MyService onUnbind =======");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("====== MyService onDestroy =======");

    }

    @Override
    protected RemoteService getSelfService() {
        return MyService.this;
    }


    public void printLog(){
        LogUtil.i("=======我是大家好=======");
        ToastUtil.shortShow("======我是自建服务中的方法========");
    }

}
```
当然，不要忘了在manifast.xml中注册你的MyService，你可像下面这样注册：
```
 </application
  //以下代码省略
  //......
>
        <service android:name="com.example.function.MyService">
            <intent-filter>
                <action android:name="com.example.function.MyService"/>
            </intent-filter>
        </service>
  //以下代码省略
  //......
    </application>
```
注意，service标签中的name属性和action标签中的name属性要保证唯一，一般我用自建服务(此处为MyService)的全路径标识。

#### 一.给自建的“绑定式”服务设置前台运行模式
RemoteServiceHelper可以方便的给你自己创建的“绑定式”服务设置前台运行模式，如果你想要给你创建的“绑定式”服务设置前台运行模式，你可以这样
(以上面的MyService为例)：
```
public class MyService extends RemoteService{

    private RemoteServiceHelper mRemoteServiceHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.i("====== MyService onCreate =======");

        //初始化服务对象
        mRemoteServiceHelper=new RemoteServiceHelper();
//        //开启服务简易前台模式
//        mRemoteServiceHelper.startForeService(MyService.this, R.mipmap.ic_launcher,getApplicationContext());
    }

 

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("====== MyService onDestroy =======");

        //停止服务前台模式
        mRemoteServiceHelper.stopForeService(MyService.this);
    }

    //其他代码省略
    //......

}
```
值得注意的是，RemoteServiceHelper对于自建的“绑定式”服务，唯一的帮助是 开启/关闭 服务前台运行模式，所以它在自建的“绑定式”服务(此处是MyService)中的
初始化方式为：
```
mRemoteServiceHelper=new RemoteServiceHelper();
```
若你自建的“绑定式”服务(此处是MyService)中不需设置服务前台运行模式，那么你完全不必在你的MyService中引入RemoteServiceHelper

#### 二.绑定/解绑 服务
RemoteServiceHelper的另一个主要用途，便是在需要用到“绑定式”服务的时候，快速实现服务的绑定与解绑。
以 MyService 在 MainActivity 中的使用为例，在MainActivity中，你需要这样初始化RemoteServiceHelper(注：与在服务中的初始化是有区别的)：
```
    //声明服务辅助类
    private RemoteServiceHelper mRemoteServiceHelper;
    
    //初始化服务帮助类
    mRemoteServiceHelper=new RemoteServiceHelper(MyService.class,MainActivity.this);
```
 然后，你可以在MainActivity中这样启动MyService：
 ```
   //启动服务
   mRemoteServiceHelper.startService();
 ```
 当服务使用完毕后，在MainActivity销毁的时候，你可以这样解绑MyService：
 ```
   //解绑服务
   mRemoteServiceHelper.stopService();
 ```
#### 三.调用自建"绑定式"服务中的自定义方法
若你要在MainActivity中调用MyService中的printLog()方法，你可以在MainActivity中这样：
```
  //调用服务中的方法
  ((MyService)mRemoteServiceHelper.getService()).printLog();
```
ok,下面贴出RemoteServiceHelper在MainActivity中使用代码，以作参考：
```
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTv;
    private Button mBtn;

    //声明服务辅助类
    private RemoteServiceHelper mRemoteServiceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setListener();
    }

    private void initData(){
        mTv = findViewById(R.id.tv);
        mBtn = findViewById(R.id.btn);

        //初始化服务帮助类
        mRemoteServiceHelper=new RemoteServiceHelper(MyService.class,MainActivity.this);

        ToastUtil.shortShow("启动服务");
        //启动服务
        mRemoteServiceHelper.startService();
    }

    private void setListener() {
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                test();
                break;
            default:
                break;
        }
    }

    private void test(){
        LogUtil.i("======kk========");

        //调用服务中的方法
        ((MyService)mRemoteServiceHelper.getService()).printLog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ToastUtil.shortShow("注销服务");

        //解绑服务
        mRemoteServiceHelper.stopService();
    }
}
```



