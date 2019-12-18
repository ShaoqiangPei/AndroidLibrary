## LoadingDialog使用说明

### 概述
LoadingDialog 是专门用于网络通讯期间用于显示的 dialog，其目的是防止用户在通讯期间对界面进行操作，也在一定程度上防止了用户重复点击的可能。增加用户体验。

### 使用说明
#### 一. LoadingDialog 弹出的简单使用
如果你想弹出一个简单快速的LoadingDialog，你可以像下面这样：
```
        //显示网络加载dialog
        LoadingDialog.getInstance().showLoading(mContext);
```
#### 二. LoadingDialog 设置提示语 及 不弹出的设置
LoadingDialog 默认弹出时显示的提示语为："正在加载...",若你想更改提示语，你可以像下面这样：
```
        //显示网络加载dialog
        LoadingDialog.getInstance()
                //设置弹出dialog时提示语
                .setTitle("大家好")
                //弹出dialog
                .showLoading(mContext);
```
LoadingDialog在使用的时候，一般是会弹出的，若你在通讯过程中已经设置好了LoadingDialog弹出和隐藏的位置，那么想快速做到通讯时不弹出此dialog，你可以这样：
```
        //网络加载框的处理
        //显示网络加载dialog
        LoadingDialog.getInstance()
                //设置不弹出dialog
                .setDismiss(true)
                //此句代码要加
                .showLoading(mContext);
        
        //网络发起通讯
        //接收返回结果
        
        //隐藏网络加载dialog
```
#### 三. LoadingDialog的隐藏
通讯收到结果后，你可以像下面这样隐藏你的网络加载框:
```
        //隐藏网络加载dialog
        LoadingDialog.getInstance().hideLoading();
```
#### 四. 其他方法
LoadingDialog还有一个用于判断dialog是否该显示的通用方法：
```
    /**
     * 判断dialog是否该show
     * @param dialog
     * @param context 为 activity 上下文
     * @return
     */
    public boolean shouldShow(Dialog dialog, Context context)
```

