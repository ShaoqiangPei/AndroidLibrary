## AppDialog使用说明

### 概述
`AppDialog`是继承`Dialog`实现的一个`自定义dialog`的父类。开发者可继承`AppDialog`快速实现一个`自定义dialog`。`AppDialog`具备设置`dialog`尺寸，
自定义`dialog`布局等优势。

### 使用说明
#### 一. 如何快速实现自定义 dialog
为了方便描述，本文将以自定义`dialog`的类`TestDialog`为例进行讲解。
ok，要自定义`dialog`，则要`TestDialog`继承`AppDialog`，然后写一个`TestDialog`的构造方法。跳入`AppDialog`源码中，我们可以看到有以下两个构造方法：
```
    /**使用"R.style.AppDialog"的dialog样式初始化**/
    public AppDialog(Context context,int layoutId)

    /**可传入"theme"的dialog样式初始化**/
    public AppDialog(Context context,int theme,int layoutId)
```
第一个构造方法是采用默认的`R.style.AppDialog` dialog 样式，而第二个构造方法是可以传一个 `int theme`的提示框样式。
其他两个参数`context`是上下文，`layoutId`是自定义`dialog`的`布局id`。
一般我们直接使用` AppDialog(Context context,int layoutId)`来快速实现自定义`dialog`，则自定义提示框`TestDialog`代码如下：

```
public class TestDialog extends AppDialog{
    //其他代码省略
    //......
}
```
接着要写一个`TestDialog`的构造方法，跳入`AppDialog`源码中，我们可以看到有以下两个构造方法：
```
    /**使用"R.style.AppDialog"的dialog样式初始化**/
    public AppDialog(Context context,int layoutId)

    /**可传入"theme"的dialog样式初始化**/
    public AppDialog(Context context,int theme,int layoutId)
```
第一个构造方法是采用默认的`R.style.AppDialog` dialog 样式，而第二个构造方法是可以传一个 `int theme`的提示框样式。
其他两个参数`context`是上下文，`layoutId`是自定义`dialog`的`布局id`。
一般我们直接使用` AppDialog(Context context,int layoutId)`来快速实现自定义`dialog`，则`TestDialog`代码如下编写：
```
public class TestDialog extends AppDialog{

    public TestDialog(Context context) {
        super(context, R.layout.dialog_test);

        //初始化(这里必须调用,不然initView,initData和setListener三个方法不执行)
        initView();
        initData();
        setListener();
    }

    @Override
    protected double[] getWindowSize() {
        return new double[0];
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
```
其中,`R.layout.dialog_test`为自定义提示框的`布局id`。
#### 二. 设置自定义dialog大小
设置`TestDialog`的尺寸主要集中在`double[] getWindowSize()`方法上，此方法接收的是一个`double`数组。其子项为两个，下标为0的表示是`dialog宽度与屏幕宽度比例系数`，下标为1的表示是`dialog高度与屏幕高度比例系数`。
##### 2.1 设置dialog宽高自适应
设置`TestDialog`宽高自适应大小，你可以像下面这样：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        return new double[]{super.WRAP_CONTENT,super.WRAP_CONTENT};
    }

}
```
##### 2.2 设置dialog宽高全屏显示
设置`TestDialog`宽高全屏，你可以像下面这样：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        return new double[]{super.MATCH_PARENT,super.MATCH_PARENT};
    }

}
```
或者像下面这样：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        return new double[]{1.0d,1.0d};
    }

}
```
##### 2.3 设置dialog宽高按屏幕比例大小显示
例如设置`TestDialog`的宽度按屏幕0.8，高度按屏幕0.25显示，你可以像下面这样处理：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.8d,0.25d};
    }

}
```
效果图如下：
##### 2.4 设置dialog 宽/高 最大值
有时我们会有这样的需求(以高度讲解为例)：当`dialog`高度低于某个高度(如屏幕高度的0.5)时显示实际高度，高度超过某个高度(如屏幕高度的0.5)时，则`dialog`显示该高度(如屏幕高度的0.5)，那么在`TestDialog`中设置如下：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        //设置dialog的最大高度不超过屏幕高度的0.5,
        //此时dialog的高度设置必须为super.WRAP_CONTENT,否则设置的最大高度无效
        setMaxScaleHeight(0.5d);
        return new double[]{0.8d,super.WRAP_CONTENT};
    }

}
```
这里需要注意的是，要设置`TestDialog`显示的最大高度，必须设置`TestDialog`的高度为`super.WRAP_CONTENT`,即以上代码中的`return new double[]{0.8d,super.WRAP_CONTENT};
    }`第二个参数必须为`super.WRAP_CONTENT`。然后，设置最大高度的代码，必须写在` return new double[]{0.8d,super.WRAP_CONTENT};`之前，位置类似如下：
```
    @Override
    protected double[] getWindowSize() {
        //此处设置dialog的最大高度代码
        //......
        return new double[]{0.8d,super.WRAP_CONTENT};
    }
