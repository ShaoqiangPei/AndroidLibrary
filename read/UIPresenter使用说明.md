## UIPresenter使用说明

### 概述
UIPresenter 隶属于 mvp-frame 框架，主要用于 Presenter类 中涉及到数据更新主线程ui时调用。

### 使用说明
#### 一.UIPresenter使用
初始化：
```
UIPresenter uiPresenter=new UIPresenter();
```
更新方法如下：
```
    /**更新ui的方法**/
    public  <T> void updateUI(Context context, PreView view, UIData<T> data, OnUIListener listener)
```
#### 二.UIPresenter在Presenter类中使用范例
UIPresenter主要在Presenter类中使用，下面以在MainPresenter通过UIPresenter更新主线程UI为例：
数据实体类 UIData，请参看[源码](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/androidlibrary/commonlibrary/src/main/java/com/android/commonlibrary/entity/UIData.java)  
```
/**
 * Title:登录功能通讯逻辑层
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainPresenter implements MainContract.Presenter{

    private Context mContext;
    private MainContract.View mView;
    
    //声明UIPresenter
    private UIPresenter mUiPresenter;


    public MainPresenter(Context context, PreView view) {
        this.mContext = context;
        this.mView = (MainContract.View) view;

        //初始化UIPresenter
        mUiPresenter=new UIPresenter();
    }

    @Override
    public void attachView() {//可以用来注册RxBus

    }

    @Override
    public void detachView() {//可以用来注销RxBus

    }

    @Override
    public void login() {
        //以下写通讯代码
        //......
        
        //将返回结果封装到UIData

        UIData uiData=new UIData();
        uiData.setCode(1);
        uiData.setNotifyId(2);
        uiData.setMessage("oaiuehe");
        uiData.setData("kajene");
        //将返回结果去更新ui
        mUiPresenter.updateUI(mContext, mView, uiData, new UIPresenter.OnUIListener() {
            @Override
            public <T> void update(PreView view, UIData<T> data) {
                
                //将值通过mView传给activity
                mView.loginSuccess(data);
            }
        });
    }

}
```
