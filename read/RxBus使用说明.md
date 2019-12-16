## RxBus使用说明

### 概述
RxBus 是基于 rxJava2.x 封装的一个消息事务传递总线。方便在app中进行消息的传递。
使用方便快捷。

### 使用说明
RxBus的使用有以下几个步骤：
1. 注册
2. 传值
3. 注销
#### 一. RxBus注册及接收数据  
你可以在你需要接收传值的地方像下面这样注册一个RxBus：
```
        //注册RxBus
        RxBus.getInstance().register(MainActivity.class, new RxBus.OnCallBack() {
            @Override
            public void callBack(Object obj) {
                //接收传过来的值
                //......
            }
        });
```
这里需要注意的是，register第一个传的值是String类型，我们一般传class 的name(全路径)作为String，不一定要是Activity或Fragment哟，只要是一个class的全路径name就行。
#### 二. RxBus传值  
在你需要传值的时候，你可以像下面这样：
```
//RxBus传值
RxBus.getInstance().post(rxData);
```
post的时候可以传Object，但是在一个app项目中会有多个值传来传去，为了防止消息传递中混淆，我将要传值的数据封装到RxData实体类中(后面会给出RxData代码)，这时RxBus传值则变成(以传一个字符串为例)：
```
                RxData rxData=new RxData();
                rxData.setCode(1);
                rxData.setData("我是rx传值");
                RxBus.getInstance().post(rxData);
```
然后在接收值的时候则这样处理：
```
        //注册RxBus
        RxBus.getInstance().register(MainActivity.class, new RxBus.OnCallBack() {
            @Override
            public void callBack(Object obj) {
                if(obj!=null&&obj instanceof RxData){
                    int code=((RxData) obj).getCode();
                    LogUtil.i("=======code=="+code);
                    
                    switch (code) {
                        case 1:
                            String data=((RxData) obj).getData().toString();
                            
                            break;
                        default:
                            break;
                    }
                }
            }
        });
```
#### 三. RxBus注销  
最后在使用完RxBus后，要注销RxBus，像下面这样：
```
        //注销RxBus
        RxBus.getInstance().unRegister(MainActivity.class);
```
需要注意的是，注销时传的String要和注册时传的String一 一对应。

