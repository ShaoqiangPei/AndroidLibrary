## ViewPagerHelper使用说明

### 概述
`ViewPagerHelper`主要用于帮助快捷实现`ViewPager+Fragment`的页面效果。

### 使用说明
#### 一. ViewPagerHelper 主要方法介绍
```
    /**添加标题**/
    public ViewPagerHelper addTitleList(List<String>titleList)


    /**
     * 添加fragment
     **/
    public ViewPagerHelper addFragment(Fragment fragment)

    /***
     * 设置ViewPger滑至边界是否去掉边界阴影
     *
     * @param removeBoundShadow true:去掉边界阴影  false:滑到边界有阴影
     *                          默认为false,即滑至边界有边界阴影
     */
    public ViewPagerHelper setRemoveBoundShadow(boolean removeBoundShadow) 

    /**
     * 设置fragment预加载个数(若不设置，则默认预加载mFragmentList集合长度)
     *
     * @param count
     */
    public ViewPagerHelper setLoadCount(int count)

    /**
     * viewpager默认显示page下标(若不设置，默认index=0,即默认ViewPager加载第一页)
     *
     * @param index
     */
    public ViewPagerHelper setLoadIndex(int index)

    /**
     * 初始化
     * @param viewPager
     * @param context
     */
    public void init(ViewPager viewPager, Context context)

    /**
     * 获取FragmentList对象
     **/
    public List<Fragment> getFragmentList()

    /**
     * 获取ViewPager适配器
     **/
    public ViewPagerAdapter getViewPagerAdapter()
```
这里需要注意的是`setRemoveBoundShadow(boolean removeBoundShadow)`主要用于处理`ViewPager`滑到边界时出现边界阴影的问题。
默认情况下 `removeBoundShadow=false`，即滑动是带有边界阴影的。若为了显示效果滑动无边界阴影，可以像下面这样设置：
```
//去掉滑动的边界阴影
setRemoveBoundShadow(true)
```
若不想设置如上代码的话，你也可以在布局中给`ViewPager`添加`android:overScrollMode="never"`,即`ViewPager`在布局中显示类似如下：
```
    <!--android:overScrollMode="never" 用于去除viewPager滑动边界阴影问题-->
    <androidx.viewpager.widget.ViewPager
        android:id="@+id/vpager"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:overScrollMode="never"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn1"
        app:layout_constraintBottom_toBottomOf="parent"/>
```
#### 二. ViewPagerHelper 在 Activity 中的使用
为了讲解方便，这里我准备了三个`Fragment`:`OneFragment`，`TwoFragment`，`ThreeFragment`。这三个`Fragment`页面基本一样，只是在显示颜色和文字上有稍许差异。`Fragment`的快捷实现，大家可以参看[AppFragment使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppFragment%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md),这里就不赘述了。  
接下来要在`Activity`对应的布局中添加`ViewPager`控件，类似如下：
```
    <androidx.viewpager.widget.ViewPager
        android:id="@+id/vpager"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn1"
        app:layout_constraintBottom_toBottomOf="parent"/>
```
然后在`Activity`中做相关声明和初始化操作：
```
    //声明
    private ViewPager mViewPager;
    private ViewPagerHelper mViewPagerHelper;

    //初始化控件
    mViewPager=findViewById(R.id.vpager);

    //初始化 ViewPager+Fragment页面效果
    private void initData(){
        mViewPagerHelper=new ViewPagerHelper();
        mViewPagerHelper.addTitleList(mTitleList) //可不选，不设置则不显示标题文字
                .addFragment(new OneFragment())
                .addFragment(new TwoFragment())
                .addFragment(new ThreeFragment())
                .setRemoveBoundShadow(true)//true:去掉边界阴影  false:滑到边界有阴影。默认为false,即滑至边界有边界阴影
                .setLoadCount(3)//设置预加载fragment个数，不设置的话默认添加几个fragment就预加载几个
                .setLoadIndex(0)//设置初始化时显示第几页,默认下表为0,即加载第一页
                .init(mViewPager,TempActivity.this);//此行设置要放最后
    }
```
监听`ViewPager`切换界面事件,可以像下面这样：
```
    /**设置监听**/
    private void setListener(){
        //viewPager页面滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(position);
                        ToastUtil.shortShow("第一页");
                        mTv.setText("第一页");
                        break;
                    case 1:
                        mViewPager.setCurrentItem(position);
                        ToastUtil.shortShow("第二页");
                        mTv.setText("第二页");
                        break;
                    case 2:
                        mViewPager.setCurrentItem(position);
                        ToastUtil.shortShow("第三页");
                        mTv.setText("第三页");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
            }
        });
    }
```

