## AffairManager使用说明

### 概述
AffairManager 是用于处理事务队列的工具类。原理是将一个个的事务添加到 AffairManager 的队列中，然后依次处理。

### 使用说明
#### 一. AffairManager初始化
初始化事务队列处理类对象，你可以这样:
```
    //声明
    private AffairManager mAffairManager;
    
    //初始化对象
    mAffairManager=new AffairManager();
```
#### 二. AffairManager主要方法
```
    /**
     * 设置事务队列处理时间间隔
     * @param delayTime 间隔时间,单位毫秒,不设置则默认500毫秒
     * @return
     */
    public AffairManager setDelayTime(long delayTime)
    
    /**设置事务处理监听**/
    public void setOnAffairListener(OnAffairListener onAffairListener)
    
    /**事务处理方法**/
    public void handleAffair(Object obj) 
   
    /**需要更新UI的时候,可以在doAffair(Object obj)或affairDestroy()中执行**/
    public void updateInUI(Activity activity, Runnable runnable)
    
    /**结束事务**/
    public void stopAffair()
```
#### 三. AffairManager使用示例
##### 3.1 声明及初始化AffairManager
```
    //声明
    private AffairManager mAffairManager;
    
    //初始化对象
    mAffairManager=new AffairManager();
```
##### 3.2 设置事务处理监听，处理时间间隔
- 添加的事务，在`doAffair(Object obj)`依次处理,事务队列全部执行完毕后,回调`affairDestroy()`方法  
- 设置事务处理时间间隔,单位为毫秒,若不设置,则默认事务处理时间间隔为500毫秒  
- 在`doAffair(Object obj)`或`affairDestroy()`方法中需要更新ui时，可调用`updateInUI(Activity activity, Runnable runnable)`方法
- 需要注意的是`doAffair(Object obj)`会有一个`boolean`返回值,默认是返回`false`，表示该事务执行完毕后会将`obj`从事务队列中移除，若有特殊情况需要处理(事务执行完毕后，
`obj`不从事务队列中移除)，则你需要在`doAffair(Object obj)`方法内部做相应处理，然后返回`true`
```
    //设置事务处理时间间隔,单位毫秒
    mAffairManager.setDelayTime(1500)
          //设置事务处理监听
         .setOnAffairListener(new AffairManager.OnAffairListener() {
          @Override
          public void doAffair(Object obj) {
             String time = DateUtil.getTimeToString(System.currentTimeMillis(), "HH:mm:ss.SSS");
             LogUtil.i("=1======执行===obj==="+obj.toString()+"   time="+time);
             //此处对事务做具体处理
             //......

             //需要更新ui时调用
             mAffairManager.updateInUI(MainActivity.this, new Runnable() {
                @Override
                public void run() {
                     mTextView.setText(obj.toString());
                }
             });
             
//            //特殊情况不移除特殊情况的处理,不移除
//                if(obj.toString().trim().contains("5")){
//                  LogUtil.i("=====特殊情况不移除===  time="+time);
//                  return true;
//              }
             
             return false
          }

          @Override
          public void affairDestroy() {
            LogUtil.i("======队列中事务全部处理完毕=======");
            
            //事务全部处理完毕后的需要处理的逻辑
            //......
            
             //需要更新ui时调用
             mAffairManager.updateInUI(MainActivity.this, new Runnable() {
                @Override
                public void run() {
                     mTextView.setText("队列中事务全部处理完毕");
                }
             });
          }
     });
```
##### 3.3 处理事务
需要处理事务时,调用以下方法
``` 
    //需要处理事务时,添加事务到队列
    mAffairManager.handleAffair("a疫情防御"+i);
```
##### 3.4 结束事务
退出时，结束事务队列
```
    //结束事务
    mAffairManager.stopAffair();
```

