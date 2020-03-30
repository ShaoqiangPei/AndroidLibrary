## TitleBar使用说明

### 概述
TitleBar 是一个自定义标题栏，里面含有 左返回键，标题，右返回键 三个控件，使用十分灵活。为你自定义标题栏提供便捷。  
此类已废弃，建议使用其替代类[TitleBar2](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/TitleBar2%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)

### 使用
#### 一.TitleBar的引用
##### 1.1 要在MainActivity中使用TitleBar，则在activity_main.xml中引入TitleBar控件如下(控件包路径以实际为准)：
```
    <com.android.commonlibrary.widget.TitleBar
        android:id="@+id/title"
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:background="#ff0000"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
```
在MainActivity中声明及初始化TitleBar：
```
    //声明
    private TitleBar mTitleBar;

   //初始化(注意不能用butternikfe等方式初始化,只能使用原始方式初始化，否则获取的mTitleBar为null)
   mTitleBar=findViewById(R.id.title_bar);
```
这里需要注意的是，不能使用第三方库进行初始化，要使用最原始的 “findViewById” 方式初始化，否则得到的 mTitleBar 会为 null。
#### 二.TitleBar的使用样式  
TitleBar非为左边返回键，中间标题，右边返回键。默认情况下，TitleBar中三个子控件都不显示，就像下面这样(为了辨识，我给TitleBar加红色背景)：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-3b47a2c3c16e81cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
获取左边返回键，中间标题，右边返回键控件，你可以类似下面这样：  
```
        //左边返回键
        ImageTextView tvLeft = mTitleBar.getTvLeft();
        //中间标题
        TextView tvTitle = mTitleBar.getTvTitle();
        //右边返回键
        ImageTextView tvRight = mTitleBar.getTvRight();
```
显示三个控件
```
        //左返回键显示
        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
        //中间标题显示
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        //右返回键显示
        mTitleBar.getTvRight().setVisibility(View.VISIBLE);
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-2c7a8b845a827ee0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
给mTitleBar设置文字：
```
        //设置文字
        mTitleBar.getTvTitle().setText("登录");
        mTitleBar.getTvLeft().setText("返回");
        mTitleBar.getTvRight().setText("下一步");
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-5f87d05f553779e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
ok,下面以左边返回键(tvLeft)及中间控件(tvTitle)进行讲解[右边返回键使用与左边返回键使用雷同]。
##### 2.1 只显示标题
```
    /**只显示标题**/
    private void showTitle(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-a9a8fbe91e752ed9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.2 显示返回键图标  
```
    /**显示左返回键图标**/
    private void showLeftDrawable(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");

        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-dfb01d76ede6e739.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.3 显示返回键文字
```
    /**显示左返回键文字**/
    private void showLeftText(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");

        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar.getTvLeft().setText("返回");
        mTitleBar.getTvLeft().setNullDrawable();
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-bffae184d76b8371.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.4 显示返回键文字和图标
```
    /**显示左返回键文字和图标**/
    private void showLeftTextDrawable(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");

        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar.getTvLeft().setText("返回");
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-227974f36bd4e39e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.5 设置左返回键图标
```
    /**设置左返回键图标**/
    private void setLeftDrawable(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");

        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar.getTvLeft().setText("返回");

        //设置左边图标
        mTitleBar.getTvLeft().setDrawableLeft(R.mipmap.ic_launcher, MainActivity.this);
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-6497e2cca84fb713.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.6 设置左返回键图标大小
```
    /**设置左返回键图标大小**/
    private void setLeftDrawableSize(){
        mTitleBar.getTvTitle().setVisibility(View.VISIBLE);
        mTitleBar.getTvTitle().setText("登录");

        mTitleBar.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar.getTvLeft().setText("返回");

        //设置左边图标
        mTitleBar.getTvLeft().setDrawableLeft(R.mipmap.ic_launcher, MainActivity.this);
        //设置左边图标宽高
        mTitleBar.getTvLeft().setDrawableWidth(40);
        mTitleBar.getTvLeft().setDrawableHeight(40);
    }
```
效果如下：  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-1e47f6f41dd91870.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
##### 2.7 设置图标和文字间的padding
```
        //设置左返回键drawable的padding
        mTitleBar.setLeftDrawablePadding(5);
        //设置右返回键drawable的padding
        mTitleBar.setRightDrawablePadding(5);
```
##### 2.8 设置整个控件(左返回键)的margin
```
        //设置整个控件的margin
        mTitleBar.setMargins(mTitleBar.getTvLeft(),2,2,2,2);
```
##### 2.9 设置整个控件(左返回键)的padding
```
        //设置整个控件的padding
        mTitleBar.setPaddings(mTitleBar.getTvLeft(),2,2,2,2);
```

