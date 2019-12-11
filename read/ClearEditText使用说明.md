## ClearEditText使用说明

### 概述
ClearEditText是一款含删除按钮的输入框控件，用户使用时，能很方便的删除输入框中的内容。
ClearEditText还能实现左侧含图标的展示效果。

### 使用说明
#### 1. ClearEditText在布局中的引入
在 activity_main.xml 布局中引入ClearEditText，你可以像下面这样：
- 在布局开头声明自定义属性引用:
```
xmlns:clearEditText="http://schemas.android.com/apk/res-auto"
```
类似下面这样：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:clearEditText="http://schemas.android.com/apk/res-auto"

    android:id="@+id/container_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
//其他代码省略
//......
</androidx.constraintlayout.widget.ConstraintLayout>
```
- 在布局中引入控件，类似下面这样(注：ClearEditText路径以你实际项目路径为主)：
```
    <com.android.commonlibrary.widget.ClearEditText
        android:id="@+id/edt_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="30dp"
        android:layout_marginTop="24dp"
        android:layout_marginEnd="30dp"
        android:background="#00000000"
        android:drawablePadding="10dp"
        android:hint="请输入联系人名称"
        android:paddingTop="15dp"
        android:paddingBottom="15dp"
        android:singleLine="true"
        android:textColor="#000000"
        android:textColorHint="#aaaaaa"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv"/>
```
##### 2. ClearEditText几个重要属性的讲解
ClearEditText除了以上的一些基本设置外，还有几个重要属性的设置：  
**设置右侧删除按钮**
```
        //设置右侧删除按钮的图标
        android:drawableRight="@drawable/ic_clear_edit_delete"
        //设置右侧删除按钮的宽度
        clearEditText:rightDrawable_width="15dp"
        //设置右侧删除按钮的高度
        clearEditText:rightDrawable_height="15dp"  
```
以上属性可以不设置，默认图标是一个带"X"的圆形图标，宽高默认15dp  
**设置左侧图标**
```    
        //设置输入框左侧图标，默认为null，即不设置
        android:drawableLeft="@drawable/ic_clear_edit_delete"
        //设置输入框左侧图标宽度，默认20dp，当有左侧图标时才设置
        clearEditText:leftDrawable_width="20dp"
        //设置输入框左侧图标高度，默认20dp，当有左侧图标时才设置
        clearEditText:leftDrawable_height="20dp"
```
**设置图标和文字间距**
```
android:drawablePadding="10dp"
```
**设置输入框光标颜色宽度**
```
android:textCursorDrawable="@drawable/clear_edit_cursor"
```
clear_edit_cursor.xml代码如下：
```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#ffce38" />
    <size android:width="1dp"/>
</shape>
```
不设置此属性的时候，输入框光标显示系统默认
##### 3. 在MainActivity中初始化及监听
初始化：
```
//声明控件
private ClearEditText mClearEditText;

//控件初始化
mClearEditText = findViewById(R.id.edt_name);
```
若要监听ClearEditText输入动作，你可以这样设置监听：
```
        //监听输入的动作
        mClearEditText.setOnEditerListener(new ClearEditText.OnEditerListener() {
            @Override
            public void afterTextChanged(View view, Editable s) {
               //处理你的逻辑
               //......
            }
        });
```

