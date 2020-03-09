## LoadProgressBar使用说明

### 概述
`LoadProgressBar`是一款自定义进度条控件。该进度条具备两种表现形式： 
- 水平模式
- 圆形模式
可设置多种属性，调用灵活

### 使用说明
#### 一. ProgressBar特性
`ProgressBar`是一款可自行设置众多属性的进度条，其参数以属性形式在布局中设置。下面对其分两种表现形式做设置参数讲解。其表现形式分为
- 水平进度条
- 圆形进度条
##### 1.1 水平进度条可设置参数
```
        progress_unreach_color="#FFA6ADB7"  //未完成部分色值
        progress_unreach_height="4dp" //未完成部分高度
        progress_reach_color="#FF8AD858" //完成部分色值
        progress_reach_height="4dp" //完成部分高度
        progress_text_color="#FF000000" //文字颜色
        progress_text_size="12sp" //文字大小
        progress_text_offset="5dp"  //文字与进度条间距
        progress_circle_style="false" //是否为圆形进度条(不设置默认是false)
```
##### 1.2 圆形进度条可设置参数
```
        progress_unreach_color="#FFA6ADB7"  //未完成部分色值
        progress_unreach_height="4dp"  //未完成部分高度
        progress_reach_color="#FF8AD858" //完成部分色值
        progress_reach_height="4dp" //完成部分高度
        progress_text_color="#FF000000" //文字颜色
        progress_text_size="12sp" //文字大小
        progress_text_offset="5dp" //文字与进度条间距(圆形进度条不需要设置此属性)
        progress_radius="40dp" //圆半径
        progress_circle_style="true" //是否为圆形进度条(为圆形进度条时必须设置为true)
```
##### 1.3 主要使用方法
`ProgressBar`只有一个可用的更新进度条的方法：
```
    /***
     * 更新进度条
     * @param progress 进度
     * @return true:已经更新到最大,需要移除handle消息  false:进度条更新
     */
    public boolean update(int progress)
```
#### 二. ProgressBar的使用
##### 2.1 建立自定义属性
在`attrs`文件中新建如下引用样式(不会自定义的，可参考[番外篇2：自定义View属性全解](https://www.jianshu.com/p/0eb7abe21996))：
```
<?xml version="1.0" encoding="utf-8"?>
<resources>


    <!--  LoadProgressBar自定义属性样式  -->
    <declare-styleable name="LoadProgressBar">
        <attr name="progress_unreach_color" format="color"></attr>
        <attr name="progress_unreach_height" format="dimension"></attr>
        <attr name="progress_reach_color" format="color"></attr>
        <attr name="progress_reach_height" format="dimension"></attr>
        <attr name="progress_text_color" format="color"></attr>
        <attr name="progress_text_size" format="dimension"></attr>
        <attr name="progress_text_offset" format="dimension"></attr>
        <attr name="progress_circle_style" format="boolean"/>
        <attr name="progress_radius" format="dimension"/>
    </declare-styleable>

</resources>
```
##### 2.2 在布局中引用LoadProgressBar控件
在`MainActivity`对应布局文件`activity_main.xml`中声明` LoadProgressBar`控件的属性：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:pain="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

//其他代码省略
//......

</androidx.constraintlayout.widget.ConstraintLayout>
```
其中` xmlns:pain="http://schemas.android.com/apk/res-auto"`即为控件属性声明，`pain`这个可以随便取，但是要保证`attrs`文件中每个属性的`name`都唯一。然后在xml中给控件设置属性可以像下面这样：
```
pain:progress_unreach_color="#FFA6ADB7" //设置未完成部分颜色
```
接着再在`activity_main.xml`中引用控件：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:pain="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    
    <!--水平进度条-->
    <com.android.commonlibrary.widget.LoadProgressBar
        android:id="@+id/progress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="10dp"
        android:layout_marginEnd="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv"
        android:layout_marginTop="30dp"

        pain:progress_unreach_color="#FFA6ADB7"   //可选
        pain:progress_unreach_height="4dp"  //可选
        pain:progress_reach_color="#FF8AD858"  //可选
        pain:progress_reach_height="4dp"  //可选
        pain:progress_text_color="#FF000000"  //可选
        pain:progress_text_size="12sp"  //可选
        pain:progress_text_offset="5dp"  //可选
        pain:progress_circle_style="false"/>  //可选

    <!--圆形进度条-->
    <com.android.commonlibrary.widget.LoadProgressBar
        android:id="@+id/progress2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="10dp"
        android:layout_marginEnd="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/progress"
        android:layout_marginTop="30dp"

        pain:progress_unreach_color="#FFA6ADB7"  //可选
        pain:progress_unreach_height="4dp"  //可选
        pain:progress_reach_color="#FF8AD858"  //可选
        pain:progress_reach_height="4dp"  //可选
        pain:progress_text_color="#FF000000"  //可选
        pain:progress_text_size="12sp"  //可选
        pain:progress_text_offset="5dp"  //可选
        pain:progress_radius="40dp"  //可选
        pain:progress_circle_style="true"/> //必须设置，且属性必须为true

</androidx.constraintlayout.widget.ConstraintLayout>
```
##### 2.3 在MainActivity中使用LoadProgressBar控件
```
       //声明控件 
       private LoadProgressBar mLoadProgressBar;
       
       //初始化
       mLoadProgressBar=findViewById(R.id.progress);
       
       //更新进度(true:已经更新到最大,需要移除handle消息  false:进度条更新)
       boolean complete=mLoadProgressBar.update(progress);
```

