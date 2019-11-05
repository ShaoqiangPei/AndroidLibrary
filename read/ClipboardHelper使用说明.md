## ClipboardHelper使用说明

### 概述
ClipboardHelper作为一个剪切板工具类，具备监听本app界面中复制/剪切/粘贴 操作 的功能，同时还能监听其他app复制的内容，然后打开本app也能获取到
(即在当前app读取剪切板内容的功能)。下面对 ClipboardHelper 的使用做一个说明。

### 使用说明
#### 一.对象初始化
ClipboardHelper作为剪切板监听帮助类，是一个单例形式。
初始化ClipboardHelper对象的话，你可以这样：
```
 ClipboardHelper helper=ClipboardHelper.getInstance();
```
但我们实际使用过程中，多直接像下面这样使用：
```
ClipboardHelper.getInstance();
```
#### 二.监听本app的 复制/剪切/粘贴 操作
监听本app的 “复制/剪切/粘贴”动作时，涉及到注册注销的问题。
##### 2.1 注册监听
若在MainActivity中，你需要监听剪切板操作的时候，你可以像下面这样：
```
        //注册并监听剪切板操作
        ClipboardHelper.getInstance().registerClipEvents(MainActivity.this, null,new ClipboardHelper.OnClipboardListener() {
            @Override
            public void clipboard(String content) {
                LogUtil.i("==========监听剪切内容======="+content);


            }
        });
```
registerClipEvents(Context context,String value,OnClipboardListener listener) 此方法用于监听本app中复制，剪切，粘贴动作。
当 value 为 null 时，表示监听获取 “复制/剪切/粘贴”的内容，当 value 不为 null 时，表示监听 “复制/剪切/粘贴”动作，并显示 value 内容。
##### 2.2 注销监听
注销基于本app的监听，你可以像下面这样处理：
```
        //注销剪切板事件
        ClipboardHelper.getInstance().unRegisterClipEvents();
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
        ClipboardHelper.getInstance().getClipboardContent(MainActivity.this, new ClipboardHelper.OnClipboardListener() {
            @Override
            public void clipboard(String content) {
               
            }
        });
    }
```
##### 3.3 需要注意的问题
由于 ClipboardHelper.getInstance().getClipboardContent 方法想要实现的效果是：用户一打开本app就获取剪切板的内容，所以你最后将获取剪切板
内容的这段代码放到activity生命周期的 onResume() 方法中，以达到一旦app回到前台，就能读取到剪切板内容。

#### 四. 其他方法
除此之外，ClipboardHelper还提供了一个设置本app中TextView内容可直接复制的设置方法。因为一般写的app中，如无特殊需求，TextView显示的内容都是禁止复制的。
设置TextView内容可复制方法如下：
```
/**设置textView可被复制,剪切和粘贴**/
canBePasted(TextView textView)
```
