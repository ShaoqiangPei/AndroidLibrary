## AppReceiver使用说明
### 概述
AppReceiver本质是一个BroadcastReceiver，是一个基于BroadcastReceiver的封装类。它的出现是为了让手动注册广播的使用更加方便快捷。使注册和使用广播时不再繁琐。作为一个全局广播工具类，适合涉及监听本app广播，也有监听其他app广播需求的场景使用。

### 使用说明
#### 一. 基本方法介绍
AppReceiver作为 BroadcastReceiver的封装类，具备以下方法：
```
//构造方法1
AppReceiver()

//构造方法2
AppReceiver(Context context,String activityName)

//设置监听，广播接收在此处理
setOnReceiverListener(OnReceiverListener listener)

//发送广播
sendToBroadcast(Context ctx, String sendToActivityName, Bundle bundle)

//注销广播
onDestroy()
```
#### 二. 基本使用介绍
以MainActivity为例，当我们要在MainActivity中注册一个广播监听的时候，我们可以这样：
```
    //声明广播对象
    private AppReceiver mAppReceiver;

    //初始化(含注册广播逻辑)mAppReceiver对象
    mAppReceiver=new AppReceiver(MainActivity.this,MainActivity.class.getName());

    //接收广播
    mAppReceiver.setOnReceiverListener(new AppReceiver.OnReceiverListener() {
         @Override
         public void receiver(String action, Bundle b) {
           //接收数据，并做相关逻辑处理
           //......
          }
    });


    //在MainActivity销毁时，注销广播
    mAppReceiver.onDestroy();
```
若要在MainActivity中发起广播，你可以像下面这样：
```
   //发送广播
   Bundle bundle=new Bundle();
   bundle.putInt("code",0);
   bundle.putString("key","我是MainActivity自己发送的值");
   mAppReceiver.sendToBroadcast(MainActivity.this,MainActivity.class.getName(),bundle);
```
若你要在其他界面，如 ActivityB 界面给 MainActivity 发送广播，你可以在ActivityB 中像下面这样给MainActivity发送广播：
```
   //发送广播
   AppReceiver receiver=new AppReceiver();
   Bundle bundle=new Bundle();
   bundle.putInt("code",1);
   bundle.putString("key","我是B发过来的值");
   receiver.sendToBroadcast(this,MainActivity.class.getName(),bundle);
```
