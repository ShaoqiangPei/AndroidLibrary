## AppFragActivity使用说明

### 概述
`AppFragActivity`主要用于处理含`Fragment`的`Activity`,具备返回键处理的逻辑。

### 使用说明
#### 一.activity使用
当你新建的`TestActivity`涉及到`Fragment`加载的话，你可以继承`AppFragActivity`。
你可以像下面这样建一个你的含`Fragment`的`Activity`:
```
public class TestActivity extends AppFragActivity {
    
    
    @Override
    public int getContentViewId() {
        return R.layout.activity_temp;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
    
}
```
当涉及到mvp架构(一般是涉及到通讯的时候)，则需要重写`getPresenter()`方法，类似如下：
```
public class TestActivity extends AppFragActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_temp;
    }

    @Override
    public void initData() {

    }

    /***
     * 若要使用mvp架构,需要在子类中重写此方法并返回具体的 PrePresenter
     * @return
     */
    @Override
    public PrePresenter getPresenter() {
        return super.getPresenter();
    }

    @Override
    public void setListener() {

    }

}
```

