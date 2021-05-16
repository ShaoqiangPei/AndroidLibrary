## AppActivity加载Fragment能力使用说明

### 概述
主要用于处理含`Fragment`的`Activity`,具备返回键处理的逻辑。

### 使用说明
#### 一. AppActivity加载fragment时主要涉及到的重点
`AppActivity`加载`fragment`时，主要涉及两个问题：
- activity 和 fragment 交互传值
- activity 和 fragment 对于返回键处理

这里我重点讲`返回键处理`的使用，因为`传值`的逻辑已经包含在`返回键处理`逻辑中。
#### 二. Activity 和 Fragment 对于返回键处理使用说明
`Activty`中加载有动态的`Fragment`，对于该`activity`依然需要继承`AppActivity`,示例如下：
```
public class MainActivity extends AppActivity{

}
```
当 `MainActivity` 不需要处理返回键的时候，它无需重写`onActivityKeyDown(int keyCode, KeyEvent event)`方法。此时`MainActivity`点击返回键会执行系统对于返回键响应的方法。 当 `MainActivity` 中需要处理返回键逻辑的时候,则需要重写`AppActivity`的`onActivityKeyDown(int keyCode, KeyEvent event)`方法，并且将返回值设置为true,类似如下：
```
public class MainActivity extends AppFragActivity{

   //其他代码省略
   //......

    @Override
    protected boolean onActivityKeyDown(int keyCode, KeyEvent event) {
        //此处写MianActivity的返回键处理逻辑
        //......

        //最后返回true,用于拦截系统返回键功能
        return true;
    }
}
```
`Fragment`中处理返回键逻辑的话,`Fragment`需要实现`OnFragmentBackListener`接口，如下：
```
public class FragmentB extends Fragment implements OnFragmentBackListener {

}
```
然后在`Fragment`的`onAttach(Context context)`中给`Context`赋值，在`onCreateView`中设置监听,在`onDestroyView()`中解除监听，并在`onBackForward(int keyCode, KeyEvent event)`方法中对`Fragment`界面返回键做处理，类似如下：
```
//Fragment实现OnFragmentBackListener接口
public class FragmentB extends Fragment implements OnFragmentBackListener {

    //声明Context
    private Context mContext;
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //给context赋值
        this.mContext = context;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutView = inflater.inflate(R.layout.fragment_b, container, false);

        initView();
        initData();
        setListener();
        
        return mLayoutView;
    }
    
    private void initView() {
        
    }

    private void initData() {
        
    }

    private void setListener() {
        //监听返回键
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).setOnFragmentBackListener(this);
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除返回键监听
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).setOnFragmentBackListener(null);
        }
    }
    
    @Override
    public void onBackForward(int keyCode, KeyEvent event) {
        //fragment中返回键处理逻辑
        //......
    }
   
}

```
#### 三. 需要注意的问题
- `AppActivity`加载`frgment`的设计初衷是`fragment`按正常生命周期执行。所以务必确定`activity`到`Fragment`的跳转是用的`add()`系列方法，而不是`show()`和`hide()`,因为`show()`和`hide()`不走`Fragment`生命周期，这样会导致返回键功能没反应，或者出"奇妙"的`bug`
- `Activity` 向 `Fragment` 传值可以参考上面关于返回键处理的逻辑。

