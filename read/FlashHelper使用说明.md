## FlashHelper使用说明

### 概述
FlashHelper 是一个实现控件闪烁效果的帮助类。当你需要点击按钮，让按钮不断闪烁的时候，可以考虑使用此类。FlashHelper中包含启动和停止控件闪烁的效果。

### 使用说明
#### 1. FlashHelper对象初始化
FlashHelper 是一个单例，你可以像下面这样初始化FlashHelper对象：
```
FlashHelper helper=FlashHelper.getInstance();
```
一般我们直接用：
```
FlashHelper.getInstance();
```
#### 2. 设置控件闪烁时间间隔
默认时间间隔为 300 毫秒
```
//flashTime单位为毫秒
setFlashTime(long flashTime)
```
#### 3. 启动控件闪烁特效
```
    /**开启View闪烁效果**/
    startFlick(View view)
```
#### 4. 停止控件闪烁特效
```
    /**取消View闪烁效果**/
    stopFlick(View view)
```
#### 5. 调用示例
```
    //启动mTextView闪烁特效
    FlashHelper.getInstance()
           .setFlashTime(300)//若不设置,则默认时间间隔为300毫秒
           .startFlick(mTextView);
        
        
    //停止闪烁
    FlashHelper.getInstance().stopFlick(mTextView);
```
