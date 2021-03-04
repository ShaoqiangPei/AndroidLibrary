## SmsMonitor使用说明

### 概述
`SmsMonitor`是一个监听短信接收并获取新收到短信内容的帮助类。其主要运用于接收短信验证码并实现输入框自动填充。

### 使用说明
#### 一. SmsMonitor主要方法介绍
`SmsMonitor`作为一个单例类，我们主要使用到以下几个方法：
```
    /***
     * 初始化
     *
     * @param context
     */
    public SmsMonitor init(Context context)

    /***
     * 注册短信接收监听(一般在点击"发送短信"按钮时注册)
     *
     * @param phoneNumber：phoneNumber为具体电话号码时表示只监听该号码短信
     *                     phoneNumber表示监听所有短信
     *
     * @param listener: 监听得到的短信数据
     *
     */
    public void registerSmsObserver(String phoneNumber,OnSmsChangeListener listener)

    /***
     * 注销短信接收监听
     *
     * 注：一般在短信接收超时时注销
     *    当然为了保证代码健壮性,在界面退出时也要调用
     */
    public void unRegisterSmsObserver()
```
这里需要注意的是`registerSmsObserver(String phoneNumber,OnSmsChangeListener listener)`中的`phoneNumber=null`时表示监听短信箱中的所有短信，
`phoneNumber=具体电话号码`时，表示监听短信箱中的所有该号码的短信
#### 二. 用户权限
`SmsMonitor`的使用需要在`Androidmanifast.xml`中添加以下权限：
```
<uses-permission android:name="android.permission.READ_SMS"/>
```
当然，你还需要添加`Android 6.0+`手动权限和`FileProvider`文件权限，具体可参考文章
[PermissionsDispatcher动态权限申请kotlin版](https://www.jianshu.com/p/c3da2f4aff34)
接着需要添加`FileProvider`相关处理，大家可参考以下文章
[SpUtil多样加密存储，兼容android9.0](https://www.jianshu.com/p/bbf057ccbcff)

#### 三. SmsMonitor的具体使用
初始化短信监听类对象：
```
        //初始化
        SmsMonitor.getInstance().init(this);
```
然后在点击按钮时发起短信监听注册，类下面这样:
```
        //发送短信验证
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("发送短信");

                //每次点击按钮注册一次短信监听
                SmsMonitor.getInstance().registerSmsObserver(null, new SmsMonitor.OnSmsChangeListener() {
                    @Override
                    public boolean isAuthor(SmsData smsData) {
                        //设置接收短信内容的条件(如只接收信息内容中含"验证码"的信息)
                        String message=smsData.getBody();
                        if(StringUtil.isNotEmpty(message)&&message.contains("验证码")){
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void getSmsData(SmsData smsData) {
                        LogUtil.i("=============接收我要的数据==========");
                        //注:每次收到消息后都会注销短信监听
                        //   要想监听生效,则需下次重新注册监听
                        LogUtil.i("==电话号码: " + smsData.getAddress() + " 短信内容: " + smsData.getBody()+"  时间: "+smsData.getDate());
                        mTvTest.setText("电话号码: " + smsData.getAddress() + "\n短信内容: " + smsData.getBody());
                    }
                });
            }
        });
```
这里需要注意的是,`isAuthor(SmsData smsData)`可用于过滤只接收某些特定短信，如只接收短信内容含`验证码`的短信，我们可以这样写:
```
                SmsMonitor.getInstance().registerSmsObserver(null, new SmsMonitor.OnSmsChangeListener() {
                    @Override
                    public boolean isAuthor(SmsData smsData) {
                        //设置接收短信内容的条件(如只接收信息内容中含"验证码"的信息)
                        String message=smsData.getBody();
                        if(StringUtil.isNotEmpty(message)&&message.contains("验证码")){
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void getSmsData(SmsData smsData) {
                       //以下代码省略
                       //......
                    }
                });
```
当不需要对接收短信做任何限制是，可以`isAuthor(SmsData smsData)`直接返回`true`,类似下面这样:
```
                SmsMonitor.getInstance().registerSmsObserver(null, new SmsMonitor.OnSmsChangeListener() {
                    @Override
                    public boolean isAuthor(SmsData smsData) {
                        //监听收到的所有短信类型,此处不做任何限制，直接返回true
                        //.....
                        return true;
                    }

                    @Override
                    public void getSmsData(SmsData smsData) {
                       //以下代码省略
                       //......
                    }
                });
```
然后在退出界面时，销毁短信监听:
```
    @Override
    protected void onDestroy() {
        //注销短信接收的监听
        SmsMonitor.getInstance().unRegisterSmsObserver();
        super.onDestroy();
    }
```
#### 四. 需要注意的问题
`SmsMonitor`的设计逻辑是，当收到自己想要的短信内容时，会自动注销短信监听。若收到的不是自己想要的短信内容,会继续监听接收短信。
所以，当监听到自己想要的短信后，你还想再次监听短信消息时，别忘记再次注册监听。程序退出时，要记得注销短信监听。



