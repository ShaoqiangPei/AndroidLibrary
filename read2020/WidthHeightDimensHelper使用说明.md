## WidthHeightDimensHelper使用说明

### 概述
`WidthHeightDimensHelper`作为一个`android`屏幕适配帮助类，主要用于生成`res/`文件夹下不同`values`文件夹。
使用的是宽高限定符原理。

### 使用
在你`Android`项目下建`java_module`,然后将`WidthHeightDimensHelper`复制到此`module`下，接着在`java`的
`Main`方法中执行以下代码：

```
    public static void main(String[] args) {
        System.out.println("======我是中国人=======");

        WidthHeightDimensHelper helper=new WidthHeightDimensHelper();
        helper.setBaseWidth(1080,1920) //设计图基准宽高
                .setDefaultScale(1.0f) //默认缩放比
                .createWidthDimens(); //宽度基准适配
//                .createHeightDimens(); //高度基准适配

    }
```
以上需要说明的是，`setBaseWidth(1080,1920)`中传入的是设计图的基准屏幕宽高。
`setDefaultScale(1.0f)`设置的是默认布局中`dp`和`px`缩放比例。
然后`createWidthDimens();`表示是以宽度进行生成`values`文件适配，
`createHeightDimens();`表示的是以高度进行生成`values`文件适配。
一般情况下，我们采用以宽度进行生成`values`文件适配，然后在竖直方向辅以滚动，以达到屏幕适配的目的。

最后会在`Android`项目的`res\`文件夹下生成一系列`values`文件夹，部分如下：

```
values
valuse-320x240
valuse-480x320
valuse-800x480
valuse-800x600
...
```
这样我们就生成很多布局对应需要的`dp`和`px`对等关系了。
让我们打开一个`dimens`文件看看(以`values-19200x1200`为例)，该`dimens`文件内容如下：

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
<dimen name="dp_1">1.11px</dimen>
<dimen name="dp_2">2.22px</dimen>
<dimen name="dp_3">3.33px</dimen>
<dimen name="dp_4">4.44px</dimen>
<dimen name="dp_5">5.55px</dimen>
<dimen name="dp_6">6.66px</dimen>
<dimen name="dp_7">7.77px</dimen>
<dimen name="dp_8">8.88px</dimen>
<dimen name="dp_9">10.0px</dimen>
......

</resources>
```
在`Activity`的布局中，我们就可以使用`values`文件夹中`dimen`值来达到屏幕适配的目的了。


