## AppFragment使用说明
### 说明
AppFragment 作为所有Fragment的基类，提供系列的跳转Activity方法,跳转Fragment界面的传值. 
供fragment继承，提高fragment的创建及编写效率

### 使用介绍
#### 一. fragment的继承
当你要新建一个fragment(假设你新建的为FragmentB类),你只需要让FragmentB继承AppFragment
并实现AppFragment的getContentViewId(), initData()和setListener()这三个方法即可.你新建的TestActivity可以类似这样创建：
```
public class FragmentB extends AppFragment {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_b;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
```
#### 二.控件初始化
##### 1.原始方式初始化控件
FragmentB作为一个fragment,具备基本的初始化方法,在继承AppFragmnet后依然可以类似下面这样初始化：
```
    //声明控件
    private TextView mTvTestB;
    
    //控件初始化
    mTvTestB=mLayoutView.findViewById(R.id.tv_b);
```
##### 2.利用 AppHelper 类初始化控件
AppHelper中具备一个初始化控件的泛型方法,初始化 FragmentB 中的 mTvTestB 时，你可以这样：
```
    //声明控件
    private TextView mTvTestB;
    
    //控件初始化
    mTvTestB= AppHelper.getInstance().getView(mLayoutView,R.id.tv_b);
```
##### 3.利用父类 AppFragment 中的方法初始化控件
在 FragmentB 类中初始化 mTvTestB，你还可以这样操作：
```
    //声明控件
    private TextView mTvTestB;
    
    //控件初始化
    mTvTestB= getView(R.id.tv_b);
```
##### 4.利用 butterknife 初始化控件
AppFragment 中已经集成了 butterknife,但是作为一个库引用的话，你仍需在自己的项目中引用 butterknife 库,
如你在自己项目的 app_module下做 butterknife 库的引用：
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
然后在你的 FragmentB 类中就可以进行愉快的初始化了：
```
public class FragmentB extends AppFragment {

    //声明控件
    @BindView(R.id.tv_b)
    TextView mTvTestB;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_b;
    }

    @Override
    public void initData() {

        mTvTestB.setText("大神d");
    }

    @Override
    public void setListener() {

    }
}
```
### 三. AppFragment可见与不可见方法介绍
界面可见时调用,isFirstTimeLoad=true时,表示Fragment第一次界面可见
当isFirstTimeLoad=false时表示非第一次界面可见
```
onVisible(boolean isFirstTimeLoad){}
```
fragment界面不可见时调用
```
onInvisible(){}
```
#### 注:以上两个方法只有Fragment结合viewpager使用的时候才生效。
### 四. AppFragment常见方法介绍
#### 1. 控件值获取,非空判断、取值
更多相关方法请查阅 AppHelper 类
```
/**获取控件值**/
public String getTextOfView(TextView textView)

/**获取非空字符串**/
public String getNotEmptyString(String str)
```
#### 2. 吐司
```
/**长吐司**/
public void showToast(String msg)

/**短吐司**/
public void showShortToast(String msg)
```
#### 3. 控件初始化
```
/**用于初始化控件的**/
protected <T> T getView(int rId)
```
#### 4. activity界面跳转传值
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

/**下一个Fragemnt中接收传值**/
protected Bundle getFragBundle()

```
### 五. Fragment向Activity回传值
仔细观察，在APPFragment中，你可以发现以下代码：
```
    /**与Activity交互回调监听**/
    protected OnFragmentListener mOnFragmentListener;
    
    /**设置监听**/
    public void setOnFragmentListener(OnFragmentListener onFragmentListener){
        this.mOnFragmentListener=onFragmentListener;
    }
```
以上代码就是用于来协助Fragment给Activity传值用的,第一步你的 FragmentB 继承 AppFragment,如下：
```
public class FragmentB extends AppFragment {

}
```
然后重写fragment的onAttach(Context context)方法,如下：
```
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mContext instanceof MainActivity){
            setOnFragmentListener((MainActivity)mContext);
        }
    }
```
其中MainActivity 要实现 OnFragmentListener接口。
然后在 FragmentB 中需要给 activity 中传值的时候，这样写：
```
   //给activity传值
   if(mOnFragmentListener!=null){
       mOnFragmentListener.onFragment("FragmentB","牛人");
   }
```
其中 onFragment(String clsNameDetail, Object object)中的两个参数：
clsNameDetail: 字符串,用于告诉activity，此值来自哪个Fragment的回传，我一般传fragment字符串类名
object： object类,最好用可序列化对象传，用于传递Fragment给activity的值

然后对应的MainActivity要实现 OnFragmentListener接口，然后在onFragment中接收fragment返回值，类似如下：
```
//activity实现OnFragmentListener接口
public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    @Override
    public void onFragment(String clsNameDetail, Object object) {
       //根据接收不同fragment回传值做对应处理
       //接收FragmnetB回传值
       if("FragmentB".equals(clsNameDetail)){
           mTvText.setText(object.toString());
       }
    }
}
```



