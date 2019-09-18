## ComAdapter使用说明

### 概述
ComAdapter是一个通用适配器基类，里面集成了adapter创建需要的一些基本方法，包括控件初始化,获取布局对象，加载动画，列表数据加载，控件点击事件监听等方法(具体可参见代码)。
当你要实现一个线性列表，需要创建adapter的时候，继承ComAdapter可以帮你快速实现自己的adapter。

### 使用说明
#### 一. 继承ComAdapter,写自己的Adapter
当RecyclerView加载列表的时候，需要一个适配器(NameAdapter)，你可以像这样快速来创建它：
```
public class NameAdapter<T>extends ComAdapter {

    private TextView mTvName;

    public NameAdapter(List<T> data, Context context) {
        //加载布局和数据
        super(R.layout.item_layout, data, context);
    }

    @Override
    public <T>void initView(BaseViewHolder viewHolder, T obj) {
        //控件初始化
        mTvName=viewHolder.getView(R.id.tv);
    }

    @Override
    public <T>void initData(BaseViewHolder viewHolder, T obj) {
        String name=obj.toString();
        mTvName.setText(name);
    }

    @Override
    public <T>void setListener(BaseViewHolder viewHolder, T obj) {
        //添加控件监听
        addOnClickListener(mTvName,viewHolder,obj);
    }

}
```
#### 二. 在mainActivity中调用
##### 2.1 线性布局调用
```
mNames=new ArrayList<>();
//for (int i = 0; i < 10; i++) {
//    mNames.add("小黄"+i);
//}
mNameAdapter=new NameAdapter<>(mNames,MainActivity.this);
mNameAdapter.setRecyclerLinearManager(mRecyclerView);
```
##### 2.2 九宫格布局调用
```
mNames=new ArrayList<>();
//for (int i = 0; i < 10; i++) {
//    mNames.add("小黄"+i);
//}
mNameAdapter=new NameAdapter<>(mNames,MainActivity.this);
mNameAdapter.setRecyclerGridManager(mRecyclerView,4);
```
#### 三. 设置分割线,返回RecyclerView.ItemDecoration对象
```
//设置线性布局分割线
LinearDividerItemDecoration linearDivider=mNameAdapter.setLinearLayoutItemSpace(mRecyclerView,5,R.color.colorAccent);
//设置九宫格局分割线
GridDividerItemDecoration gridDivider=mNameAdapter.setGridLayoutItemSpace(mRecyclerView,5,R.color.colorAccent);
```
#### 四. 移除分割线
```
/**移除RecycleView间距**/
removeItemSpace(RecyclerView recyclerView,  RecyclerView.ItemDecoration divider)
```
#### 五.点击事件
```
        //点击事件
        mNameAdapter.setOnItemClickListener(new AdapterHelper.OnItemClickListener() {
            @Override
            public void itemClickListener(View view, BaseViewHolder viewHolder, Object obj) {
                switch (view.getId()) {
                    case R.id.tv:
                        ToastUtil.shortShow("====1====="+obj.toString());
                        break;
                    default:
                        break;
                }
            }
        });
```
