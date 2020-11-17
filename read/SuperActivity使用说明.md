## SuperActivity使用说明

### 说明
SuperActivity 作为所有Activity的超类，具备mvp模式,提供系列的状态栏设置,控件初始化,非空判断,常用界面跳转传值以及小部分系统级dialog的创建.
供activity继承，提高activity的创建及编写效率

### 使用介绍
#### 一.使用须知
作为`Activity`的超类，我们并不会在项目中去直接继承此类建自己的`Activity`，而是通常继承该类的子类，即`AppActivity`来快速创建自己的`Activity`.
但是为了更加流畅的使用`AppActivity`这个基类，我们有必要对其父类，也就是`SuperActivity`有一个全面的认知。
#### 二. 隐藏标题栏
在app开发过程中，我们经常会隐藏标题栏.SuperActivity也提供了相关方法.
如果你要将整个app都设为标题栏隐藏状态，那么你可以直接在 Mainfast.xml 中设置整个app的 theme,在 mainfast.xml 中将 app 的 theme 设置成 Theme.Design.NoActionBar 即可,就像下面这样：
```
<application
        //以上代码省略
        //......
        android:theme="@style/Theme.Design.NoActionBar">
        
        //以下代码省略
        //......

</application>
```
如果你只需要隐藏某个activity(如TestActivity),那么你就不要改 mainfast.xml 中的 theme,只需要在 TestActivity 加载 xml 文件之前设置 AppActivity 的 isNoTitle=true 即可,类似下面这样：
```
public class TestActivity extends AppActivity {

    @Override
    public int getContentViewId() {
        super.isNoTitle=true;//隐藏标题栏
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
#### 三. 控件初始化
在 TestActivity 继承 AppActivity 以后,TestActivity界面的控件可以有多种初始化方式
##### 1.原始方式初始化控件
TestActivity 作为一个activity,其界面中的控件具备最原始的初始化方式,以 TestActivity 界面的 TextView 为例,你可以这样初始化：
```
public class TestActivity extends AppActivity {

    private TextView mTvTest;

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;//activity_test为TestActivity对应的xml文件
    }

    @Override
    public void initData() {
       mTvTest=findViewById(R.id.textView);
    }

    @Override
    public void setListener() {

    }
}
```
##### 2.利用 AppHelper 类初始化控件
AppHelper 作为一个 activity辅助类，其中有一个帮助初始化控件的方法.初始化如上 TestActivity 中的 mTvTest 时，你可以这样：
```
//初始化mTvTest,其中 mContext 为 TestActivity 实例
mTvTest=AppHelper.getInstance().getView(mContext,R.id.textView);
```
##### 3.利用父类 AppActivity 中的方法初始化控件
在 TestActivity 类中初始化 mTvTest，你还可以这样操作：
```
mTvTest=getView(R.id.textView);
```
##### 4.利用 butterknife 初始化控件
AppActivity 中已经集成了 butterknife,但是作为一个库引用的话，你仍需在自己的项目中引用 butterknife 库,如你在自己项目的 app_module下做 butterknife 库的引用：
```
android {
  ...
  // Butterknife requires Java 8.
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}

dependencies {
  implementation 'com.jakewharton:butterknife:10.1.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'
}
```
然后在你的 TestActivity 类中就可以进行愉快的初始化了：
```
public class TestActivity extends AppActivity {
    
    @BindView(R.id.textView)
    TextView mTvTest;

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;//activity_test为TestActivity对应的xml文件
    }

    @Override
    public void initData() {
       mTvTest=findViewById(R.id.textView);
    }

    @Override
    public void setListener() {

    }
}
```
#### 五. SupperActivity中方法简介
##### 1. 控件值获取,非空判断、取值
更多相关方法请查阅 AppHelper 类
```
/**获取控件值**/
public String getTextOfView(TextView textView)

/**获取非空字符串**/
public String getNotEmptyString(String str)
```
##### 2. 吐司
```
/**长吐司**/
public void showToast(String msg)

/**短吐司**/
public void showShortToast(String msg)
```
##### 3. 控件初始化
```
/**用于初始化控件的**/
protected <T> T getView(int rId)
```
##### 4. activity界面跳转传值
更多相关方法请查阅 IntentHelper 类
```
/**无参界面跳转**/
public void startAct(Class<?> cls)

/**含一个参数的界面跳转**/
public void startParameterAct(Class<?> cls, String tag, Object parameter)

/**接收上一个界面传过来的int**/
public int getIntParameter(String tag)

/**接收上一个界面传过来的String**/
public String getStringParameter(String tag)

/**接收上一个界面传过来的boolean**/
public boolean getBooleanParameter(String tag)

/**接收上一个界面传过来的Bundle**/
public Bundle getBundleParameter(String tag)

/**接收上一个界面传过来的对象，对象需要实现Serializable**/
public Serializable getSerializableObject(String tag)

/**接收上一个界面传过来的对象，对象需要实现Parcelable**/
public Parcelable getParcelableObject(String tag)

/**传一个int集合的界面跳转**/
public void startIntegerListAct(Class<?> cls, String tag, List<Integer> list)

/**接收上一个界面传过来的int集合**/
public List<Integer> getIntegerList(String tag)

/**传一个String集合的界面跳转**/
public void startStringListAct(Class<?> cls, String tag, List<String> list)

/**接收上一个界面传过来的String集合**/
public List<String> getStringList(String tag)

/**传一个object集合的界面跳转,集合中的object需要实现Parcelable接口**/
public void startParcelableListAct(Class<?> cls, String tag, List<? extends Parcelable> list)

/**接收上一个界面传过来的object集合,集合中的object需要实现Parcelable接口**/
public List<? extends Parcelable> getParcelableList(String tag)

/***
 * 带List<Serializable>list的界面跳转
 *
 * @param cls
 * @param bundle  若有携带有信息的bundle需要传,则此处传该bundle对象
 *                若没有bundle需要传,则此处传null就行
 * @param tag
 * @param list
 */
void startSerializableListAct(Class<?> cls, Bundle bundle, String tag, List<? extends Serializable> list);

/**用intent接收上一个界面传过来的list<Serializable>list**/
List<? extends Serializable> getSerializableList(String tag);
```
##### 5. 系统dialog方法
```
/** 含有标题和内容的对话框 **/
protected AlertDialog showAlertDialog(String title, String message)

/** 含有标题、内容、两个按钮的对话框 **/
protected AlertDialog showAlertDialog(String title, String message, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener)
                                          
/** 含有标题、内容、图标、两个按钮的对话框 **/
protected AlertDialog showAlertDialog(String title, String message, int icon, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener)       
                                          
/** 进度条 */
protected ProgressDialog getProgressDialog(String title, String message, boolean cancelable)
```
