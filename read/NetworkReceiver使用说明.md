## NetworkReceiver使用说明

### 概述
NetworkReceiver 是一个用于实时监听网络链接状态的工具类，可以方便于开发者来监听app是否在当前时间链接了网络。
本监听只能获取两种状态：已经链接的网络，未链接网络

### 使用说明
#### 使用前置
NetworkReceiver是一个网络监听类，它的使用涉及到网络权限，所以我们在使用之前，需要在 AndroidManifast.xml中增加以下权限：
```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
注意：你并不需要另外在代码中添加 Android6.0+ 权限。

#### 使用说明
在MainActivity中声明对象：
```
    //声明对象
    private NetworkReceiver mNetworkReceiver;
```
然后初始化时，实例化广播对象：
```
    //初始化对象并注册网络监听广播
    mNetworkReceiver = new NetworkReceiver(MainActivity.this);
```
你可以像下面这样在需要的地方监听网络连接状态：
```
        /**监听网络**/
        mNetworkReceiver.setOnNetworkListener(new NetworkReceiver.OnNetworkListener() {
            @Override
            public void available(NetworkInfo networkInfo) {
                LogUtil.i("=========有网络====");
                ToastUtil.shortShow("有网络");
                mTv.setText("有网络");


            }

            @Override
            public void unavailable(NetworkInfo networkInfo) {
                LogUtil.i("=========无网络====");
                ToastUtil.shortShow("无网络");
                mTv.setText("无网络");


            }
        });
```
在MainActivity中使用完广播监听后，注销广播：
```
        //注销网络广播监听
        if (mNetworkReceiver != null) {
            mNetworkReceiver.onDestroy();
        }
```


