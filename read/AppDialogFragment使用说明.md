## AppDialogFragment使用说明

### 概述
AppDialogFragment作为一个dialog的父类，集中了dialog创建，展示需要的各种设置，它的出现是为了更加便捷的实现一个dialog。

### 使用说明
#### 一. 创建一个dialog的准备
若你要新建一个dialog，你需要继承AppDialogFragment，这样可以帮你快速建一个dialog，具体操作示例如下
```
public class MyDialog extends AppDialogFragment{

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.5,0.5};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
```
#### 二. 设置dialog显示大小
dialog的显示大小由以下方法确定
```
    @Override
    protected double[] getWindowSize() {
        return new double[]{width,height};
    }
```
其中 width表示屏幕宽度比例，为float，height表示屏幕高度比例，为float。
##### 2.1 显示全屏dialog
若要显示全屏，你可以在MyDialog中这样重写 getWindowSize() 方法
```
    @Override
    protected double[] getWindowSize() {
        return new double[]{super.MATCH_PARENT,super.MATCH_PARENT};
    }
```
则dialog显示效果图如下  
![1.gif](https://upload-images.jianshu.io/upload_images/6127340-169d6e0d343f911e.gif?imageMogr2/auto-orient/strip)
##### 2.2 显示比例大小dialog
若要显示宽度是屏幕的0.8，高度是屏幕0.3大小的dialog，你可以像下面这样重写getWindowSize() 
```
    @Override
    protected double[] getWindowSize() {
        return new double[]{0.8f,0.3f};
    }
```
显示效果如下  
![2.gif](https://upload-images.jianshu.io/upload_images/6127340-d5a614e5c0e4421b.gif?imageMogr2/auto-orient/strip)
#### 三.dialog中控件初始化
假设你的MyDialog中有一个控件button，那么我们可以有两种方式来初始化它。
#### 3.1 利用getView()初始化控件
```
//声明控件
private Button mBtn;

//初始化控件
mBtn= (Button) getView(R.id.btn);
```
#### 3.2 利用butterknife初始化控件
你先需要在你的项目中添加butterknife引用，在app对应的model中添加如下依赖：
```
dependencies {

    //butterknife
    implementation 'com.jakewharton:butterknife:10.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'

    //其他引用省略
    //......

}
```
然后在你的MyDialog中这样初始化控件
```
    @BindView(R.id.btn)
    Button mBtn;
```
#### 四.给dialog设置圆角背景
先写一个圆角背景的xml文件，corner_bg.xml代码如下：
```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#00ff00" />
    <corners android:radius="15dp"/>
</shape>
```
给dialog设置背景有两种方式
- 在xml中设置背景
- 用 setBackGroundId 方法在代码中设置背景

这两种设置只能选择一种。
##### 4.1 在xml中设置背景
以NormalDialog为例，你可以直接在其对应xml布局normal_dialog.xml中添加圆角背景，如下：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    tools:context=".MainActivity"
    android:background="@drawable/corner_bg">

   //其他代码省略
   //......
</androidx.constraintlayout.widget.ConstraintLayout>
```
这时你需要在代码中配合使用 setRidShadow 方法，使用时范例如下：
```
       //圆角背景dialog
        new NormalDialog()
                .setRidShadow(true)//设置为false会有圆角阴影，设置为true则不会有，默认为false
                .showDialog(getSupportFragmentManager());//显示dialog
```
显示效果如下  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-fe3f8752bdd3f6d0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
若你此时设置 setRidShadow(false)，则会出现如下现象  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-8cc82ae464595556.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
因此，当你是在xml中设置dalog背景的时候，则需要设置 setRidShadow(true)。  
注意 setRidShadow 方法只需要处理圆角背景的时候才调用
##### 4.2 用 setBackGroundId 方法在代码中设置背景
当然，你也可以不在xml中设置圆角背景，而是直接在代码中设置圆角背景。在代码中设置背景如下(以设置圆角背景corner_bg为例)：
```
        new NormalDialog()
                .setBackGroundId(R.drawable.corner_bg)
                .showDialog(getSupportFragmentManager());//显示dialog
```
显示效果如下  
![image.png](https://upload-images.jianshu.io/upload_images/6127340-58721a7066b2a7f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 五. dialog设置背景遮罩
##### 5.1 显示时设置背景遮罩
给dialog设置背景遮罩主要涉及到两个方法
```
    /***
     * 设置dialog弹出时是否有遮罩覆盖界面
     * 默认为false，即无遮罩
     */
    setUIShadow(boolean uiShadow)

    /***
     * 设置dialog弹出时是否有遮罩覆盖界面
     * 默认为false，即无遮罩
     * uiShadowAlpha:遮罩弹出时的屏幕亮度(范围0f-1f,0f为黑色,1f为透明),默认为DEFAULT_UI_ALPHA,即0.6f
     */
     setUIShadow(boolean uiShadow,float uiShadowAlpha)
```
当你不想给dialog弹出时设置遮罩的时候，你可以这样设置
```
        new NormalDialog()
                .setUIShadow(false)//弹出时不显示遮罩
                .showDialog(getSupportFragmentManager());//显示dialog
```
显示效果如下  
![3.gif](https://upload-images.jianshu.io/upload_images/6127340-51f9abfe558ad32d.gif?imageMogr2/auto-orient/strip)  
当然，你也可以不设置，因为dialog默认setUIShadow为false。  
如果，你想简单的设置一个背景遮罩，你可以这样  
```
        new NormalDialog()
                .setUIShadow(true)//弹出时显示遮罩
                .showDialog(getSupportFragmentManager());//显示dialog
```
效果如下    
![4.gif](https://upload-images.jianshu.io/upload_images/6127340-739323949b7cdcff.gif?imageMogr2/auto-orient/strip)  
若你对背景遮罩的透明度有要求，那么你可以这样设置：
```
        new NormalDialog()
                .setUIShadow(true,0.4f)//设置背景遮罩透明度0.4f
                .showDialog(getSupportFragmentManager());//显示dialog
```
需要注意的是，当你想设置背景遮罩透明度的时候，setUIShadow(boolean uiShadow,float uiShadowAlpha)  方法中的 uiShadow需要设置为true，然后 uiShadowAlpha 范围为(范围0f-1f,0f为黑色,1f为透明)
##### 5.2 关闭时设置背景遮罩
当姐买你涉及到弹出多个dialog叠加的时候，在关闭顶层dialog的时候，我们希望界面能保持底层dialog原有的背景遮罩，
这时，我们则需要设置关闭时的遮罩透明度。默认情况下，`mDestoryAlpha`取值为`MAX_UI_ALPHA`(`MAX_UI_ALPHA`=1.0f),即正常情况下，关闭dialog
视窗透明度为全透明，关闭dialog时，背景遮罩消失。
当你有多个dialog叠加显示，并在顶层dialog关闭时，要视窗保持底层dialog的背景遮罩透明度,你可以在显示顶层dialog时作如下设置：
```
   new TopDialog()
       .setDestoryAlpha(0.6f)//消失时显示遮罩,透明度设置底层dialog的遮罩透明度
       .showDialog(getSupportFragmentManager());//显示dialog
```
这样，你在关闭TopDialog的时候，视窗就能保证底层dialog仍保持原背景遮罩了。
#### 六. dialog返回键的处理
设置点击返回键dialog消失的方法是：
```
setCancel(true)//返回键是否关闭dialog，默认true
```
若你要拦截dialog的返回键，则不能使用setCancel，而应该使用另一个方法：
```
setOnDialogCancelListener(OnDialogCancelListener listener)
```
下面给出dialog拦截返回键的示例：
```
        new NormalDialog()
                .setOnDialogCancelListener(new AppDialogFragment.OnDialogCancelListener() {
                    @Override
                    public boolean onCancel(DialogInterface dialog, int keyCode, KeyEvent event) {
                        LogUtil.i("=====我拦截了返回键=====");
                        ToastUtil.shortShow("=====我拦截了返回键=====");
                        return true;//此处需要返回true才会拦截返回键事件
                    }
                })
                .showDialog(getSupportFragmentManager());//显示dialog
```
运行效果如下  
![5.gif](https://upload-images.jianshu.io/upload_images/6127340-2d2185457b2b59bd.gif?imageMogr2/auto-orient/strip)
#### 七. 其他方法
```
    /**设置点击屏幕外面是否关闭dialog**/
    public AppDialogFragment setCancelOnTouchOutside(boolean canDismiss)

    /**dialog是否已经显示**/
    isShowing()

    /***
     * 显示dialog，需要传 fragmentManager=getSupportFragmentManager()
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager)

    /**用于子类在使用时创建DialogFragment对象**/
    createFragment(Class<?>cls,Context context,OnCreateFragmentListener listener)

    /**获取editText的值**/
    getTextOfView(TextView textView)
```
#### 八. 一个dialog调用的通常基本设置
继承AppDialogFragment建的dialog拥有众多方法，但在一般情况使用的时候，只需要做些基本设置，以NormalDialg为例,一些基本设置如下
```
        new NormalDialog()
//                .setRidShadow(true)//当dialog设置圆角背景的时候，会留下边角阴影,设置true的时候，可去除边角,默认为false，即有边角阴影
                .setUIShadow(false)//默认弹出diaolog时,界面无遮罩
                .setCancel(true)//返回键是否关闭dialog，默认true
                .setCancelOnTouchOutside(true)//屏幕外点击是否关闭，默认true
                .showDialog(getSupportFragmentManager());//显示dialog
```



