[![](https://jitpack.io/v/ShaoqiangPei/AndroidLibrary.svg)](https://jitpack.io/#ShaoqiangPei/AndroidLibrary)

### 库引用说明
在自己项目的project对应的build.gradle里面添加如下代码：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在你项目的app_module对应的build.gradle里面引用此库,如下：
```
  dependencies {
	        implementation 'com.github.ShaoqiangPei:AndroidLibrary:Tag'
	}
```
1.1.0以下版本(以1.0.0版为例),则在app_module对应的build.gradle里面具体引用如下：
```
  dependencies {
	        implementation 'com.github.ShaoqiangPei:AndroidLibrary:v1.0.0'
	}
```
1.1.0及以上版本(以1.1.0版为例),则在app_module对应的build.gradle里面具体引用如下：
```
  dependencies {
	        implementation 'com.github.ShaoqiangPei:AndroidLibrary:1.1.0'
	}
```
在你的项目中自定义一个Application继承于ComContext,类似如下：
```
public class AppContext extends ComContext {

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
```
在你项目的mainfast.xml中声明自己的application，类似如下：
```
 <application
        android:name=".AppContext"//声明自己的Application
	//以下省略
        //......
        >
    //此处省略
    //......

  </application>
```
### 使用说明索引
#### 一. 启动页工具类  
[LaunchUtil](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/LaunchUtil%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
)
#### 二. 用户权限申请  
[PermissionHelper](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/PermissionHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
)
#### 三. Util工具  
[SpUtil](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/SpUtil%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)                  ————— SharedPreferences存储类  
AppUtil ————— app设备信息相关类  
CollectionUtil ————— 集合处理工具 
[CompareSortor](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/CompareSortor%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)————— List集合排列工具类 
DateUtil ————— 日期相关工具处理类  
DoubleClickUtil ————— 防双击，防抖功能类  
FileUtil ————— 文件工具类  
FlyAnimtor ————— 物品飞入购物车效果工具类(贝塞尔曲线)  
FormatUtil ————— 数字给格式化工具类  
GsonUtil ————— gson解析工具类  
KeyboardUtil ————— 软键盘控制工具类  
LogUtil ————— log打印工具类  
MainfastUtil ————— mainfast信息获取工具类  
MD5Util ————— MD5工具类  
NetUtil ————— 网络工具类  
RegularUtil ————— 正则表达式工具类  
ScreenUtil ————— 屏幕工具类  
SDCardUtil ————— 内存卡工具类  
ShotShareUtil ————— 截屏分享工具类  
SpannableStringUtil ————— 设置文本工具类  
StatusBarUtil ————— 状态类工具类  
StringUtil ————— 字符串处理工具类  
ToastUtil ————— 吐司工具类  
UriUtil ————— uri转换工具类  
VibratorUtil ————— 手机震动工具类  
ViewUtil ————— 控件相关工具类  
WakeUpUtil ————— 屏幕唤醒工具类  
#### 四. 可序列化基类
[BaseEntity](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/BaseEntity%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)
#### 五. 缓存
[Cache](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/Cache%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ————— app内存缓存  
[SimpleCache](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/SimpleCache%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ————— 文件式缓存 
#### 六. app辅助类
[AppActivityManager](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivityManager%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
) ————— App中Activity管理类  
[AppHelper](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
) ————— Activity基类帮助类  
[IntentHelper](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/IntentHelper%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
) ————— Activity跳转帮助类(包含activity间的各种跳转和传值)  
#### 七. Activity & Fragment
[AppActivity](https://github.com/ShaoqiangPei/AndroidLibrary/blob/master/read/AppActivity%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md
)————— Activity基类




