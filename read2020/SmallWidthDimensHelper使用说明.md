## SmallWidthDimensHelper使用说明

### 概述
`SmallWidthDimensHelper`采用的是`最小屏幕宽度适配原理`.结合此类的运用，我们可以在`Android`项目的
`res/`文件夹下批量生成类似`values-swxxxdp`的文件夹，其中`xxxdp`表示屏幕的最小宽度。

### 一.概念理解
#### 1.1 最小宽度的理解
之前我们已经讲过屏幕适配的原理是: `px=(实际分辨率/涉及基准分辨率)*dp`，由于无法兼顾横竖两个方向，所以会采取宽，或者高的一个方向来适配。
今天要讲的最小宽度`smallWidth`适配与之前又有所区别。最小宽度是指对手机或平板而言，最短的两条边，不一定是屏幕的宽度。
(例如对平板而言，有的高度其实是小于宽度的，这是所谓的最小宽度其实是平板的高度)
#### 1.2 values文件夹理解
利用`SmallWidthDimensHelper`帮助类，我们生成的`values`文件夹名字类似如下： 
```
values-sw240dp
values-sw360dp
values-sw400dp
values-sw960dp
values-sw1080dp
......
```
其中的`240dp`,`360dp`,`400dp`等表示的就是屏幕的最小宽度。
以我手机屏幕尺寸: 1080x2032,dpi=480,density(屏幕像素比例)=3.0,接着有一个smallwidthDensity(最小宽度)=360.0,这里想告诉大家的是
手机的屏幕最小宽度并不是手机屏幕尺寸，且每个手机的smallwidthDensity(最小宽度)也是固定的。
### 二.SmallWidthDimensHelper屏幕values文件夹生成帮助类的使用
`SmallWidthDimensHelper`类的使用是要在`Android`项目中新建一个`Java_module`，然后在`Java`的`main`方法中执行以下调用： 
```
    public static void main(String[] args) {
        System.out.println("======我是中国人=======");

        SmallWidthDimensHelper helper = new SmallWidthDimensHelper();
        helper.setBase(1080, 1920) //设计图基准宽高
                .setDefaultScale(1.0f) //默认缩放比
                .createSmallWidthDimens(); //最小宽度适配
    }
```
以上代码的`setBase(1080, 1920)`设置的是设计图即`UI`给出的基准屏幕宽高, `setDefaultScale(1.0f)`设置的是默认`values`文件夹中`dp`和实际`dp`的比值  
生成的文件夹列表大致如下：
```
values-sw240dp
values-sw360dp
values-sw400dp
values-sw960dp
values-sw1080dp
......
```
每个`values`文件夹下有一个`dimens.xml`文件,该文件打开类容大致如下：
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
<dimen name="dp_0">0.0dp</dimen>
<dimen name="dp_1">0.29dp</dimen>
<dimen name="dp_2">0.59dp</dimen>
<dimen name="dp_3">0.88dp</dimen>
<dimen name="dp_4">1.18dp</dimen>
<dimen name="dp_5">1.48dp</dimen>
<dimen name="dp_6">1.77dp</dimen>
<dimen name="dp_7">2.07dp</dimen>
<dimen name="dp_8">2.37dp</dimen>
......
</resources>
```
注意，这里生成的值也是`dp`。
这样的话我们就可以在项目中使用`values`文件夹中的`dp`值达到屏幕适配目的了。

