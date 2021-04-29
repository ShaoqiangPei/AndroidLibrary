## AppActivity网络通讯能力使用说明

### 概述
主要讲述`Activity`基类 —— `AppActivity` 在需要进行`网络通讯`时，如何快速接入`MVP模式`，可以协助开发者快速实现`MVP`模式。

### 说明
当一个`Activity`不牵涉到网络通讯的时候，我们直接继承`AppActivity`写自己的`activity`并实现`AppActivity`的`getContentViewId()`, `initData()`和`setListener()`这三个方法即可，示例如下：
```
public class TestActivity extends AppActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;//activity_test为TestActivity对应的xml文件
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
```
若涉及到`mvp架构`(即涉及到网络通讯会用到)，那么你还要重写`AppActivity`的`getPresenter()`方法，示例如下：
```
public class TestActivity extends AppActivity implements MainContract.View {

    @Override
    public int getContentViewId() {
        return R.layout.activity_temp;
    }

     
    @Override
    public PrePresenter getPresenter() { 
        return new MainPresenter(mContext,this);
    }

    @Override
    public void initData() {
       
    }


    @Override
    public void setListener() {
       
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    //其他方法省略
    //......
}
```

更多关于`MVP架构`网络通讯的使用，可参考  
[mvp-frame](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/mvp-frame%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)


