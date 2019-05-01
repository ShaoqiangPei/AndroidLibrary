### PermissionHelper使用说明
#### app权限申请工具类

#### 一.提前准备
在你的项目中出引用当前项目的依赖外，还要另外添加以下依赖：
```
implementation 'com.lovedise:permissiongen:0.1.1'
```
在你项目的 mainfast.xml中增加以下权限：
```
 <!--安装未知来源应用的权限 -->
 <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```

ok，接下来你才能开始使用PermissionHelper这个类的相关方法了。
#### 二.具体使用
##### 2.1 定义申请权限的常量
在你项目的常量类(假设你常量类名为Contents)中定义权限申请的常量：
```
public static final int PERMISSION_CODE=555;(值自己定义)
```
##### 2.2 具体操作
在你要申请权限的activity中添加以下代码
```
    @PermissionSuccess(requestCode = Contents.PERMISSION_CODE)
    public void requestSuccess() {
        //申请到权限后的处理
        //......

    }

    @PermissionFail(requestCode = Contents.PERMISSION_CODE)
    public void requestFail() {
        //未获取到权限的处理
        //......

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```
注意：此段代码只在activity中使用，在Fragment中不起作用。注解 @PermissionSuccess 和  @PermissionFail中涉及的requesCode对应的值即为你定义的
申请权限的值,如上面的 Constents.PERMISSION_CODE

假设你要申请文件读写权限，那么你需要先在你的 mainfast.xml中添加对应权限：
```
    <!--读写权限-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
然后你需要写一个类似下面的方法，在方法中定义读写权限：
```
  private void requestPermission(int requestCode) {
    String permissions[] = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    PermissionHelper.getInstance().checkPermissions(permissions, requestCode,MainActivity.this);
  }
```
然后在你申请权限的地方调用：
```
requestPermission(Constents.PERMISSION_CODE);
```
ok,以上即为权限申请调用类的基本使用。

