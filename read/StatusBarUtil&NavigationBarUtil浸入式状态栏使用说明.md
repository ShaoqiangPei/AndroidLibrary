## StatusBarUtil&NavigationBarUtil浸入式状态栏使用说明

### 简介
浸入式状态栏的处理分别包含两个类:
- StatusBarUtil  头部状态栏处理类
- NavigationBarUtil 底部按键栏处理类  
主要用于实现界面浸入式状态栏效果。

### 使用说明
#### 一. StatusBarUtil与NavigationBarUtil主要方法简介
##### 1.1 StatusBarUtil方法简介
`StatusBarUtil`类主要用来处理头部状态栏的浸入式效果，为了实现兼容性，其diamagnetic比较多也繁琐，这里主要介绍几个常用方法。
```
//设置状态栏背景透明
StatusBarUtil.immersive(context);
//设置状态栏背景红色
StatusBarUtil.immersive(context, ContextCompat.getColor(context,R.color.red));
//设置状态栏背景半透明红
StatusBarUtil.immersive(context, ContextCompat.getColor(context,R.color.red),0.5f);


注:目前状态栏文字颜色只有两种：白色和黑色
//设置状态栏文字变黑
StatusBarUtil.darkMode(context);
//设置状态栏文字变白
StatusBarUtil.darkMode(context,false);
//设置状态栏文字变黑
StatusBarUtil.darkMode(context,true);

//设置状态栏字体变黑，设置状态栏背景色和状态栏背景色的透明度
//参数介绍：
//activity: 上下文
//color：状态栏背景色
//alpha：状态栏背景色透明度
StatusBarUtil.darkMode(context, ContextCompat.getColor(this,R.color.red),0.5f);
```
为了简介起见，在使用过程中，我们通常只用以下方法：
```
        //设置头部侵入式状态栏
        StatusBarUtil.immersive(this);//设置状态栏透明
        StatusBarUtil.setHeightAndPadding(this, mTvTitle);
        StatusBarUtil.darkMode(this, true);//设置状态栏文字颜色为黑色：只有白色和黑色。true: 设置黑色   false: 设置白色
```
##### 1.2 NavigationBarUtil方法简介
`NavigationBarUtil`主要用于处理底部按键栏的浸入式效果，及处理底部按键栏的显示和隐藏效果。其主要有以下方法：
- 处理底部按键栏的浸入式效果的方法
```
    /**
     * 初始化时调用
     *
     * 注:rootView需要传具体的根view对象。
     *   例如当你界面对应的xml中根布局为ConstraintLayout,
     *   则此处rootView需要传ConstraintLayout实例。
     *
     * @param rootView 界面的根布局view对象
     * @param context 上下文
     */
    public static void initNavigationBar(View rootView,Context context)


    /**
     * 手机底部NavigationBar透明
     * 在actiivty的onWindowFocusChanged()中调用
     *
     * @param context 上下文
     * @param color 底部 颜色,如:Color.TRANSPARENT
     */
    public static void onWindowFocusChanged(Context context,int color)
```
- 处理底部按键栏的显示和隐藏的方法
```
    /**隐藏底部虚拟按键**/
    public static void hideNavigationBar(Context context)

    /**显示虚拟按键**/
    public static void showNavigationBar(Context context)
```
#### 二.浸入式状态栏的实现
#####  2.1  浸入式头部状态栏实现
下面贴出在`MainActivity`中使用示例代码:
```
public class StatusBarActivity extends AppCompatActivity {


    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar);

        mTvTitle=findViewById(R.id.tv);

        //设置头部侵入式状态栏
        StatusBarUtil.immersive(this);//设置状态栏透明
        StatusBarUtil.setHeightAndPadding(this, mTvTitle);
        StatusBarUtil.darkMode(this, true);//设置状态栏文字颜色为黑色

        mTvTitle.setText("设置头部侵入式状态栏");
    }
}
```
#####  2.2  浸入式底部按键栏实现
下面贴出在`MainActivity`中使用示例代码:
```
public class NavigationActivity extends AppCompatActivity {

    private ConstraintLayout mRootView;
    private TextView mTvTitle;

    //第一次进入当前activity的标志
    private boolean isFirstCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar);

        mRootView=findViewById(R.id.root);
        mTvTitle=findViewById(R.id.tv);

        //设置侵入式底部按键栏
        NavigationBarUtil.initNavigationBar(mRootView,NavigationActivity.this);
        mTvTitle.setText("设置侵入式底部按键栏");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!isFirstCreated){
            NavigationBarUtil.onWindowFocusChanged(NavigationActivity.this, Color.TRANSPARENT);
            StatusBarUtil.setHeightAndPadding(this, mTvTitle);
            isFirstCreated=true;
        }
    }
}
```
这里需要说明的是
- `initNavigationBar(mRootView,NavigationActivity.this)`方法中的`mRootView`为当前界面的具体根布局对象，不能笼统的使用抽取的父类(如baseActivity)中的根布局(如mLayoutView)代替使用，因为会出现界面控件全部没有的现象。若当前界面的根布局为`ConstraintLayout`,则此处的`mRootView`为`ConstraintLayout`控件实例。
- `onWindowFocusChanged(NavigationActivity.this, Color.TRANSPARENT)`方法需要在`Activity`的`onWindowFocusChanged`方法中执行，此处还需要结合`StatusBarUtil`类中的`StatusBarUtil.setHeightAndPadding(this, mTvTitle);`方法使用，为的是不至于在使用底部按键栏浸入效果时导致头部的标题栏出现显示异常的问题。为了防止界面重绘，我们还需要一个全局布尔值`isFirstCreated`来保证`Activity`的`onWindowFocusChanged`方法中关于浸入式处理只执行一次。
##### 2.3 浸入式头部状态栏和底部按键栏同时使用
一个完美的效果，当然是期望头部与底部均实现浸入展示，则需要`StatusBarUtil`与`NavigationBarUtil`结合使用。在`MainActivity`中使用代码如下：
```
public class SNActivity extends AppCompatActivity {

    private ConstraintLayout mRootView;
    private TextView mTvTitle;

    //第一次进入当前activity的标志
    private boolean isFirstCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar);

        mRootView=findViewById(R.id.root);
        mTvTitle=findViewById(R.id.tv);

        //设置侵入式底部按键栏
        NavigationBarUtil.initNavigationBar(mRootView,SNActivity.this);
        mTvTitle.setText("设置侵入式头部底部状态栏");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!isFirstCreated){
            NavigationBarUtil.onWindowFocusChanged(SNActivity.this, Color.RED);
            //设置头部侵入式状态栏
            StatusBarUtil.immersive(this);//设置状态栏透明
            StatusBarUtil.setHeightAndPadding(this, mTvTitle);
            StatusBarUtil.darkMode(this, true);//设置状态栏文字颜色为黑色
            isFirstCreated=true;
        }
    }
}
```
##### 2.4 隐藏显示底部按键栏
这个主要涉及到`NavigationBarUtil`中关于显示和隐藏底部按键栏的方法，下面贴出在`MainActivity`中使用的示例代码：
```
public class HideWithoutActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout mRootView;
    private TextView mTvTitle;

    private Button mBtn;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        mRootView=findViewById(R.id.root);
        mTvTitle=findViewById(R.id.tv);
        mBtn=findViewById(R.id.button);
        mBtn2=findViewById(R.id.button2);

        mBtn.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button://显示底部按键栏
                NavigationBarUtil.showNavigationBar(HideWithoutActivity.this);
                break;
            case R.id.button2://隐藏底部按键栏
                NavigationBarUtil.hideNavigationBar(HideWithoutActivity.this);
                break;
            default:
                break;
        }
    }
}
```
##### 2.5 在含浸入式状态栏情况下显示和隐藏底部按键栏的处理
这种情况是在界面已经有浸入式头部和底部的时候，又要处理底部按键栏遮挡问题时才会出现的处理。会涉及到浸入式效果的去掉和恢复问题。个人建议尽量避免此种情况的发生。要么就只使用浸入式效果，但不去涉及处理底部键盘显示和隐藏的动作，要么涉及到底部按键栏的显示和隐藏的时候就别设置浸入式效果。因为底部按键栏显示和隐藏的操作，会干扰到浸入式效果的呈现。
但是，这里还是写出这种情况下在`MainActivity`中的使用代码吧，请注意在底部按键栏显示和隐藏时关于浸入式效果的处理问题:
```
public class HideActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout mRootView;
    private TextView mTvTitle;

    //第一次进入当前activity的标志
    private boolean isFirstCreated;
    private int mFirstTitleHeight;

    private Button mBtn;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        mRootView=findViewById(R.id.root);
        mTvTitle=findViewById(R.id.tv);
        mBtn=findViewById(R.id.button);
        mBtn2=findViewById(R.id.button2);

        //设置侵入式底部按键栏
        NavigationBarUtil.initNavigationBar(mRootView, HideActivity.this);
        mTvTitle.setText("处理底部按键栏遮挡问题");
        
        mBtn.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.i("=====onWindowFocusChanged========isFirstCreated="+isFirstCreated);

        if(!isFirstCreated){
            mFirstTitleHeight=mTvTitle.getHeight();
            showStatusBar();
            isFirstCreated=true;
        }else{
            LogUtil.i("=====onWindowFocusChanged========mFirstTitleHeight="+mFirstTitleHeight);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button://显示底部按键栏
                NavigationBarUtil.showNavigationBar(HideActivity.this);

                //========以下为显示底部按键栏时对浸入式效果的处理===============
                //设置侵入式底部按键栏
                NavigationBarUtil.initNavigationBar(mRootView, HideActivity.this);
                NavigationBarUtil.onWindowFocusChanged(HideActivity.this, Color.RED);
                //设置头部侵入式状态栏
                StatusBarUtil.immersive(this);//设置状态栏透明
                StatusBarUtil.darkMode(this, true);//设置状态栏文字颜色为黑色
                break;
            case R.id.button2://隐藏底部按键栏
                NavigationBarUtil.hideNavigationBar(HideActivity.this);

                //========以下为隐藏底部按键栏时对浸入式效果的处理===============
                mRootView.setPadding(0,0,0,0);
                break;
            default:
                break;
        }
    }

    private void showStatusBar(){
        LogUtil.i("=====onWindowFocusChanged========mFirstTitleHeight="+mFirstTitleHeight);

        NavigationBarUtil.onWindowFocusChanged(HideActivity.this, Color.RED);
        //设置头部侵入式状态栏
        StatusBarUtil.immersive(this);//设置状态栏透明
        StatusBarUtil.setHeightAndPadding(this, mTvTitle);
        StatusBarUtil.darkMode(this, true);//设置状态栏文字颜色为黑色
    }

}
```
#### 三.实现效果
具体实现效果请参考 [Android中浸入式状态栏的使用](https://www.jianshu.com/p/337505b25650)


