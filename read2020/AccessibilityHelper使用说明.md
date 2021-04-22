## AccessibilityHelper使用说明

### 概述
`AccessibilityHelper`主要用来协助无障碍服务`AccessibilityService`的使用。我们可以通过
`AccessibilityHelper`快速在界面上找到控件并实现该控件的一系列操作，如`点击`,`输入`和`滑动`等。

### 使用说明
#### 一.AccessibilityHelper方法简介
`AccessibilityHelper`作为一个单例工具类，具备以下主要方法:
```
    /**点击动作**/
    public boolean performClick(AccessibilityNodeInfo targetInfo) 

    /**点击返回键**/
    public boolean clickBackKey(AccessibilityService service)

    /**点击Home键**/
    public boolean clickHomeKey(AccessibilityService service)

    /**点击最近任务**/
    public boolean clickLastTaskKey(AccessibilityService service) 

    /**点击通知栏**/
    public boolean clickNotificationKey(AccessibilityService service) 

    /***
     * 根据控件显示内容text找到的控件是否存在
     *
     * 界面中可能出现多个控件显示同样的内容,则根据text获取的控件不止一个
     * 这时,则需要控件id做辅助筛选,当无viewId做筛选条件时,默认取找到第一个含内容的view返回
     *
     * @param service AccessibilityService对象
     * @param text 视图文字
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     *
     * @return  true:该控件存在    false:该控件不存在
     */
    public boolean isExistViewByText(AccessibilityService service, String text,String viewId) 

    /***
     * 根据控件ViewId找到的控件是否存在
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所要寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param text 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     *
     * @return  true:该控件存在    false:该控件不存在
     */
    public boolean isExistViewById(AccessibilityService service, String viewId,String text)

    /***
     * 根据 EditText中的内容找到 EditText 对象,并改变EditText中的内容
     *
     * @param service AccessibilityService对象
     * @param viewText EditText原来显示的内容
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     * @param message  EditText中要设置的内容
     * @return
     */
    public boolean changeInputByViewText(AccessibilityService service, String viewText,String viewId,String message)

    /***
     * 根据 EditText中的ViewId找到 EditText 对象,并改变EditText中的内容
     *
     * @param service  AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param viewText 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     * @param message EditText中要设置的内容
     * @return
     */
    public boolean changeInputByViewId(AccessibilityService service, String viewId,String viewText,String message)

    /***
     * 根据控件上显示的文字找到该控件,并执行点击事件
     *
     * 注：若该控件不可点击,则找其父控件甚至父级的父级...来执行点击
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所啊哟寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param text 控件上显示的内容
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     *               当 viewId为null时,默认取找到第一个含内容的view作为查找的返回结果
     * @return
     */
    public boolean performClickByText(AccessibilityService service, String text,String viewId)

    /***
     * 根据控件ViewId找到该控件,并执行点击事件
     *
     * 注：若该控件不可点击,则找其父控件甚至父级的父级...来执行点击
     *
     * 当界面中是一个列表的时候,根据viewId查找可能会得到一个控件列表
     * 而所啊哟寻找的不一定是默认的第一项,这时则需要文字即text辅助查找
     *
     * @param service AccessibilityService对象
     * @param viewId 参数是 com.xxx.xxx:id/tv_main,必须要带上包名
     * @param text 控件上显示的内容,当text为null时,默认取根据id获取到的列表的第一个
     *
     * @return
     */
    public boolean performClickById(AccessibilityService service, String viewId,String text)

   /***
     * 手势滑动
     *
     * 注: 开始滑动时间与滑动延长时间参考值如下：
     *     startTime=100L,
     *     duration=500L
     *
     * @param service  AccessibilityService对象
     * @param startX 起始 x 坐标
     * @param startY 起始 Y 坐标
     * @param endX  结束 X 坐标
     * @param endY  结束 Y 坐标
     * @param startTime  滑动开始时间(单位毫秒)
     * @param duration  滑动持续时间(单位毫秒)
     * @param callback 监听
     * @return
     */
    public boolean performGestureSliding(AccessibilityService service,
                                         float startX,
                                         float startY,
                                         float endX,
                                         float endY,
                                         long startTime,
                                         long duration,
                                         AccessibilityService.GestureResultCallback callback) 

    /***
     * 手势执行点击事件
     *
     * 注: 开始点击时间与点击延长时间参考值如下：
     *     startTime=50L,
     *     duration=500L
     *
     * @param service  AccessibilityService对象
     * @param x 点击屏幕的 x 坐标
     * @param y 点击屏幕的 y 坐标
     * @param startTime  滑动开始时间(单位毫秒)
     * @param duration  滑动持续时间(单位毫秒)
     * @param callback 监听
     * @return
     */
    public boolean performClickByGesture(AccessibilityService service,
                                         float x, float y,
                                         long startTime, long duration,
                                         AccessibilityService.GestureResultCallback callback)

    /***
     * 线程休眠时间
     *
     * @param miao:double类型, 单位秒
     */
    public void waitTime(double miao) 

```
#### 二.如何最大限度的让界面某个操作生效
归根结底是要准确的找到界面上该控件，然后进行操作事件。由于一般app界面`ui`设计的复杂性，可能一个界面有很多控件，列表，弹窗等。这使得要准确寻找到界面上某个控件异常困难，控件找不到，就更别谈操作了。
那么为了最大程度的找到某个特定控件，我们可以遵循以下规则(以点击动作为例)：
- 能找到该控件的id的话，尽量用`performClickById(AccessibilityService service, String viewId,String text)`方法，若该控件有内容的话，也将内容作为参数传到该方法中
- 当控件没有`viewId`时，采用`performClickByText(AccessibilityService service, String text,String viewId)`实现点击动作
- 当要找的控件既没`viewId`又没`内容`时，采用`performClickByGesture(AccessibilityService service,
                                         float x, float y,
                                         long startTime, long duration,
                                         AccessibilityService.GestureResultCallback callback)`实现点击。

