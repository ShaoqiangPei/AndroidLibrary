## AppFragActivity使用说明

### 概述
AppFragActivity 是 AppActivity 的一个子类，具备AppActivity的所有特性.
此类用在当activity需要开启Fragment的时候使用，主要用于处理Activity和Fragment间对于返回键的处理。
Activity向Fragment传值的话，也可以参考这个返回键处理的逻辑。

### 使用说明
#### 一. AppFragActivity 使用 AppActivity 相关特性
AppFragActivity 是 AppActivity 的一个子类,具备AppActivity的所有特性的所有特性.
要使用 AppActivity 相关特性的话可以参考[AppActivity使用说明](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivity%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)  
#### 二. Activity 和 Fragment 对于返回键处理使用说明
Activity中加载有动态的Fragment，那么对于activity，需要继承AppFragActivity。如MainActivity中有动态加载Fragment的需要，那么mainActivity可以这样写：
```
//activity有加载Fragment时，需要继承AppFragActivity,而不是AppAppActivity,这样更有助于处理Fragment相关问题
public class MainActivity extends AppFragActivity{

}
```
当 MainActivity 不需要处理返回键的时候，它无需重写onActivityKeyDown(int keyCode, KeyEvent event)方法。此时MainActivity点击返回键会执行系统对于返回键响应的方法。
当 MainActivity 中需要处理返回键逻辑的时候,则需要重写AppFragActivity的onActivityKeyDown(int keyCode, KeyEvent event)方法，并且将返回值设置为true,类似如下：
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
Fragment中处理返回键逻辑的话,Fragment需要实现OnFragmentBackListener接口，如下：
```
public class FragmentB extends Fragment implements OnFragmentBackListener {

}
```
然后在Fragment的onAttach(Context context)中给Context赋值，在onCreateView中设置监听,在onDestroyView()中解除监听，并在onBackForward(int keyCode, KeyEvent event)方法中对Fragment界面返回键做处理，类似如下：
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
- AppFragActivity 遵循的是Fragment生命周期处理，所以务必确定activity到Fragment的跳转是用的add()系列方法，而不是show()和hide(),因为show()和hide()不走Fragment生命周期，这样会导致返回键功能没反应，或者出奇妙的bug
- Activity 向 Fragment 传值可以参考AppFragActivity返回键处理的逻辑。
