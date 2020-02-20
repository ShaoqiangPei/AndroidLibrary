## EmotionView使用说明

### 概述
`EmotionView`是一个自定义实现表情雨效果的控件。  
使用效果详见 [Android实现表情雨效果](https://www.jianshu.com/p/dec57327ecef)

### 使用说明
#### 一.布局及初始化
在`xml`文件中引用控件：
```
    <com.android.commonlibrary.widget.EmotionView
        android:id="@+id/emotion"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginTop="20dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv" />
```
在`activity`中声明及初始化:
```
    //声明控件
    private EmotionView mEmotionView;
    
    //初始化
    mEmotionView=findViewById(R.id.emotion);
```
#### 二.使用及监听
开启动画效果并监听，你可以这样:
```
    //设置图片组并开始表情雨
    mEmotionView.setDuration(1000)//设置下落时间2秒
          .startRain(getBitmaps(), new EmotionView.OnRainListener() {
          @Override
          public void start() {
             ToastUtil.shortShow("我是开始");

          }

          @Override
          public void stop() {
              ToastUtil.shortShow("我是结束");

          }
    });
```
`getBitmaps()`返回的是一个bitmap集合，涉及相关代码如下:
```
    private List<Bitmap> getBitmaps(){
        List<Bitmap>list=new ArrayList<>();
        Drawable drawable=ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_launcher);
        Bitmap bitmap= drawableToBitmap(drawable);
        list.add(bitmap);
        list.add(bitmap);
        list.add(bitmap);
        list.add(bitmap);
        return list;
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
```
#### 三.销毁
在使用结束之后,界面销毁或程序退出的时候，为了增加代码的健壮性，你最好做下销毁动作,类似如下:
```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mEmotionView!=null){
            mEmotionView.resetRain();
            LogUtil.i("=====表情雨重置========");
        }
    }
```

