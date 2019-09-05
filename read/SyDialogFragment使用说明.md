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
