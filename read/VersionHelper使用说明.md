## VersionHelper使用说明

### 概述
VersionHelper是用于判断app版本的工具类。

### 使用说明
#### 一. 对app版本格式要求
VersionHelper用于判断app版本，但是对版本格式有要求。版本格式需满足以下要求：
1. 版本字符串是三个数字，中间以“.”隔开
2. 数字不能以0开头，必须是整数
则格式类似下面这样：
```
    12.5.6
```
#### 二. 判断版本高低的方法
判断当前版本是否低于服务器版本，你可以用下面的方法：
```
    /**
     * 判断版本，版本号为 1.2.3样式，返回ture时表示当前版本过低，需要更新
     *
     * @param loadVersion    官网app版本号
     * @param currentVersion 当前app版本号
     * @return
     */
    public static boolean isLowerVersion(String loadVersion, String currentVersion) 
```
#### 三. 使用实例
在项目中使用，你可以类似下面这样：
```
        //获取当前app版本号,如:1.0.0
        String currentVersionName= AppUtil.getVersionName()

        //逻辑判断(versionName为服务器获取版本)
        if (VersionHelper.isLowerVersion(versionName, currentVersionName)) {//更新app
            //更新app的处理(弹出是否更新的dialog)
            //......
        } else {//不更新
            //不更新app(进入app)
            //......
        }
```
