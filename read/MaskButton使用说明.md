## MaskButton使用说明

### 概述
MaskButton 是一款 实现按钮点击变暗 特效的 button。它的出现是为了方便快捷的实现 按钮点击变色效果。开发过程中，若涉及到按钮点击效果的话，
你再也不用写那么多繁琐的 xml背景文件了，只需要一个，甚至只需要给 button设置一个颜色，便可实现按钮点击特效。

### 使用说明
#### 1. MaskButton使用
若你要在你的MainActivity中使用MaskButton控件，你需要在MainActivity对应的布局中添加控件引用，类似下面这样(注：具体包路径以代码为准)：
```
    <com.android.commonlibrary.widget.MaskButton
        android:id="@+id/btn"
        android:layout_width="80dp"
        android:layout_height="40dp"
        android:layout_marginTop="24dp"
        android:text="登录测试"
        android:textColor="#0000ff"
        android:background="#00ff00"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv" />
```
#### 2. 支持背景设置种类
MaskButton 的变色特效主要作用于 android:background="xxx" 属性上，其中 xxx 支持以下几种background的设置：
```
       android:background="@drawable/confirm_btn_bg"  //drawable类xml文件
       android:background="#00ff00"  //纯色值
       android:background="@mipmap/ic_launcher"  // mipmap类图片
```
#### 3. 设置/取消 按钮点击效果
MaskButton 默认是开启 按钮点击变暗特效的，按钮点击变暗特效 的开启与关闭 设置如下：
```
        //开启按钮点击变暗特效
        maskBtn.setTouchEffect(true);
        
        //关闭按钮点击变暗特效
        maskBtn.setTouchEffect(false);
```
