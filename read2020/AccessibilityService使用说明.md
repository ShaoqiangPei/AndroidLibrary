
## AccessibilityService使用说明

### 概述
官网的介绍:`无障碍服务应该只用于帮助残疾用户使用Android设备和应用程序。它们在后台运行，并在AccessibilityEvents被触发时接收系统的回调。
这类事件表示用户界面中的某些状态转换，例如焦点发生了变化、按钮被单击等等。这样的服务可以请求查询活动窗口内容的功能。可访问性服务的开发需要扩展这个类并实现它的抽象方法`
是一个比较特殊的服务，可在后台监听监听手机的各种状态,可以自动控制其他app的各种事件，如：点击，滑动，输入等.一般可用来做自动化测试。

### 使用说明
#### 一.AccessibilityService实现步骤
##### 1.1  继承AccessibilityService写一个自定义无障碍服务TestService
当我们需要写一个无障碍服务时，需要继承`AccessibilityService`，然后重写`AccessibilityService`的三个方法：`onServiceConnected()`，`onAccessibilityEvent(event: AccessibilityEvent?)`
和`onInterrupt()`。
- onServiceConnected()：AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
- onAccessibilityEvent(event: AccessibilityEvent?)：一旦无障碍服务开启，此方法会不断的重复执行，我们要做的一些操作及业务逻辑主要在此方法中执行
- onInterrupt()：无障碍服务退出时会调用此方法，我们可以在此方法中做些销毁和释放资源的操作

