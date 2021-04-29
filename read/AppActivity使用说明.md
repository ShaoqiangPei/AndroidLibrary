## AppActivity使用说明

### 概述
`AppActivity`作为`Activity`基类,具备以下功能：
- Activity基础能力(`状态栏设置`,`控件初始化`,`非空判断`,`界面跳转传值`,`系统级dialog的创建`)
- 加载Fragment能力
- MVP架构(网络通讯能力)  

你可以继承`AppActivity`快速创建自己的`Activity`.

### 使用说明
#### 一.activity的继承
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
 <application
        //以下代码省略
        //......
        >
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name=".TestActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|touchscreen"
            android:screenOrientation="portrait" />
            
    </application>
```
#### 三. 更多使用介绍
[AppActivity基础能力](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivity%E5%9F%BA%E7%A1%80%E8%83%BD%E5%8A%9B%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ——— 处理`Activity`状态栏设置,控件初始化,非空判断,界面跳转传值,系统级dialog的创建  
[AppActivity加载Fragment能力](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivity%E5%8A%A0%E8%BD%BDFragment%E8%83%BD%E5%8A%9B%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ——— 处理`Activity`中含`Fragment`加载时的情况  
[AppActivity网络通讯能力](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivity%E7%BD%91%E7%BB%9C%E9%80%9A%E8%AE%AF%E8%83%BD%E5%8A%9B%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ——— 涉及到`网络通讯时`,迅速接入`mvp模式`
