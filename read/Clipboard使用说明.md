## Clipboard使用说明

### 概述
Clipboard作为一个剪切板工具类，具备监听本app界面中复制/剪切/粘贴 操作 的功能，同时还能监听其他app复制的内容，然后打开本app也能获取到
(即在当前app读取剪切板内容的功能)。下面对 Clipboard 的使用做一个说明。

### 使用说明
#### 一.对象初始化
Clipboard作为剪切板监听帮助类，初始化ClipboardHelper对象的话，你可以这样：
```
 Clipboard clipboard=new Clipboard();
```
#### 二.监听本app的 复制/剪切/粘贴 操作
监听本app的 “复制/剪切/粘贴”动作时，涉及到注册注销的问题。
##### 2.1 注册监听
若在MainActivity中，你需要监听剪切板操作的时候，你可以像下面这样：
```
        //注册并监听剪切板操作
        clipboard.registerClipEvents(MainActivity.this,new Clipboard.OnClipboardListener() {
            @Override
            public void clipboard(String content) {
                LogUtil.i("==========监听剪切内容======="+content);


            }
        });
```
registerClipEvents(Context context,OnClipboardListener listener) 此方法用于监听本app中复制，剪切，粘贴动作。
##### 2.2 注销监听
注销基于本app的监听，你可以像下面这样处理：
```
        //注销剪切板事件
        clipboard.unRegisterClipEvents();
```
#### 三.监听其他app的 复制/剪切/粘贴 过来的内容(即获取剪切板内容)
##### 3.1 适用场景
当你需要用户在其他app中复制了一个链接，然后进入 本app时，立马打开与该链接对应的活动的时候，可以使用本方法实现。
##### 3.2 获取剪切板内容
若你要在你的app中获取剪切板内容，你可以像下面这样：
```
    @Override
    protected void onResume() {
        super.onResume();

        //获取剪切板内容
        clipboard.getClipboardContent(MainActivity.this, new Clipboard.OnClipboardListener() {
            @Override
            public void clipboard(String content) {
               
            }
        });
    }
```
##### 3.3 需要注意的问题
由于 clipboard.getClipboardContent 方法想要实现的效果是：用户一打开本app就获取剪切板的内容，所以你最后将获取剪切板
内容的这段代码放到activity生命周期的 onResume() 方法中，以达到一旦app回到前台，就能读取到剪切板内容。

#### 四.复制口令
##### 4.1 使用场景
当你在你app中点击button，需要将某一数值复制到剪切板上(类似你选中你app中某值，然后复制的操作),你可以考虑使用复制口令的方法。
##### 4.2 复制口令的方法
例如需要将"大家后"复制到粘贴板上，你可以这样：
```
clipboard.setClipboardContent(MainActivity.this,"大家好");
```

#### 五. 其他方法
除此之外，Clipboard还提供了一个设置本app中TextView内容可直接复制的设置方法。因为一般写的app中，如无特殊需求，TextView显示的内容都是禁止复制的。
设置TextView内容可复制方法如下：
```
/**设置textView可被复制,剪切和粘贴**/
canBePasted(TextView textView)
```
