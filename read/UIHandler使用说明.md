## UIHandler使用说明

### 简介
`UIHandler`是对 `Handler`的一个封装，其继承自`Handler`,本质还是一个`Handler`，封装的目的是为了避免`Handler`在使用过程中出现内存泄漏。

### 使用说明,
#### 一.声明及初始化接收消息
你可以在需要用到`UIHandler`传递消息时，这样初始化它:
```
    //声明
    private UIHandler mUIHandler;
    
    //然后在初始化时接收处理消息
    mUIHandler=new UIHandler(MainActivity.this,new UIHandler.OnUIHandListener() {
        @Override
        public void uiHandle(Object obj, Message msg) {
          //处理接收消息的逻辑
          //...
        }
    });
```
注：在`uiHandle(Object obj, Message msg)`方法内部已经做了`msg.what`的移除,所以你在调用`uiHandle(Object obj, Message msg)`处理内部逻辑的时候，
无需再调用`removeMessages(msg.what)`方法。
#### 二.发送消息
`UIHandler`继承自`Handler`，故发下哦那个消息和`Handler`一样。下面给出一个`UIHandler`发送消息的示例:
```
    //发送消息
    mUIHandler.sendEmptyMessage(1);
```
