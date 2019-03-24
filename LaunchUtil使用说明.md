#### LaunchUtil使用说明
##### 实现启动页view颜色渐变的工具类

##### 1.初始化
```
    /**
     * 初始化
     *
     * @param startColor 八位或六位色值，如："#F3D266" 或 "#FFF3D266"
     * @param endColor   八位或六位色值，如："#FFFFFF" 或 "#FFFFFFFF"
     */
init(String startColor,String endColor);
```
初始化时需要传两个色值，渐变起始色值 startColor 和渐变终止色值 endColor，色值均为String数据类型,色值为八位或六位色值，如："#F3D266" 或 "#FFF3D266"

##### 2.设置颜色变化时间总长(单位毫秒)
```
LaunchUtil setDelayTime(long delayTime);
```

##### 3.获取颜色渐变时间总长(单位毫秒)
```
long getDelayTime();
```
若未设置此属性，则得到默认时间为 1500毫秒

##### 4.设置颜色渐变频率(单位毫秒)
```
setChangeTime(long changeTime);
```
##### 5.获取颜色渐变频率(单位毫秒)
```
getChangeTime();
```
若未设置此属性，则得到默认渐变频率为 10毫秒

##### 6.执行渐变效果的方法
```
start(final View view, final LaunchUtil.OnFinishListener listener)
```
其中 view 为要执行渐变效果的控件，listener为渐变执行完毕的监听，一般在启动页用此方法的时候，view设为布局控件。

具体调用：
```
        LaunchUtil.getInstance().init("#FF0000","#0000FF");//初始化
        LaunchUtil.getInstance().setDelayTime(1500);//设置渐变总时长,可不设置
        LaunchUtil.getInstance().setChangeTime(10);//设置渐变总时长,可不设置
        LaunchUtil.getInstance().start(mLayoutView, new LaunchUtil.OnFinishListener() {
            @Override
            public void onFinish() {
                //启动页执行完毕的处理
                //......
            }
        });
```
LaunchUtil支持链式调用，但init方法必须是第一个调用，start方法必须是最后一个调用，调用示例如下：
```
        LaunchUtil.getInstance().init("#FF0000", "#0000ff")//初始化时设置渐变起始和终止颜色
                .setDelayTime(1500)//设置变化总时长,不设置的时候，默认时间是1.5秒
                .setChangeTime(10)//设置变化频率,不设置的时候，默认时间是10毫秒
                //渐变结束时的监听(启动页的时候，一般设置mView为最外层布局控件)
                .start(mLayoutView, new LaunchUtil.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        //LogUtil.i("===启动页加载完毕====");
                        //颜色渐变加载完毕的监听
                    }
                });

```

