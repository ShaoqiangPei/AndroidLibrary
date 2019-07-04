## AppActivity使用说明

### 说明
AppActivity 作为所有Activity的基类，提供系列的状态栏设置,控件初始化,非空判断,常用界面跳转传值以及小部分系统级dialog的创建.
供activity继承，提高activity的创建及编写效率

### 使用介绍
#### 一. activity的继承
当你要新建一个activity(假设你新建的为TestActivity类),你只需要让TestActivity继承AppActivity并实现AppActivity的getContentViewId(),
initData()和setListener()这三个方法即可.你新建的TestActivity可以类似这样创建：
```
public class TestActivity extends AppActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;//activity_test为TestActivity对应的xml文件
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
```
#### 二. activity的注册通用配置
activity 在 Mainfast.xml 中注册的时候，通常会添加一些常用的配置属性.例如你的 TestActivity 在 Mainfast.xml 中注册的时候，可以这样配置：
```
        <activity
            android:name=".TestActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|touchscreen"
            android:screenOrientation="portrait" />
```
#### 三. 隐藏标题栏





