
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
在app_module对应的build.gradle里面具体引用如下：
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
### 使用说明
1. 
