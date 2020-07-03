## AppActivity使用说明

### 概述
`AppActivity`作为`Activity`基类,具备`mvp架构`功能，其继承自`SuperActivity`。你可以继承`AppActivity`快速创建自己的`Activity`.

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
若涉及到mvp架构(一般涉及通讯时会用到)，那么你还要重写一个`getPresenter()`方法，类似如下：
```
public class TestActivity extends AppActivity implements MainContract.View {

    @Override
    public int getContentViewId() {
        return R.layout.activity_temp;
    }

     
    @Override
    public PrePresenter getPresenter() { 
        return new MainPresenter(mContext,this);
    }

    @Override
    public void initData() {
       
    }


    @Override
    public void setListener() {
       
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    //其他方法省略
    //......
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
#### 三. 更多
更多使用方法，请参考[SuperActivity使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/SuperActivity%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)
