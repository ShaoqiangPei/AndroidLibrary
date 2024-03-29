## PermissionHelper2使用说明

### app权限申请工具类
`PermissionHelper2`用于便捷开发者动态申请app权限，其基于 `RxJava3`,`RxAndroid3`和`RxPermissions`.

#### 一.提前准备
在你项目的 mainfast.xml中增加以下权限：
```
 <!--安装未知来源应用的权限 -->
 <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```
为了兼容`Android10`权限申请，需要在`Manifast.xml`文件的自定义`application`标签中添加如下属性：

```
    <application
        android:requestLegacyExternalStorage="true" //添加此属性兼容Android10
        //其他代码省略
       //......
         >
        <activity
           .../>
           ......
      </application>
```
#### 二. PermissionHelper2简单介绍
`PermissionHelper2`是一个单例类，其初始化如下：
```
PermissionHelper2.getInstance()
```
此类涉及以下常用方法：
```
    /***
     * 申请单个权限
     *
     * 注: 同意后再申请此权限则不再弹出提示框
     *
     * @param permission：权限，如 Manifest.permission.READ_EXTERNAL_STORAGE
     * @param activityOrFragment：FragmentActivity或 Fragment实例
     * @return access: true:申请成功  fasle:申请失败
     */
    public void request(String permission, ViewModelStoreOwner activityOrFragment, IPermissionResultListener listener) ;

    /***
     * 申请单个权限,获得详细信息
     *
     * @param permission 权限，如 Manifest.permission.READ_EXTERNAL_STORAGE
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestInfo(String permission, ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener)

    /***
     * 申请多个权限(返回一个permission对象)
     *
     * 注: 只要有一个禁止，则返回false；全部同意，则返回true
     *     某个权限同意后，之后再申请此权限则不再弹出提示框，其他的会继续弹出
     *
     * @param permissions 权限数组
     * @param activityOrFragment  FragmentActivity或 Fragment实例
     * @return access: true:申请成功  fasle:申请失败
     */
    public void requestEach(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultListener listener)

    /***
     * 申请多个权限,获得详细信息(返回多个permission对象)
     *
     * @param permissions 权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestEachInfo(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener)

    /***
     * 申请多个权限，获取合并后的详细信息
     *
     * @param permissions 权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestEachCombinedInfo(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener)

    /**
     * 检查某个权限是否被申请
     *
     * @param permisson          权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     * @return true:有该权限  fasle:无该权限
     */
    public boolean checkPermission(String permisson, ViewModelStoreOwner activityOrFragment)

```
#### 三. PermissionHelper2在activity中示例
下面以拍照和文件读写权限申请为例，再添加以上所述之外，`Manifast.xml`中需要追加以下三个权限：

```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CAMERA" />
```
下面给出`PermissionHelper2`在`MainActivity`中调用代码：

```
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.tbruyelle.rxpermissions3.Permission;
import com.testdemo.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    private Button mBtn;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        mTv = findViewById(R.id.tv_text);
        mBtn = findViewById(R.id.btn_test);
        mBtn2 = findViewById(R.id.btn_test2);
    }

    private void initData() {

    }

    private void setListener() {
        mBtn.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                //申请单个权限
                String permission = Manifest.permission.CAMERA;
                PermissionHelper2.getInstance().requestInfo(permission, MainActivity.this, new PermissionHelper2.IPermissionResultInfoListener() {
                    @Override
                    public void checkPermission(Permission permission) {
                        if (permission.granted) {//All权限同意后调用
                            Util.i("======权限都通过=======");
                            toast("====权限申请成功===");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                            Util.i(" 拒绝权限，并没有勾选‘不再询问’,下次点击会继续弹出提示=");
                            toast("====权限申请失败===");
                        } else {
                            // but有个问题：去设置页面手动开启权限后回到app，回调不能自动执行允许权限的代码，
                            // 即没办法自动进入App,需要重启App才正常，是否可以在onResume()里处理？
                            Util.i("======弹框去设置界面=======");
                            showAlertDialog("权限申请",
                                    "需要获取手机相关权限,是否去设置界面?",
                                    "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //去设置界面
                                            goToSetting(MainActivity.this);
                                        }
                                    },
                                    "取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Util.i("========取消了权限======");
                                        }
                                    });
                        }
                    }
                });
                break;
            case R.id.btn_test2:
                //申请一组权限
                String permissons[] = {Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                PermissionHelper2.getInstance().requestEachCombinedInfo(permissons, MainActivity.this, new PermissionHelper2.IPermissionResultInfoListener() {
                    @Override
                    public void checkPermission(Permission permission) {
                        if (permission.granted) {//All权限同意后调用
                            Util.i("======所有权限申请成功=======");
                            toast("====所有权限申请成功===");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                            Util.i(" 拒绝权限，并没有勾选‘不再询问’,下次点击会继续弹出提示=");
                            toast("====部分权限申请失败===");
                        } else {
                            // but有个问题：去设置页面手动开启权限后回到app，回调不能自动执行允许权限的代码，
                            // 即没办法自动进入App,需要重启App才正常，是否可以在onResume()里处理？
                            Util.i("======弹框去设置界面=======");
                            showAlertDialog("权限申请",
                                    "需要获取手机相关权限,是否去设置界面?",
                                    "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //去设置界面
                                            goToSetting(MainActivity.this);
                                        }
                                    },
                                    "取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Util.i("========取消了权限======");
                                        }
                                    });
                        }
                    }
                });
                break;
            default:
                break;
        }
    }


    /**
     * 含有标题、内容、两个按钮的对话框
     **/
    protected AlertDialog showAlertDialog(String title, String message, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener).setNegativeButton(negativeText, onNegativeClickListener).show();
        return alertDialog;
    }

    protected void toast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context) {
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}
```