下面以继承`AccessibilityService`写一个简单的自定义无障碍服务的示例(假设该服务命名为`TestService`)：
```
/**
 * Title: 无障碍服务
 *
 * description:
 * 这个服务是不需要你在activity里去开启的，属于系统级别辅助服务 需要在设置里去手动开启 和我们平常app里
 * 经常使用的service 是有很大不同的 非常特殊
 * 你可以在 \sdk\samples\android-23\legacy\ApiDemos 这样的目录下 找到这个工程 这个工程下面有一个accessibility
 * 包 里面有关于这个服务的demo 当然他们那个demo 非常复杂，但是信息量很大，有兴趣深入研究的同学可以多看demo
 * 我这里只实现最基本的功能 且没有做冗余和异常处理，只包含基础功能，不能作为实际业务上线！
 *
 * autor:pei
 * created on 2021/4/10
 */
@RequiresApi(Build.VERSION_CODES.N)
open class TestService: AccessibilityService(){

    companion object{
        var mService:DigService?=null

        /**服务是否启动**/
        fun isStart():Boolean = mService!=null

        /**销毁**/
        fun destory(): Boolean{
            if (isStart()) {
                mService!!.disableSelf()
                mService=null
                return true
            }
            return false
        }
    }


    /***
     * AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，
     * 我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号
     * 做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.
     * 同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        LogUtil.i("======DigService建立连接成功========")

        //屏幕宽高(w=1080  h=1792)
        LogUtil.i("===屏幕尺寸: w = ${ScreenUtil.getWidth()}  h=${ScreenUtil.getHeight()}")

        mService=this@DigService
        //数据初始化
        //......

    }

    /**此方法会重负执行**/
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        LogUtil.i("==========onAccessibilityEvent执行=========")

        if(event==null||event!!.packageName.isNullOrBlank()){
            LogUtil.e("=======event.getPackageName()为空====")
            return
        }
        var packageName:String? = event!!.packageName.toString()
        if(packageName.isNullOrBlank()){
            LogUtil.e("=======packageName为空====")
            return
        }
        var eventType:Int=event.eventType
        LogUtil.i("========eventType=$eventType   event.text=${event.text}")

       //处理具体的业务逻辑
       //......


    }

    override fun onInterrupt() {
        LogUtil.i("====无障碍服务要结束了====")
        mService=null
        //做一些清除和释放资源的操作
        //......

    }


}
```
##### 1.2 声明无障碍服务
声明无障碍服务的方式又两种，一种是代码声明，一种是`androidmanifast.xml`中声明。
在代码中声明时，我们需要把这些配置代码写在`无障碍`服务的`onServiceConnected()`方法中：，类似下面这样：
```
    /***
     * AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，
     * 我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号
     * 做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.
     * 同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        LogUtil.i("======DigService建立连接成功========")

        var serviceInfo:AccessibilityServiceInfo=AccessibilityServiceInfo()
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        serviceInfo.packageNames = arrayOf<String>("com.tencent.mm")
        serviceInfo.notificationTimeout=100
        setServiceInfo(serviceInfo)

        //其他代码省略
        //......

    }
```
当然，我们也可以在`Androidmanifast.xml`中声明：
```
        <service
            android:name=".service.TestService"
            android:enabled="true"
            android:exported="true"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService"/>
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/test_file"/>
        </service>
```
这里涉及到一个配置文件`test_file`，则我们要在`res/xml/`文件夹下建一个`test_file.xml`文件(若`res/`下面没有`xml`文件夹则自己新建一个)。下面贴出`test_file.xml`代码：
```
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFeedbackType="feedbackAllMask"
    android:canRetrieveWindowContent="true"
    android:canPerformGestures="true"
    android:canRequestTouchExplorationMode="true"
    android:accessibilityFlags="flagDefault|flagRetrieveInteractiveWindows|flagIncludeNotImportantViews|flagReportViewIds"
    android:canRequestFilterKeyEvents="true"
    android:description="@string/app_name"
    android:notificationTimeout="100"/>
```
以上`xml`文件中各种配置，大家可以看[官网](https://developer.android.google.cn/reference/android/R.styleable?hl=en#AccessibilityService)介绍，这里就不多描述了。
至此，一个基本的无障碍服务就实现了。我们可以在`TestService`的`onAccessibilityEvent(event: AccessibilityEvent?)`中去处理各种业务逻辑。当然,也能在此方法中监听各种状态(`event.eventType`)，状态有很多，包括以下：
|事件类型  |  描述
|  ----  | ----  |
| TYPE_VIEW_CLICKED  |   View被点击| 
| TYPE_VIEW_LONG_CLICKED  |   View被长按| 
| TYPE_VIEW_SELECTED |    View被选中| 
| TYPE_VIEW_FOCUSED |    View获得焦点| 
| TYPE_VIEW_TEXT_CHANGED  |   View文本变化| 
| TYPE_WINDOW_STATE_CHANGED |    打开了一个PopupWindow，Menu或Dialog| 
| TYPE_NOTIFICATION_STATE_CHANGED |    Notification变化| 
| TYPE_VIEW_HOVER_ENTER |    一个View进入悬停| 
| TYPE_VIEW_HOVER_EXIT   |  一个View退出悬停| 
| TYPE_TOUCH_EXPLORATION_GESTURE_START |    触摸浏览事件开始| 
| TYPE_TOUCH_EXPLORATION_GESTURE_END |    触摸浏览事件完成| 
| TYPE_WINDOW_CONTENT_CHANGED |    窗口的内容发生变化，或子树根布局发生变化| 
| TYPE_VIEW_SCROLLED   |  View滚动| 
| TYPE_VIEW_TEXT_SELECTION_CHANGED  |   Edittext文字选中发生改变事件| 
| TYPE_ANNOUNCEMENT |    应用产生一个通知事件| 
| TYPE_VIEW_ACCESSIBILITY_FOCUSED  |   获得无障碍焦点事件| 
| TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED |    无障碍焦点事件清除| 
| TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY   |  在给定的移动粒度下遍历视图文本的事件| 
| TYPE_GESTURE_DETECTION_START   |  开始手势监测| 
| TYPE_GESTURE_DETECTION_END  |   结束手势监测| 
| TYPE_TOUCH_INTERACTION_START |    触摸屏幕事件开始| 
| TYPE_TOUCH_INTERACTION_END |    触摸屏幕事件结束| 
| TYPE_WINDOWS_CHANGED |    屏幕上的窗口变化事件，需要API 21+| 
| TYPE_VIEW_CONTEXT_CLICKED  |   View中的上下文点击事件| 
| TYPE_ASSIST_READING_CONTEXT  |   辅助用户读取当前屏幕事件| 
更多大家可参考[官网](https://developer.android.google.cn/reference/android/accessibilityservice/AccessibilityService?hl=en)
#### 二.AccessibilityService的一些方法
下面介绍下`AccessibilityService`中使用到的一些常用方法，用以协助实现界面的`点击`,`滑动`和`输入`等操作。
|方法 |  作用|
|  ----  | ----  |
| disableSelf()	| 禁用当前服务,也就是在服务可以通过该方法停止运行| 
| findFoucs(int falg) | 查找拥有特定焦点类型的控件| 
| getRootInActiveWindow() | 如果配置能够获取窗口内容,则会返回当前活动窗口的根结点| 
| getSeviceInfo()| 	获取当前服务的配置信息| 
| onAccessibilityEvent(AccessibilityEvent event)	| 有关AccessibilityEvent事件的回调函数.系统通过sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处| 
| performGlobalAction(int action)| 	执行全局操作,比如返回,回到主页,打开最近等操作| 
| setServiceInfo(AccessibilityServiceInfo info)	| 设置当前服务的配置信息| 
| getSystemService(String name)| 	获取系统服务| 
| onKeyEvent(KeyEvent event)	| 如果允许服务监听按键操作,该方法是按键事件的回调,需要注意,这个过程发生了系统处理按键事件之前| 
| onServiceConnected()	| 系统成功绑定该服务时被触发,也就是当你在设置中开启相应的服务,系统成功的绑定了该服务时会触发,通常我们可以在这里做一些初始化操作| 
| dispatchGesture | 用以实现手势操作 |
| accessibilityNodeInfo.performAction(int action)| 用以实现按钮点击等操作 |
| accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(String viewId)| 根据控件id找控件 列表|
| accessibilityNodeInfo.findAccessibilityNodeInfosByText(String text)| 根据控件上显示的内容找控件列表 |
这里急需注意的是` accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(String viewId)`和`accessibilityNodeInfo.findAccessibilityNodeInfosByText(String text)`。以
`accessibilityNodeInfo.findAccessibilityNodeInfosByText(String text)`为例，其官方说明如下：
`通过文本查找AccessibilityNodeInfos。匹配的是不区分大小写的包含。搜索是相对于这个信息的，也就是说，这个信息是遍历树的根。`
即其遍历的是以当前视图为基准下的所有`AccessibilityNodeInfo`列表。是遍历的所有节点哦。这就为我们找界面上具体的控件提供了`理论基础`。
#### 三. AccessibilityService的开启运行
最后，看看如何开启一个无障碍服务。
假设我们写的一个无障碍服务项目为`TestDemo`，其中的无障碍服务叫`TestService`,那么要启动此项目中的`TestService`,我们无需像一般的服务一样去`startService`,而是在鲜项目中判断`TestService`是否已启动，若未启动则跳转到手机的设置界面，然后 `设置 ---> 智能辅助 --->  无障碍 --->  在服务列表中找到你的项目名称"TestDemo" --->  点击进去，然后开启开关`，这样我们的`TestService`就开启了。

#### 四.无障碍操作帮助类
为了快速实现对界面控件的操作(`点击`,`输入`和`滑动`等),我们可以借助以下两个类来实现我们自动化测试的进程：
- AccessibilityHelper
- GestureHelper

