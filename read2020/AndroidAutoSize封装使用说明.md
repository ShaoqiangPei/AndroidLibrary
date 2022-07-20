## AndroidAutoSize封装使用说明
### 概述
本功能是对[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)封装实现屏幕适配的。
目的是方便开发者在项目实现过程中能快捷，便利的实现屏幕适配。

### 一.简介
本页面适配的功能涉及到以下几个类： 
- **AutoAdapterHelper:** 屏幕适配帮助类
- **DefaultSuperAutoSizeActivity：**此类继承我们自己的`BaseActivity`，主要处理屏幕适配中出现不生效问题
- **DefaultApplyAutoActivity：** 此类继承`DefaultSuperAutoSizeActivity`，我们自己创建的`Activity`需要采用此适配屏幕框架的界面统一继承`DefaultApplyAutoActivity`
- **DefaultApplyAutoFragment:** 此类继承我们项目的`Fragment`基类`BaseFragment`,当我们自建的`Fragment`要做屏幕适配，需要继承此类。
- **DefaultCancelAutoActivity:** 此类继承我们项目的`Activity`基类`BaseActivity`,当我们自建的`Activity`取消屏幕适配时，需要继承此类。
- **DefaultCancelAutoFragment:** 此类继承我们项目的`Fragment`基类`BaseFragment`,当我们自建的`Fragment`取消屏幕适配时，需要继承此类。

### 二.具体使用
#### 2.1 拷贝文件
- 将`DefaultSuperAutoSizeActivity`类拷贝到自己的项目中，改名为`SuperAutoSizeActivity`,并将其继承改为继承你项目的`Activity`基类(如`BaseActivity`)
- 将`DefaultApplyAutoActivity`类拷贝到自己的项目中，改名为`ApplyAutoActivity`,并将其继承的父类`DefaultSuperAutoSizeActivity`改名为`SuperAutoSizeActivity`
- 将`DefaultApplyAutoFragment`类拷贝到自己的项目中，改名为`ApplyAutoFragment`,并将其继承改为继承你项目的`Fragment`基类(如`BaseFragment`)
- 将`DefaultCancelAutoActivity`类拷贝到自己的项目中，改名为`CancelAutoActivity`,并将其继承改为继承你项目的`Activity`基类(如`BaseActivity`)
- 将`DefaultCancelAutoFragment`类拷贝到自己的项目中，改名为`CancelAutoFragment`,并将其继承改为继承你项目的`Fragment`基类(如`BaseFragment`)
#### 2.2 在自定义 Application 中做屏幕适配相关初始化设置
在你项目的自定义`Application`中做如下设置：
```
public class AppContext extends Application {

    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //屏幕适配AutoSize框架初始化
        new AutoAdapterHelper().init(this) //防止方法数65535
                .initForFragment(true); //适配fragment

    }

}
```
#### 2.3 在 AndroidMainfast.xml 中做ui设计图尺寸设置
假设`UI`出图的设计标准对标屏幕尺寸为: `宽x高= 1080(px) x 1920(px)`
则利用公式
$$dp=ScreenUtil.px2dp(px, context)$$ 
算出 `宽x高` 对应`dp`值为：`360x640`
接着在你项目的`MainAfast.xml`中添加设计屏幕尺寸信息，类似如下：
```
<manifest>
    <application>            
        <meta-data
            android:name="design_width_in_dp"
            android:value="360"/>  //以你设计图宽度dp值为准
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>  //以你设计图高度dp值为准          
     </application>           
</manifest>
```
**需要注意的是，`design_width_in_dp`和`design_height_in_dp`填的是你设计图上宽高px转成dp后的尺寸**
#### 2.4  Activity屏幕适配
`SuperAutoSizeActivity`继承你项目中的`BaseActivity`，然后你新建的`Activity`(如`TestActivity`)实现屏幕适配，可以像下面这样：
```
//实现屏幕适配
public class TestActivity extends ApplyAutoActivity{

}
```
若要自定义适配标准和尺寸,可以像下面这样:
```
//实现屏幕适配
public class TestActivity extends ApplyAutoActivity{

    @Override
    public boolean isBaseOnWidth() {
        //默认ture，表示以最小宽度适配，false表示以最小高度适配
        return false;
    }

    @Override
    public float getSizeInDp() {
        //表示此界面以最小高度 667dp (如果设计图是px的话要转换成dp)适配屏幕
        return 667;
    }
}
```
#### 2.5  Activity屏幕取消适配
`CancelAutoActivity`继承你项目中的`BaseActivity`，然后你新建的`Activity`(如`TestActivity`)取消屏幕适配，可以像下面这样：
```
//取消屏幕适配
public class TestActivity extends CancelAutoActivity{

}
```
#### 2.6  Fragment屏幕适配
`ApplyAutoFragment`继承你项目中的`BaseFragment`，然后你新建的`Fragment`(如`TestFragment`)实现屏幕适配，可以像下面这样：
```
//实现屏幕适配
public class TestFragment extends ApplyAutoFragment{

}
```
若要自定义适配标准和尺寸,可以像下面这样:
```
//实现屏幕适配
public class TestFragment extends ApplyAutoFragment{

    @Override
    public boolean isBaseOnWidth() {
        //默认ture，表示以最小宽度适配，false表示以最小高度适配
        return false;
    }

    @Override
    public float getSizeInDp() {
        //表示此界面以最小高度 667dp (如果设计图是px的话要转换成dp)适配屏幕
        return 667;
    }
}
```
#### 2.7  Fragment屏幕取消适配
`CancelAutoFragment`继承你项目中的`BaseFragment`，然后你新建的`Fragment`(如`TestFragment`)取消屏幕适配，可以像下面这样：
```
//取消屏幕适配
public class TestFragment extends CancelAutoFragment{

}
```
### 三. 屏幕适配注意事项
- `SuperAutoSizeActivity` 和 `CancelAutoActivity` 要继承我们项目自己的 `Activity` 基类 `BaseActivity`。
- `ApplyAutoFragment` 和 `CancelAutoFragment` 要继承我们项目自己的 `Fragment` 基类 `BaseFragment`。
- 在`MainAfast.xml`中设计宽高要填设计图px转化成dp的值，在自定义`Activity/Fragment`中重设的`SizeInDp`是需求给的是px值的话要转成dp值，
在界面对应的布局`xml`文件中，`ui`给出的是px值的话也要转成dp值。

