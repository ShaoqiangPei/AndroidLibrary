## ViewPagerHelper使用说明

### 概述
`ViewPagerHelper`主要用于帮助快捷实现`ViewPager+Fragment`的页面效果。

### 使用说明
#### 一. ViewPagerHelper 主要方法介绍
```
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
为了讲解方便，这里我准备了三个`Fragment`:`OneFragment`，`TwoFragment`，`ThreeFragment`。这三个`Fragment`页面基本一样，只是在显示颜色和文字上有稍许差异。
`Fragment`的快捷实现，大家可以参看

















