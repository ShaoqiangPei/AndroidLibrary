## TitleBar2使用说明

### 概述
`TitleBar2`是一个自定义标题栏类，可以说是`TitleBar`的升级版，之所以创建这个类，是因为`TitleBar`在使用的过程中，当涉及到突变加载时，效果不是很好，
主要是`TitleBar`的子控件`ImageTextView`对图片的显示出现拉伸变形等情况，导致图片加载严重失真，于是便产生了`TitleBar2`,建议大家在使用自定义标题栏的时候，
优先使用`TitleBar2`，因为`TitleBar`已经废弃。

### 使用说明
#### 一.在xml文件中引入TitleBar2控件
在你的xml文件中，引入`TitleBar2`控件，你可以类似下面这样：
```
    <com.android.commonlibrary.widget.TitleBar2
        android:id="@+id/title_bar2"
        android:layout_width="0dp"
        android:layout_height="50dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
```
#### 二.在MainActivity中初始化TitleBar2及获取其子控件对象
初始化`TitleBar2`如下：
```
    //声明控件
    private TitleBar2 mTitleBar2;

    //初始化
    mTitleBar2=findViewById(R.id.title_bar2);
```
获取`TitleBar2`子控件如下：
```
        //获取控件对象
        ImageView imvLeft=mTitleBar2.getImvLeft();//左侧返回键图片对象
        TextView tvLeft=mTitleBar2.getTvLeft();//左侧返回键文字对象
        TextView tvTitle=mTitleBar2.getTvTitle();//标题栏中间文字对象
        TextView tvRight=mTitleBar2.getTvRight();//右侧返回键文字对象
        ImageView imvRight=mTitleBar2.getImvRight();//右侧返回键图片对象
```
#### 三.TitleBar2显示样式
默认情况下，`TitleBar2`所有子控件均不显示，需要啥样式自己显示出来。
例如我只要显示中间标题的样式，我可以这样(为方便看效果，标题栏背景使用红色)：
```
mTitleBar2.getTvTitle().setVisibility(View.VISIBLE);
```
显示效果如下
![image.png](https://upload-images.jianshu.io/upload_images/6127340-107cee400d49fb88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
下面以左返回键为例(右返回键雷同，此处不表)，显示左返回键图标+标题：
```
        mTitleBar2.getImvLeft().setVisibility(View.VISIBLE);
        mTitleBar2.getTvTitle().setVisibility(View.VISIBLE);
        //显示左侧图标
        mTitleBar2.getImvLeft().setImageResource(R.mipmap.ic_back_left);
```
效果如下
![image.png](https://upload-images.jianshu.io/upload_images/6127340-4b91054b8b5f75f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
显示左返回键文字+标题
```
        mTitleBar2.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar2.getTvTitle().setVisibility(View.VISIBLE);
        //显示左返回键文字
        mTitleBar2.getTvLeft().setText("左返");//设置文字
        mTitleBar2.setTextSize(mTitleBar2.getTvLeft(),5);//设置文字大小
        mTitleBar2.setTextColor(mTitleBar2.getTvLeft(), R.color.blue);//设置文字颜色
        //显示中间title
        mTitleBar2.getTvTitle().setText("标题栏");//设置文字
        mTitleBar2.setTextSize(mTitleBar2.getTvTitle(),5);//设置文字大小
        mTitleBar2.setTextColor(mTitleBar2.getTvTitle(), R.color.green);//设置文字颜色
```
显示如下：
![image.png](https://upload-images.jianshu.io/upload_images/6127340-5eea8fe388f360ed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
显示左返图标+左返文字+标题
```
        //显示控件
        mTitleBar2.getImvLeft().setVisibility(View.VISIBLE);
        mTitleBar2.getTvLeft().setVisibility(View.VISIBLE);
        mTitleBar2.getTvTitle().setVisibility(View.VISIBLE);
        //显示左侧图标
        mTitleBar2.getImvLeft().setImageResource(R.mipmap.ic_back_left);
        //显示左返回键文字
        mTitleBar2.getTvLeft().setText("左返");//设置文字
        mTitleBar2.setTextSize(mTitleBar2.getTvLeft(),5);//设置文字大小
        mTitleBar2.setTextColor(mTitleBar2.getTvLeft(), R.color.blue);//设置文字颜色
        //显示中间title
        mTitleBar2.getTvTitle().setText("标题栏");//设置文字
        mTitleBar2.setTextSize(mTitleBar2.getTvTitle(),5);//设置文字大小
        mTitleBar2.setTextColor(mTitleBar2.getTvTitle(), R.color.green);//设置文字颜色
```
效果如下
![image.png](https://upload-images.jianshu.io/upload_images/6127340-88332803fa26b15a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 四.TitleBar2设置图标
以设置左侧返回键图标为例，你可以这样设置
```
        //显示左侧图标和右侧图标
        mTitleBar2.getImvLeft().setImageResource(R.mipmap.ic_back_left);
```
#### 五.TitleBar2设置文字
以标题文字的设置为例,你可以这样：
```
        //显示中间title
        mTitleBar2.getTvTitle().setText("标题栏");//设置文字
        mTitleBar2.setTextSize(mTitleBar2.getTvTitle(),5);//设置文字大小
        mTitleBar2.setTextColor(mTitleBar2.getTvTitle(), R.color.green);//设置文字颜色
```
#### 六 TitleBar2设置子控件的padding和margin
```
        //设置控件padding
        mTitleBar2.setPaddings(mTitleBar2.getImvLeft(),2,2,2,2);
        //设置控件margin
        mTitleBar2.setMargins(mTitleBar2.getImvLeft(),2,2,2,2);
```
#### 七 TitleBar2设置点击事件
```
        //左侧返回键点击事件
        mTitleBar2.setOnClickLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("====左侧被点击了====");
                LogUtil.i("====左侧被点击了====");
            }
        });

        //中间点击事件
        mTitleBar2.setOnClickCenter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("====我是中间点击事件====");
                LogUtil.i("====我是中间点击事件====");
            }
        });

        //右侧点击事件
        mTitleBar2.setOnClickRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("====点击右侧干啥====");
                LogUtil.i("====点击右侧干啥====");
            }
        });
```
#### 八.更详细内容参考
更多使用详情，请参考[自定义标题栏TitleBar2](https://www.jianshu.com/p/e1478123b31a)



