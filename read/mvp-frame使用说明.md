## mvp-frame使用说明

### 概述
mvp-frame 架构主要为 app 以 MVP架构 模式构建而产生，实现项目逻辑解耦。
当你app中涉及到通讯模块的时候，你可以考虑使用 mvp_frame 包中的基类。

### 使用说明
#### 一. mvp_frame 包下有以下类
- AppActivity：MVP架构 Activity基类  
- AppFragment：MVP架构 Fragment基类  
- PrePresenter：T-MVP Presenter基类  
- PreView：MVP-View基类  
#### 二. mvp-frame在Activity中的使用  
以登录通讯为例，则登录功能模块一共要写三个类：   
- MainContract：登录功能接口层  
- MainPresenter：登录功能通讯逻辑层  
- MainActivity：登录界面ui   
##### 2.1 MainContract — 登录功能接口层  
MainContract中包含两个接口，一个需要实现PreView接口，另一个需要实现PrePresenter接口。示例代码如下：
```
/**
 * Title:登录功能接口层
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainContract {

    public interface View extends PreView {
        void loginSuccess();
        void loginFail(String msg);
    }

    public interface Presenter extends PrePresenter {
        //登录
        void login();
    }

}
```
##### 2.2 MainPresenter — 登录功能通讯逻辑层 
MainPresenter需要实现 MainContract.Presenter 接口，示例代码如下：
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

    public MainPresenter(Context context, PreView view) {
        this.mContext = context;
        this.mView = (MainContract.View) view;
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
    }

}
```
##### 2.3 MainActivity — 登录界面ui
涉及到通讯，MainActivity需要继承AppActivity，并实现 MainContract.View 接口(在含有Fragment加载的Activity中使用也是如此)，示例代码如下：
```
/**
 * Title:登录界面
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainActivity extends AppActivity implements MainContract.View {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.btn1)
    Button mBtn1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Nullable
    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(mContext,this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mBtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.btn1:
                test();
                break;
            default:
                break;
        }
    }

    private void test() {


    }


    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail(String msg) {

    }

}
```
#### 三. mvp-frame在Fragment中的使用  
mvp-frame 在 Fragment中使用的时候，"MainContract登录功能接口层" 和 "MainPresenter登录功能通讯逻辑层" 没什么变化，唯一变化的是 “界面ui层” 的继承关系变了，由对 activity 的继承，改为对 Fragment 的继承，类似下面这样：  
```
/**
 * Title:登录界面
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class MainFragment extends AppFragment implements MainContract.View {
     
     //其他代码省略
     //......

}
```

