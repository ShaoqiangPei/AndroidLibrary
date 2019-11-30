## RemoteService使用说明

### 概述
RemoteService作为一个绑定式服务的父类，主要用作创建一个服务对象及bind的相关处理，并不做外部使用，也不给特定“绑定式”service提供有效的调用方法。

### 使用
RemoteService仅作为自建的绑定式服务的基类，协助开发者快速创建一个自建的service。
若你要自建一个“绑定式”服务 MyService，你可以这样：
```
/**
 * Title:自建绑定式服务，继承RemoteService
 * description:
 * autor:pei
 * created on 2019/11/28
 */
public class MyService extends RemoteService{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return super.onBind(arg0);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected RemoteService getSelfService() {
        return MyService.this;
    }

}
```
值得注意的是，getSelfService() 方法必须重写，并且返回当前Service对象。



