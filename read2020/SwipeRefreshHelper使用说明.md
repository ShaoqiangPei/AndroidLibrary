# SwipeRefreshHelper使用说明

## 概述
`Android`开发的官方有一个下拉刷新的控件——`SwipeRefreshLayout`，其使用前的引用如下：
```
dependencies {
    //swiperefreshlayout
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
}
```
为了使下拉刷新`控件`SwipeRefreshLayout的使用更加方便快捷，于是封装了`SwipeRefreshHelper`.本库已经对下来刷新控件`SwipeRefreshLayout`的引用做了进一步处理，
因此大家在使用本类时无需再做`swiperefreshlayout`库引用。

## 使用说明
### 一. SwipeRefreshHelper 方法简介
`SwipeRefreshHelper`中主要方法如下：
```
    /***
     * 设置下拉图标主题色
     *
     * @param colorResIds 颜色样式为：R.color.green
     *
     * 最少设置一种颜色
     * 最多可设置四个参数，如：
     *     setColorSchemeResources(R.color.green,R.color.red,R.color.black,R.color.blue)
     */
    public void setColorSchemeResources(@ColorRes int... colorResIds)
    
    /***
     * 设置下拉图标背景色(不设置的话,默认背景色为白色)
     *
     * @param color 颜色样式为：R.color.green
     */
    public void setProgressBackgroundColorSchemeResource(int color)
    
    /***
     * 初始化下拉刷新
     *
     * 一般在 initData() 中初始化时调用
     */
    public void initRefresh(OnRefreshListener listener)
    
    /**自动刷新**/
    public void autoRefresh()
    
    /**停止刷新**/
    public void stopRefresh()
    
    /**是否正在刷新**/
    public boolean isRefreshing()
    
```
### 二. SwipeRefreshHelper使用介绍
在你的`Activity`对应的布局中引入`SwipeRefreshLayout`控件,类似如下：
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:pain="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white">

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/mSwipeRefreshLayout"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/mContentLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            //ConstraintLayout布局也可以替换成其他布局,其他代码省略
            //......

        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```
在`Activity`中声明`SwipeRefreshLayout`和`SwipeRefreshHelper`:
```
    //声明控件和对象
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
```
在`Activity`的`initView()`中初始化`SwipeRefreshLayout`控件:
```
    mSwipeRefreshLayout=findViewById(R.id.mSwipeRefreshLayout);
```
在`Activity`的`initData()`中对`SwipeRefreshLayout`做必要的基本设置和初始化下拉刷新：
```
        mSwipeRefreshHelper=new SwipeRefreshHelper(mSwipeRefreshLayout);

        //设置下拉主题颜色(最多设置四种颜色,最少设置一种颜色)
        mSwipeRefreshHelper.setColorSchemeResources(R.color.green,R.color.red,R.color.black,R.color.blue);
//        //设置背景色(不设置时默认为白色背景)
//        mSwipeRefreshHelper.setProgressBackgroundColorSchemeResource(R.color.green);

        //初始化下拉刷新
        mSwipeRefreshHelper.initRefresh(new SwipeRefreshHelper.OnRefreshListener() {
            @Override
            public void refresh() {
                //下拉刷新的处理
                //......

                //模拟通讯流程
                //......
            }
        });
```
这里需要注意的是，若不设置`setProgressBackgroundColorSchemeResource`则会默认下拉刷新图标的背景色为白色。  
如果在进入`Activity`界面时有自动下拉刷新的需求,则可以在` mSwipeRefreshHelper.initRefresh`之后做如下调用：
```
        //自动刷新(需要在initRefresh方法之后调用)
        mSwipeRefreshHelper.autoRefresh();
```
需要获取`SwipeRefreshLayout`当前下拉状态时，可以调用如下方法：
```
       if(mSwipeRefreshHelper.isRefreshing()){
           ToastUtil.shortShow("正在刷新");
       }else{
           ToastUtil.shortShow("刷新完毕");
       }
```
当获取通讯结果后，则需要结束下拉刷新状态，类似如下：
```
        //结束刷新
        mSwipeRefreshHelper.stopRefresh();
```
### 三. SwipeRefreshHelper 在 Activity 中使用示例
以定时器模拟下拉刷新发起通讯的动作，则`SwipeRefreshHelper` 在 `Activity` 中使用示例如下：
```
public class TempActivity extends AppCompatActivity{

    private TextView mTvTest;
    private Button mBtnTest;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Timer mTimer;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        //初始化控件
        initView();
        //初始化数据
        initData();
        //控件监听
        setListener();
    }

    /**初始化控件**/
    private void initView(){
        mTvTest=findViewById(R.id.mTvTest);
        mBtnTest=findViewById(R.id.mBtnTest);
        mSwipeRefreshLayout=findViewById(R.id.mSwipeRefreshLayout);
    }

    private void initData(){
        mSwipeRefreshHelper=new SwipeRefreshHelper(mSwipeRefreshLayout);

        //设置下拉主题颜色(最多设置四种颜色,最少设置一种颜色)
        mSwipeRefreshHelper.setColorSchemeResources(R.color.green,R.color.red,R.color.black,R.color.blue);
//        //设置背景色(不设置时默认为白色背景)
//        mSwipeRefreshHelper.setProgressBackgroundColorSchemeResource(R.color.green);

        //初始化下拉刷新
        mSwipeRefreshHelper.initRefresh(new SwipeRefreshHelper.OnRefreshListener() {
            @Override
            public void refresh() {
                //下拉刷新的处理
                //......

                //模拟通讯流程
                startTimer();
            }
        });

        //需要一进入activity界面时进行自动刷新时调用
//        //自动刷新(需要在initRefresh方法之后调用)
//        mSwipeRefreshHelper.autoRefresh();
    }


    /**控件监听**/
    private void setListener() {
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSwipeRefreshHelper.isRefreshing()){
                    ToastUtil.shortShow("正在刷新");
                }else{
                    ToastUtil.shortShow("刷新完毕");
                }
            }
        });

    }

    private void startTimer(){
        mTimer=new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                if(count==8){
                    receiveHttp();
                    count=0;
                    mTimer.cancel();
                    mTimer=null;
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortShow("count="+count);
                        }
                    });
                }
            }
        },0,1000);
    }

    private void receiveHttp(){
        //结束刷新
        mSwipeRefreshHelper.stopRefresh();
    }

    @Override
    protected void onDestroy() {
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        super.onDestroy();
    }
}
```

