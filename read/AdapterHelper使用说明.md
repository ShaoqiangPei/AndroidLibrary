## AdapterHelper使用说明

### 概述
AdapterHelper是一个Adapter的帮助类，里面集成一些adapter创建和使用时需要的方法。通过这个类，我们可以方便的使用adapter。

### 使用说明
AdapterHelper这个类比较简单，下面就里面的方法做些简单的介绍。
#### 1. AdapterHelper类初始化
AdapterHelper是一个单例，初始化你可以这样：
```
AdapterHelper adapter=AdapterHelper.getInstance();
```
一般我们都直接这样用：
```
AdapterHelper.getInstance()
```
#### 2. 根据布局id获取布局对象
```
/**根据布局id获取布局对象**/
public View getLayoutView(int layoutId, Context context)
```
#### 3. 设置String值,obj一般是String或int
```
/**设置String值**/
public String getAdapterString(Object obj)
```
#### 4. 设置线性RecyclerView
```
/**设置线性RecyclerView**/
public void setRecyclerLinearManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, Context context)
```
#### 5. 设置线性RecycleView间距,返回LinearDividerItemDecoration对象
```
/**设置线性RecycleView间距**/
public LinearDividerItemDecoration setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context) 
```
#### 6. 设置九宫格RecyclerView
```
/**设置九宫格RecyclerView**/
public void setRecyclerGridManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, int itemCount, Context context)
```
#### 7. 设置九宫格间距,返回GridDividerItemDecoration对象
```
/**设置九宫格间距**/
public GridDividerItemDecoration setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context)
```
#### 8. 移除RecycleView间距
```
/**移除RecycleView间距**/
public void removeItemSpace(RecyclerView recyclerView,  RecyclerView.ItemDecoration divider)
```
#### 9. 获取position，当添加有header或footer要注意改变
```
/**获取position，当添加有header或footer要注意改变**/
public int getPosition(BaseViewHolder viewHolder) 
```
#### 10. 添加控件监听
```
/**
  * 添加控件监听
  **/
public void addOnClickListener(OnItemClickListener listener, View view, final BaseViewHolder viewHolder, final Object obj)
```
### 11.滑动到指定位置(此position会列表置顶)
```
/***
   * 滑动到指定位置(此position会列表置顶)
   * @param context
   * @param position
   */
public void moveToPosition(RecyclerView recyclerView, int position, Context context)
```
### 12.adapter中设置item高度
当你的adaper为九宫格模式。要设置item的高度与宽度一样，形成方块造型的话，你可以在adapter中view初始化的时候，类似下面这样设置view高度：
```
    //在九宫格adapter初始化方法中设置item高度
    //此段代码仅作使用参考
    public void itemHeight() {
        //九宫格设置item高度[思路:(屏幕宽度-间隙*间隙个数)/item个数]
        int height = (ScreenUtil.getWidth() - ScreenUtil.dp2px(5, context)*2) / 3;
        ViewUtil.setViewHeight(height, view);
    }
```
