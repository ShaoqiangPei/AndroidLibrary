## DefaultDialogFragment使用说明

### 前言
DefaultDialogFragment 用于创建默认dialog,其继承自AppCompatDialogFragment。DefaultDialogFragment在其基础上做以扩展，其创建使用方式分为两种：
- 使用系统定义的 title-message-button 布局的模式
- 自定义布局模式

### 调用效果图
DefaultDialogFragment系统布局使用方式效果图  
![1.gif](https://upload-images.jianshu.io/upload_images/6127340-82dba1b6a1cbd2b7.gif?imageMogr2/auto-orient/strip)  
DefaultDialogFragment自定义布局使用方式效果图  
![2.gif](https://upload-images.jianshu.io/upload_images/6127340-698c10ab59962f14.gif?imageMogr2/auto-orient/strip)

### 使用说明
#### 一. DefaultDialogFragment类中方法介绍
下面对DefaultDialogFragment主要使用的方法做些介绍
在自定义布局模式的使用的时候会牵涉到下面两个方法
```
    /***
     * 设置自定义布局
     *
     * 此方法设置后,title,message均在布局中实现,则不调用本类中的
     * setTitle，setMessage方法
     *
     * @return
     */
    public DefaultDialogFragment setCustomerLayoutId(int layoutId,Context context)

    /**根据id获取自定义布局中的控件**/
    public <T>View getView(int rId)
```
设置标题(此方法只有在调用系统模式时才使用)
```
setTitle(String title) 
```
设置信息(此方法只有在调用系统模式时才使用)
```
setMessage(String message)
```
设置点击返回键是否关闭dialog
```
setCancel(boolean canDismiss)
```
设置点击屏幕外面是否关闭dialog
```
setCancelOnTouchOutside(boolean canDismiss) 
```
设置dialog背景(此方法只有在调用系统模式时才使用)
```
setBackGround(int rid)
```
设置Positive点击事件
```
setPositiveListener(String positiveText, DialogInterface.OnClickListener onClickListener)
```
设置Negative点击事件
```
setNegativeListener(String negativeText, DialogInterface.OnClickListener onClickListener)
```
显示dialog
```
    /***
     * 显示dialog，需要传 fragmentManager=getSupportFragmentManager()
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager)
```
#### 二. DefaultDialogFragment两种使用方式的调用示例
下面给出 使用系统定义的 title-message-button 布局模式的使用范例
```
        new DefaultDialogFragment()
                //设置标题
                .setTitle("你是谁")
                //设置内容
                .setMessage("who are you")
                //禁用返回键
                .setCancel(false)
                //禁止屏幕外部点击消失
                .setCancelOnTouchOutside(false)
                //设置背景色
                .setBackGround(R.color.colorPrimaryDark)
                //取消事件
                .setNegativeListener("不好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.shortShow("heihei");
                    }
                })
                //确认事件
                .setPositiveListener("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.shortShow("haha");
                    }
                })
                //显示dialog
                .showDialog(getSupportFragmentManager());
```
下面给出自定义布局模式使用的范例
```
        DefaultDialogFragment defaultDialogFragment=new DefaultDialogFragment();
        //设置自定义布局
        defaultDialogFragment.setCustomerLayoutId(R.layout.customer_layout,MainActivity.this)
                //禁用返回键
                .setCancel(false)
                //禁止屏幕外部点击消失
                .setCancelOnTouchOutside(false)
                //取消事件(可不写,然后在自定义布局中写点击事件)
                .setNegativeListener("不好a", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.shortShow("heihei");
                    }
                })
                //确认事件(可不写,然后在自定义布局中写点击事件)
                .setPositiveListener("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.shortShow("haha");
                    }
                })
                //显示dialog
                .showDialog(getSupportFragmentManager());
        //获取自定义布局中的控件
        TextView tv= (TextView) defaultDialogFragment.getView(R.id.tv_text);
        //给自定义布局中的控件设置值
        tv.setText("大神");

```
这里需要注意的是，在自定义布局使用的时候，DefaultDialogFragment中的setTilte和setMesssage方法不能使用，一般是将这两个信息自己在在定义布局中写好，然后通过自定义布局去设置或获取。然后setNegativeListener(取消事件)和setPositiveListener(确认事件)可在这里使用，其实也可以自己在自定义布局中去实现。当在自定义布局中实现的时候，这里就不必调用了。