如何打开要测试的app?
请参考[Android实现打开第三方app](https://www.jianshu.com/p/1c5344d7eca6)
如何找到界面控件的`viewId`，`内容`或`界面坐标`等信息?
请参考[Android上DDMS的简单使用](https://www.jianshu.com/p/4115be69be7d)

#### 三.AccessibilityHelper在自定义AccessibilityService中的简单使用
以本项目打开另一个项目`kotlinTest`，并点击该项目中的测试按钮，使之显示文字为例。
在`MainActivity`中判断`无障碍服务MyService`是否开启,若未开启则跳到设置界面去开启：
```
       //服务未启动则去启动
       if(!MyService.isStart()){
           AppUtil.setSerivce(TempActivity.this);
       }
```
通过包名打开要测试的app:
```
    //打开第三方app
    var open:Boolean=AppUtil.openAppByPackageName(this@MainActivity,packageName)
```
在无障碍服务`MyService`中执行具体业务逻辑
```
/**
 * Title:无障碍服务
 *
 * description:
 * 这个服务是不需要你在activity里去开启的，属于系统级别辅助服务 需要在设置里去手动开启 和我们平常app里
 * 经常使用的service 是有很大不同的 非常特殊
 * 你可以在 \sdk\samples\android-23\legacy\ApiDemos 这样的目录下 找到这个工程 这个工程下面有一个accessibility
 * 包 里面有关于这个服务的demo 当然他们那个demo 非常复杂，但是信息量很大，有兴趣深入研究的同学可以多看demo
 * 我这里只实现最基本的功能 且没有做冗余和异常处理，只包含基础功能，不能作为实际业务上线！
 *
 * autor:pei
 * created on 2021/4/7
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class MyService extends AccessibilityService {

    public static final String TEST="com.kotlintest";

    public static MyService mService;

    private static int mCount=0;

    /***
     * AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，
     * 我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号
     * 做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.
     * 同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtil.i("====建立服务链接====");

    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LogUtil.i("====启动Event====");
        //屏幕尺寸
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int x = displayMetrics.widthPixels;
        int y = displayMetrics.heightPixels;
        LogUtil.i("======屏幕尺寸:x="+x+"  y="+y);

        mService=this;

        if (event.getPackageName() == null) {
            LogUtil.e("=======event.getPackageName()为空====");
            return;
        }
        String packageName = event.getPackageName().toString();
        if(StringUtil.isNotEmpty(packageName)){
            switch (packageName) {
                case TEST://测试demo
                    LogUtil.i("========测试demo=========");

                    doTask();
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onInterrupt() {
        LogUtil.i("====无障碍服务要结束了====");
        mService=null;

    }

    /**服务是否启动**/
    public static boolean isStart(){
        return mService!=null;
    }


    private void doTask(){
        //延时一秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.i("=========mCount="+mCount);
        switch (mCount) {
            case 1://点击按钮
                //点击测试按钮
                AccessibilityHelper.getInstance().performClickById(mService,"com.kotlintest:id/mBtnTest",null);
                break;
            default:
                break;
        }
        mCount++;

    }

}
```


