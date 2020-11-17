## SyDialogHelper使用说明

### 概述
SyDialogHelper 是对 SyDialogFragment 的一个再封装，它的出现是为了更加快捷的创建dialog。
进一步了解SyDialogFragment，可查看[SyDialogFragment使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/SyDialogFragment使用说明.md)

### 使用说明
#### 一. Dialog整体风格设置
我们在选哟使用`SyDialogHelper`时，一般是用于对整个`app`而言设置一个通用风格的弹窗。直接调用`SyDialogHelper`类相关方法的时候，可以快速弹出一个`Dialog`窗口宽度为
屏幕宽度0.8d，高度为屏幕高度0.28d的`Dialog`,且确认按钮文字颜色为`#68c81c`，取消按钮文字颜色为`#68c81c`。若你在自己的项目中要对这个通用`Dialog`做微调，你可以在自己的
`Application`中做类似如下设置:
```
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //其他代码省略
        //......

        //SyDialogHelper帮助类全局dialog属性设置
        SyDialogHelper.setDialogSize(0.5d,0.4d);//设置dialog尺寸 
        SyDialogHelper.setCancelTextColor(R.color.blue);  //取消按钮颜色
        SyDialogHelper.setConfirmTextColor(R.color.blue); //确定按钮颜色

    }

}
```

#### 二. 其他方法说明
##### 2.1显示含一个按钮的提示dialog，按钮文字默认为"确定"
```
/**显示一个按钮的提示dialog(默认按钮文字为"确定")**/
showDialogOneBtn(String tipMsg,Context context,View.OnClickListener listener)
```
##### 2.2显示含一个按钮的提示dialog，按钮文字需要定义
```
/**显示一个按钮的提示dialog(自己写按钮文字)**/
showDialogOneBtn(String tipMsg, String btnText, Context context, View.OnClickListener listener)
```
##### 2.3显示含两个按钮的提示dialog，按钮文字固定是"确定"和"取消",只需要写确定功能
```
/**显示两个按钮的提示dialog(确定，取消按钮的文字固定，只需自己写确定功能)**/
showDialogTwoBtn(String tipMsg,Context context,View.OnClickListener listener)
```
##### 2.4显示含两个按钮的提示dialog，按钮文字自定义,只需要写确定功能
```
/**显示两个按钮的提示dialog(确定，取消按钮文字自定义，点击取消按钮dialog消失)**/
showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener listener)
```
##### 2.5显示含两个按钮的提示dialog，按钮文字自定义,确定取消的功能也需要自定义
```
/**显示两个按钮的提示dialog(确定，取消按钮文字和功能自定义)**/
showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener cancelListener,View.OnClickListener confirmListener)
```
#### 三. 使用范例
以显示含一个按钮的提示dialog，按钮文字默认为"确定",只需实现确定功能为例，在使用的时候，你可以类似这样：
```
                SyDialogHelper.showDialogOneBtn("要关闭码?", MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.shortShow("dajillllppppp");
                    }
                });
```

