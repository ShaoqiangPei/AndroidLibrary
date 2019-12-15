## ImageTextView使用说明

### 概述
ImageTextView 是一款能在TextView旁边显示图标的TextView，其可以设置图标的大小，还可以设置 图标 与TextView的相对位置。

### 使用说明
#### 一. ImageTextView特性
ImageTextView是一个可以在旁边设置icon的TextView，其支持以下功能：
- 在ImageTextView左侧设置icon
- 在ImageTextView上侧设置icon
- 在ImageTextView右侧设置icon
- 在ImageTextView下侧设置icon

#### 二. ImageTextView的使用
##### 2.1 布局中声明自定义属性
在将ImageTextView引入布局的时候，在布局头部添加自定义属性声明：  
```
xmlns:ImageTextView="http://schemas.android.com/apk/res-auto"
```
类似下面这样：  
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:ImageTextView="http://schemas.android.com/apk/res-auto"
    //其他代码省略
    //...... 
   >

    //其他代码省略
    //......
 
</androidx.constraintlayout.widget.ConstraintLayout>
```
##### 2.2 布局中引入控件
在布局中引用ImageTextView，你可以像下面这样(注意：ImageTextView路径以实际为准)：  
```
    <com.android.commonlibrary.widget.ImageTextView
        android:id="@+id/imv_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:text="带图片的TextView"
        android:textColor="#0000ff"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv"
        ImageTextView:drawable="@drawable/activate_card2"
        ImageTextView:drawableWidth="20dp"
        ImageTextView:drawableHeight="20dp"
        ImageTextView:position="1"/>
```
这里需要解释几个属性：  
-  ImageTextView:drawable：给 ImageTextView设置要显示的资源drawable
-  ImageTextView:drawableWidth：图标宽度
-  ImageTextView:drawableHeight：图标高度
-  ImageTextView:position：图标显示的位置:  
```
position="1"  图标显示在TextView 左侧
position="2"  图标显示在TextView 上侧
position="3"  图标显示在TextView 右侧
position="4"  图标显示在TextView 下侧
```
要设置图标和文字间距的话，可以用属性：  
```
android:drawablePadding="5dp"
```
#####2.3 控件在mainActivity中使用
在MainActivity中使用如下：  
```
//声明
private ImageTextView mImageTextView;

//初始化
mImageTextView = findViewById(R.id.imv_tv);
```
ImageTextView不仅能在xml中通过自定义属性来设置drawable和drawable的位置，也可以在代码中设置，具体如下：  
```
//设置左边显示图片
mImageTextView.setDrawableLeft(R.drawable.activate_card2,MainActivity.this);
//设置上边显示图片
mImageTextView.setDrawableTOP(R.drawable.activate_card2,MainActivity.this);
//设置右边显示图片
mImageTextView.setDrawableRight(R.drawable.activate_card2,MainActivity.this);
//设置下边显示图片
mImageTextView.setDrawableBottom(R.drawable.activate_card2,MainActivity.this);
```
代码中设置drawable的宽高：
```
mImageTextView.setDrawableWidth(50);//设置drawable宽度
mImageTextView.setDrawableHeight(50);//设置drawable高度
```
代码中不给ImageTextView设置drawable:
```
//设置不显示图片
mImageTextView.setNullDrawable();
```

