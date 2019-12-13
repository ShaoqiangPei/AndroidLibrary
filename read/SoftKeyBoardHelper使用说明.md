## SoftKeyBoardHelper使用说明

### 概述
SoftKeyBoardHelper是一个软键盘监听工具类，主要用于监听软件盘的 显示 和 隐藏。
使用方便简单，对界面布局无限制。

### 使用说明
以在TestActivity中监听软键盘为例。
#### 1. 在 manifast.xml中给TestActivity设置键盘相关属性：
```
        <activity android:name=".TestActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|touchscreen"
            android:screenOrientation="portrait"/>
```
#### 2. 在TestActivity中使用
在activity中监听软键盘的显示和隐藏，你可以类似下面这样：
```
//监听软键盘的显示和隐藏
SoftKeyBoardHelper.setOnListener(TestActivity.this, new SoftKeyBoardHelper.OnSoftKeyBoardChangeListener() {
    @Override
    public void keyBoardShow(int height) {
        //软键盘显示时处理你的逻辑 (height为软键盘高度)
        //......
    }

    @Override
    public void keyBoardHide(int height) {
        //软键盘隐藏时处理你的逻辑 (height为软键盘高度)
        //......
    }
});
```


