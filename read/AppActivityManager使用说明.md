## AppActivityManager使用说明

### 说明
AppActivityManager类提供对于整个app中Activity的管理,方便控制activity的打开和关闭流程。

### AppActivityManager的使用简介
#### 1. 初始化
AppActivityManager为单例类，其初始化如下：
```
AppActivityManager appActivityManager=AppActivityManager.getInstance();
```
#### 2. 添加Activity到堆栈
```
addActivity(Activity activity)
```
#### 3. 获取app中Activity打开数目,默认返回值为 0
```
getActivitySize()
```
#### 4. 获取当前Activity（堆栈中最后一个压入的）,默认返回为 null
```
getCurrentActivity()
```
#### 5. 结束当前Activity（堆栈中最后一个压入的）
```
finishActivity()
```
#### 6. 结束指定的Activity
```
finishActivity(Activity activity)
```
#### 7. 结束指定类名的Activity
```
finishActivity(Class<?> cls)
```
#### 8. 结束指定类名以外的Activity
```
finishOtherActivity(Class<?> cls)
```
#### 9. 结束所有Activity
```
finishAllActivity()
```
#### 10. 退出应用程序
```
appExit() 
```
#### 11. 判断当前activity是否存在
```
isCurActivityExists(Class<?> cls) 
```
#### 12. 判断应用是否已经启动
```
isAppAlive(Context context)
```
