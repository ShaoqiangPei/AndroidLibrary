## SyDialogHelper使用说明

### 概述
SyDialogHelper 是对 SyDialogFragment 的一个再封装，它的出现是为了更加快捷的创建dialog。
进一步了解SyDialogFragment，可查看[SyDialogFragment使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/SyDialogFragment使用说明.md)

### 使用说明
#### 一. 方法说明
##### 1.1显示含一个按钮的提示dialog，按钮文字默认为"确定"
```
/**显示一个按钮的提示dialog(默认按钮文字为"确定")**/
showDialogOneBtn(String tipMsg,Context context,View.OnClickListener listener)
```
##### 1.2显示含一个按钮的提示dialog，按钮文字需要定义
```
/**显示一个按钮的提示dialog(自己写按钮文字)**/
showDialogOneBtn(String tipMsg, String btnText, Context context, View.OnClickListener listener)
```
##### 1.3显示含两个按钮的提示dialog，按钮文字固定是"确定"和"取消",只需要写确定功能
```
/**显示两个按钮的提示dialog(确定，取消按钮的文字固定，只需自己写确定功能)**/
showDialogTwoBtn(String tipMsg,Context context,View.OnClickListener listener)
```
##### 1.4显示含两个按钮的提示dialog，按钮文字自定义,只需要写确定功能
```
/**显示两个按钮的提示dialog(确定，取消按钮文字自定义，点击取消按钮dialog消失)**/
showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener listener)
```
##### 1.5显示含两个按钮的提示dialog，按钮文字自定义,确定取消的功能也需要自定义
```
/**显示两个按钮的提示dialog(确定，取消按钮文字和功能自定义)**/
showDialogTwoBtn(String tipMsg,String cancelText,String confirmText,Context context,View.OnClickListener cancelListener,View.OnClickListener confirmListener)
```
#### 二. 使用范例
以显示含一个按钮的提示dialog，按钮文字默认为"确定",只需实现确定功能为例，在使用的时候，你可以类似这样：
```
                SyDialogHelper.showDialogOneBtn("要关闭码?", MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.shortShow("dajillllppppp");
                    }
                });
```

