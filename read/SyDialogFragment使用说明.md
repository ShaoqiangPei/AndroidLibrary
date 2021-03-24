## SyDialogFragment使用说明

### 概述
SyDialogFragment继承自AppDialogFragment，是一款通用dialog，致力于界面简洁，使用方便。
SyDialogFragment是AppDialogFragment子类，拥有AppDialogFragment全部特性。
若要了解AppDialogFragment，请参考[AppDialogFragment使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppDialogFragment使用说明.md)

### SyDialogFragment效果图  
SyDialogFragment使用过程中，一般效果图如下  
![1.png](https://upload-images.jianshu.io/upload_images/6127340-0cd0f2982556af25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  


### SyDialogFragment使用说明 
#### 一. 使用说明
当你要使用 SyDialogFragment 作为弹出框使用的时候，你可以像下面这样调用
```
        SyDialogFragment syDialogFragment=new SyDialogFragment();
        syDialogFragment.setBackGroundColor(R.color.white)//默认背景白色，在SyDialogFragment中设置
                .setDialogSize(0.8d,0.28d) //设置dialog弹框大小,不设置的话默认宽度比例为0.8d,高度比例为0.28d
                .setTitle(true)//默认显示标题栏
                .setTitleText("申请")//默认显示"提示"，在xml中设置
                .setTitleTextColor(R.color.blue)//默认黑色，在xml中设置
                .setTitleTextSize(18f)//默认14sp,在xml中设置
                .setMesssageText("你确定要申请权限吗?")//默认显示"提示"，在xml中设置
                .setMesssageTextColor(R.color.black)//默认#666666，在xml中设置
                .setMesssageTextSize(15f)//默认12sp，在xml中设置
                .setCancelTextColor(R.color.green)//默认#68c81c，在xml中设置
                .setCancelTextSize(16f)//默认14sp,在xml中设置
                .setCancelBtn("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//默认文字为“取消”，在xml中设置
                        ToastUtil.shortShow("取消");
                        syDialogFragment.dismiss();
                    }
                })
                .setConfirmTextColor(R.color.red)//默认#68c81c，在xml中设置
                .setConfirmTextSize(16f)//默认14sp,在xml中设置
                .setConfirmBtn("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//默认文字为“确定”，在xml中设置
                        ToastUtil.shortShow("确认");
                        syDialogFragment.dismiss();
                    }
                })
                .setUIShadow(false)//默认不弹出背景遮罩
                .setCancel(false)//默认true
                .setCancelOnTouchOutside(false)//默认true
                .showDialog(getSupportFragmentManager());
```
#### 二. SyDialogFragment使用时注意事项
SyDialogFragment作为AppDialogFragment子类，具备AppDialogFragment的所有特性，其支持链式调用，当你的SyDialogFragment调用的时候既涉及到SyDialogFragment
的方法，又涉及到AppDialogFragment中的方法的时候，请先调用SyDialogFragment类中的方法，再调用AppDialogFragment。下面举例说明。
例如:syDialogFragment中有setMesssageText方法，而setCancelOnTouchOutside是AppDialogFragment的方法，若你的SyDialogFragment再显示的时候需要调用到
这两个方法，那么正确的调用顺序是：
```
        new SyDialogFragment()
                .setMesssageText("大学")//先调用SyDialogFragment自己的方法
                .setCancelOnTouchOutside(false)//再调用父类AppDialogFragment中的方法
                .showDialog(getSupportFragmentManager());
```
若先调用父类AppDialogFragment中的方法，再调用SyDialogFragment自己的方法则会报错。
#### 三. SyDialogFragment刷新界面数据
##### 3.1 只刷新提示信息
当`SyDialogFragment`已经创建了，但是我们要刷新其界面提示语的时候(以调用只显示一个按钮的方法为例),可以像下面这样:
```
        //在第一次调用时显示提示语为"大学"
        SyDialogHelper.showDialogOneBtn("大学",mContext);
```
在`SyDialogFragment`已经显示的情况下，我们要将提示语刷新为"小学",则在需要刷新的地方直接像下面这样调用即可:
```     //在需要将提示语刷新为"小学"的地方调用
        SyDialogHelper.showDialogOneBtn("小学",mContext);
```
##### 3.2 需要刷新已经显示的更多参数
对于一个已经弹出的`SyDialogFragment`时,若需要刷新更多信息，则需要`SyDialogFragment`实例对象及`resetInitConfig()`的协助。  
以`SyDialogHelper.showDialogOneBtn`方法为例。本来此方法是显示时有一个按钮,但是我们在其显示的时候希望其显示时没有按钮，则可以像下面这样:
```
        //初始设置
        SyDialogHelper.showDialogOneBtn("大学",mContext);
        //隐藏按钮
        SyDialogHelper.getSyDialogFragment(mContext).setCancelBtn(false);
        //刷新后最终的显示
        SyDialogHelper.getSyDialogFragment(mContext).resetInitConfig();
```
在以上`SyDialogFragment`已经显示的情况下，我想刷新该`dialog`,使得提示语变更为`xiao学`,然后显示出一个按钮，则可以在需要的地方接着像下面这样调用:
```     
        //刷新一个已经显示的dialog的提示语，并显示出按钮 
        SyDialogHelper.showDialogOneBtn("xiao学",mContext);
```

