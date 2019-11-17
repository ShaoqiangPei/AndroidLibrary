## NotificationHelper使用说明

### 概述
NotificationHelper是一个基于消息栏通知的封装类。NotificationHelper 整合了消息栏的基本配置，发通知和取消通知的一些基本操作，
目的是方便 Notification 的使用。当然NotificationHelper也对通知栏消息的使用做了相应的版本呢兼容，下面就让我们来了解下NotificationHelper的具体使用吧。

### 使用说明
先看下效果图  
系统样式  
![1.gif](https://upload-images.jianshu.io/upload_images/6127340-7f66c7ae03451d06.gif?imageMogr2/auto-orient/strip)  
自定义布局样式  
![5.gif](https://upload-images.jianshu.io/upload_images/6127340-3626f91420326101.gif?imageMogr2/auto-orient/strip)  

#### 一.NotificationHelper 初始化
NotificationHelper作为一个通知工具类，你可以这样初始化它：
```
  //创建工具类对象
  mNotificationHelper = new NotificationHelper();
```
在你需要使用通知相关方法的时候，你必须创建NotificationHelper对象，因为与通知相关的方法，都需要通过NotificationHelper对象来调用。
#### 二.NotificationHelper 创建系统通知
大家都知道，一个通知的使用流程依次是以下几步：
1. 创建NotificationManager对象
2. 创建NotificationCompat.Builder对象
3. 通过给builder设置参数来设置通知相关属性
4. 通过builder获得Notification对象
5. NotificationManager设置Notification并发送通知
6. 使用完毕后，通过NotificationManager取消通知

在整个流程中，“创建NotificationCompat.Builder对象”并“通过给builder设置参数来设置通知相关属性”是很重要的两步。
在系统样式通知的使用过程中，你可以像下面这样创建一个简洁的Builder：
```
   //创建builder
   NotificationCompat.Builder builder = mNotificationHelper.init(R.mipmap.ic_launcher)//初始化,设置小图标,必须调用,一般设置app的icon
                      .createBuilder(MainActivity.this);//创建builder
```
这样，你只能获得一个什么都没设置的builder，但是你可以用这个builder来设置你需要的任何属性，如：
```
   //设置小图标
   builder .setSmallIcon(R.mipmap.ic_launcher)
           //点击后自动清除
          .setAutoCancel(true)
          //设置通知标题
         .setContentTitle("最简单的通知")
         //设置通知内容
         .setContentText("真的很简单很简单很简单")
         //设置通知的动作
         .setContentIntent(mPendingIntent)
         //设置通知时间，默认为系统发出通知的时间
         .setWhen(System.currentTimeMillis());
```
等等众多NotificationCompat.Builder的原始属性，这里就不作过多介绍了。
但是，由于是讲到NotificationHelper的使用，那么你也可以直接用NotificationHelper建一个基本够用的通知，如下：
```
        //创建builder
        NotificationCompat.Builder builder = mNotificationHelper.init(R.drawable.ic_delete)//初始化,设置小图标,必须调用,一般设置app的icon
                .setLargeIcon(R.drawable.ic_delete)//设置大图标
                .setTitle("提示")//设置标题
                .setContent("内容")//设置内容
                .setActivityIntent(null, 1, TestActivity.class)//设置点击跳转的activity
                //                .setBroadcastIntent(null,1,MyBroadcast.class)//设置点击跳转的广播类<注：一般设置静态广播类接收>
                .createBuilder(MainActivity.this);//创建builder
```
其中.setActivityIntent(null, 1, TestActivity.class)是你有点击需要跳转的时候才会添加进去。
然后.setBroadcastIntent(null,1,MyBroadcast.class)是你涉及需要广播统一处理的时候则添加进去。
一般广播处理的话，会建一个静态注册的广播，用于统一处理通知消息。

ok，接着讲 setActivityIntent(null, 1, TestActivity.class) 与 setBroadcastIntent(null,1,MyBroadcast.class) 中的第一个参数，均是 bundle，一个用于通知向activity传参，一个用于通知向广播传参。
在Activity和广播中，你可以向下面这样接收传过来的bundle：
```
        //创建工具类对象
        mNotificationHelper = new NotificationHelper();

        //activity中接收bundle
        Bundle actBundle=mNotificationHelper.getActivityBundle(intent,1);

        //broadcast中接收bundle
        Bundle broBundle=mNotificationHelper.getBroadcastBundle(intent,1);
```
其中 getActivityBundle 中的第二个参数，为 创建builder时setActivityIntent(null, 1, TestActivity.class) 方法中的第二个参数。
getBroadcastBundle 中的第二个参数，为创建 builder 时 setBroadcastIntent(null,1,MyBroadcast.class)方法中的第二个参数。
#### 三. NotificationHelper 创建自定义通知
NotificationHelper 除了可以创建系统样式通知外，也能自定义布局通知。
先贴一个自定义通知的布局test_view.xml代码：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"

    android:id="@+id/container_layout"
    android:layout_width="match_parent"
    android:gravity="center"
    android:layout_height="300dp"
    android:background="#ffffff"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="20dp"
        android:gravity="center"
        android:text="测试test"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</LinearLayout>
```
关键设置仍在builder，若你需要写一个只供展示，没有点击事件的通知，你可以建一个简单的builder，像下面这样：
```
        //创建自定义布局的builder
        NotificationCompat.Builder customerBuilder = mNotificationHelper.init(R.mipmap.ic_launcher)//初始化,设置小图标,必须调用,一般设置app的icon
                //创建自定义布局的builder, R.layout.test_vie为自定义布局
                .createCustomBuilder(MainActivity.this, R.layout.test_view, null);
```
当然，你也可以建一个基本的，含点击什么的通知，那么你需要这样写你的builder：
```
        //创建自定义布局的builder
        NotificationCompat.Builder customerBuilder = mNotificationHelper.init(R.mipmap.ic_launcher)//初始化,设置小图标,必须调用,一般设置app的icon
                //创建自定义布局的builder, R.layout.test_vie为自定义布局
                .createCustomBuilder(MainActivity.this, R.layout.test_view, new NotificationHelper.OnRemoteViewListener() {
                    @Override
                    public RemoteViews getRemoteView(RemoteViews remoteViews) {
                        remoteViews.setTextViewText(R.id.tv,"大家好");
                        
//                        //设置整个通知栏事件
//                        remoteViews.setOnClickPendingIntent(pendingIntent);

                        //设置自定义布局中textView的点击跳转事件
                        Intent intent = new Intent(MainActivity.this, TestActivity.class);//cls填类似“TestActivity.class”格式
                        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        remoteViews.setOnClickPendingIntent(R.id.tv,pendingIntent);
                        return remoteViews;
                    }
                });
```
如果你还需要对customerBuilder做些其他基本设置，你也可以利用NotificationCompat.Builder 的api给 customerBuilder 直接设置。
#### 四.自定义通知需要注意的问题
自定义通知涉及到RemoteViews，RemoteViews对布局或者控件有些要求。
通知栏RemoteViews只支持以下布局和控件：
```
布局容器Layout:
FrameLayout, LinearLayout, RelativeLayout
 
控件Component:
AnalogClock, Button, Chronometer, ImageButton, ImageView, ProgressBar, 
TextView, ViewFlipper, ListView, GridView, StackView, AdapterViewFlipper
```
超出这些控件布局，运行时会报以下错误：
```
android.app.RemoteServiceException: Bad notification posted from package com.example.testdemo: 
Couldn't expand RemoteViews for: StatusBarNotification
```
#### 五.发送通知与取消通知
在第三，四步的时候，你已经创建了一个系统样式的builder或者自定义布局的builder，接着你就可以发送通知和取消通知了。
发送通知：
```
 //发起通知
 mNotificationHelper.sendNotification(MainActivity.this,notificationId,customerBuilder);
```
在使用完通知后，你需要取消通知，一般我们在界面销毁时调用取消通知的代码，取消一个通知，你可以这样：
```
//注销通知
mNotificationHelper.cancel(MainActivity.this,1);
```
需要注意的是，发送通知和取消通知中的 notificationId 要是一 一 对应的。




