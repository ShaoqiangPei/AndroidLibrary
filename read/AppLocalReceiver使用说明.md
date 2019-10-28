## AppLocalReceiver使用说明
### 概述
AppLocalReceiver是一个本地广播工具类，是对LocalBroadcastManager功能的一个封装使用类。AppLocalReceiver本质是一个BroadCast，
它的出现是为了让 动态注册本地广播 流程变得简洁。开发者无需再去关注广播的注册，使用，注销细节，这些统统被封装到AppLocalReceiver的方法中。AppLocalReceiver
由于是本地广播，故比全局广播更具安全性，适合仅需在本app内部传递广播消息的场景。

### 使用说明
#### 一.AppLocalReceiver基本方法
```
//构造方法<在非注册Receiver界面给注册Receiver界面发送广播时调用，用于创建广播对象。>
AppLocalReceiver()

//构造该方法<创建广播接收器对象，并动态注册广播>
AppLocalReceiver(Context context, String activityName)

/**设置监听**/
setOnReceiverListener(OnReceiverListener listener)

/**发送广播**/
sendToBroadcast(Context context,String sendToActivityName, Bundle bundle)

/**注销广播**/
onDestroy() 
```
#### 二.AppLocalReceiver的使用
以在MainActivity中使用为例，你需要对AppLocalReceiver声明：
```
 //声明对象
 private AppLocalReceiver mAppLocalReceiver;
```
然后初始化兼注册本地广播：
```
  //初始化并注册本地广播
  mAppLocalReceiver=new AppLocalReceiver(MainActivity.this,MainActivity.class.getName());
```
设置广播监听:
```
  //设置本地广播监听
  mAppLocalReceiver.setOnReceiverListener(new AppLocalReceiver.OnReceiverListener() {
     @Override
     public void receiver(String action, Bundle bundle) {
        //接收逻辑处理
        //......

      }
  });
```
在MainActivity界面销毁时，注销本地广播：
```
  //销毁本地广播
  mAppLocalReceiver.onDestroy();
  LogUtil.i("===注销本地广播===");
```
若你需要在MainActivity中发送广播，你可以像下面这样：
```
  //发送广播
  Bundle bundle=new Bundle();
  bundle.putInt("code",0);
  bundle.putString("pp","测试");
  mAppLocalReceiver.sendToBroadcast(MainActivity.this,MainActivity.class.getName(),bundle);
```
若你不在MainActivity界面发送广播，而是在另一个界面(如 ActivityB界面)，即你要在ActivityB界面向MainActivity界面发送本地广播，那么在ActivityB界面你可以像下面这样发送广播：
```
   //发送广播
   AppLocalReceiver receiver=new AppLocalReceiver();
   Bundle bundle=new Bundle();
   bundle.putInt("code",1);
   bundle.putString("pp","测试B");
   receiver.sendToBroadcast(ActivityB.this,MainActivity.class.getName(),bundle);
```