```
同理，在设置`TestDialog`的最大宽度时，我们可以类似下面这样：
```
public class TestDialog extends AppDialog{
    
    //其他代码省略
    //......

    @Override
    protected double[] getWindowSize() {
        //设置dialog的最大宽度不超过屏幕高度的0.5,
        //此时dialog的宽度设置必须为super.WRAP_CONTENT,否则设置的最大高度无效
        setMaxScaleWidth(0.5d);
        return new double[]{super.WRAP_CONTENT,0.25d};
    }

}
```
#### 三.自定义 dialog 在 MainActivity 中使用
经过以上讲解，下面贴出一个宽度为屏幕宽度0.8，高度为屏幕高度0.25的自定义提示框类`TestDialog`代码：
```
/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/6/2
 */
public class TestDialog extends AppDialog{

    private TextView mTv;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;

    public TestDialog(Context context) {
        super(context, R.layout.dialog_test);

        //初始化(这里必须调用,不然initView,initData和setListener三个方法不执行)
        initView();
        initData();
        setListener();
    }

    @Override
    protected double[] getWindowSize() {
        return new double[]{0.8d,0.25d};
    }

    @Override
    protected void initView() {
        mTv= (TextView) getView(R.id.tv);
        mBtn1= (Button) getView(R.id.btn1);
        mBtn2= (Button) getView(R.id.btn2);
        mBtn3= (Button) getView(R.id.btn3);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public void setBtn1(String message,OnClickListener listener){
        setNegativeBtn(mBtn1,message,listener);
    }

    public void setBtn2(String message,OnClickListener listener){
        setNeutralBtn(mBtn2,message,listener);
    }

    public void setBtn3(String message,OnClickListener listener){
        setPositiveBtn(mBtn3,message,listener);
    }

}
```
在`MainActivity`中，你可以像下面这样显示`TestDialog`:
```
    /**弹出dialog**/
    private void showDialog(){
        TestDialog dialog=new TestDialog(TempActivity.this);
        dialog.setCanceledOnTouchOutside(true);//允许外部点击关闭
        dialog.setCancelable(true);//可用返回键
        //按钮1点击事件
        dialog.setBtn1("按钮1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //逻辑处理
                //......
                ToastUtil.shortShow("按钮1");

                //                //关闭dialog
                //                dialog.dismiss();
            }
        });

        //按钮2点击事件
        dialog.setBtn2("按钮2", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //逻辑处理
                //......
                ToastUtil.shortShow("按钮2 ");

                //                //关闭dialog
                //                dialog.dismiss();
            }
        });

        //按钮3点击事件
        dialog.setBtn3("按钮3", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //逻辑处理
                //......
                ToastUtil.shortShow("按钮3 ");

                //                //关闭dialog
                //                dialog.dismiss();
            }
        });
        //弹出dialog
        if (dialog.shouldShow(TempActivity.this)) {
            dialog.show();
        }
    }
```
#### 四. 其他重要方法
`AppDialog`作为一个自定义`Dialog`的父类，其实也继承自`Dialog`,故`Dialog`中一些比较重要的方法也需要关注下，如：
```
setOnCancelListener(@Nullable OnCancelListener listener)
setOnDismissListener(@Nullable OnDismissListener listener)
setOnKeyListener(@Nullable OnKeyListener onKeyListener)
setOnShowListener(@Nullable OnShowListener listener)
```
