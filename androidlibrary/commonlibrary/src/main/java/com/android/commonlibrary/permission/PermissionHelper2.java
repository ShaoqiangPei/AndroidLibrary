package com.android.commonlibrary.permission;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.commonlibrary.util.CollectionUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Title: app权限申请工具类
 * <p>
 * 在 PermissionHelper调用申请权限的同时还在要 mainfast.xml 中添加相应权限
 * 另外，在 mainfast 中需要另外添加安装未知文件来源的权限：
 * <!--安装未知来源应用的权限 -->
 * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
 * 在application中添加该句
 * android:requestLegacyExternalStorage="true"
 * <p>
 * autor:pei
 * created on 2022/6/15
 */
public class PermissionHelper2 {

    private PermissionHelper2() {
    }

    private static class Holder {
        private static PermissionHelper2 instance = new PermissionHelper2();
    }

    public static PermissionHelper2 getInstance() {
        return Holder.instance;
    }

    /***
     * 申请单个权限
     *
     * 注: 同意后再申请此权限则不再弹出提示框
     *
     * @param permission：权限，如 Manifest.permission.READ_EXTERNAL_STORAGE
     * @param activityOrFragment：FragmentActivity或 Fragment实例
     * @return access: true:申请成功  fasle:申请失败
     */
    public void request(String permission, ViewModelStoreOwner activityOrFragment, IPermissionResultListener listener) {
        //校验参数
        if (StringUtil.isEmpty(permission)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        //申请权限
        RxPermissions permissions = getRxPermissions(activityOrFragment);
        //permissions.setLogging(true);
        permissions.request(permission)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean access) throws Exception {
                        if (listener != null) {
                            listener.checkPermission(permission, access);
                        }
                    }
                });
    }

    /***
     * 申请单个权限,获得详细信息
     *
     * @param permission 权限，如 Manifest.permission.READ_EXTERNAL_STORAGE
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestInfo(String permission, ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener) {
        //校验参数
        if (StringUtil.isEmpty(permission)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        //申请权限
        RxPermissions permissions = getRxPermissions(activityOrFragment);
        //permissions.setLogging(true);
        permissions.requestEach(permission)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission p) throws Exception {
                        LogUtil.i("=====requestInfo=========permission.name=" + p.name);

//                        if (p.name.equalsIgnoreCase(permission)) {
//                            if (p.granted) {//同意后调用
//
//                            } else if (p.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
//
//                            } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
//
//                            }
//                        }

                        if (listener != null) {
                            listener.checkPermission(p);
                        }
                    }
                });
    }

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
    public void requestEach(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultListener listener) {
        if (CollectionUtil.isEmpty(permissions)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        RxPermissions rxPermissions = getRxPermissions(activityOrFragment);
        //permissions.setLogging(true);
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean access) throws Exception {
                        if (listener != null) {
                            listener.checkPermission(null, access);
                        }
                    }
                });
    }

    /***
     * 申请多个权限,获得详细信息(返回多个permission对象)
     *
     * @param permissions 权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestEachInfo(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener) {
        if (CollectionUtil.isEmpty(permissions)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        RxPermissions rxPermissions = getRxPermissions(activityOrFragment);
        rxPermissions.requestEach(permissions)
                .subscribe(p -> {
                    String permissionName = p.name;

//                    //列举两个示例
//                    if (p.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                        if (p.granted) {//同意后调用
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + true);
//                        } else if (p.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
//                        } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + false);
//                        }
//                    }
//                    if (p.name.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        if (p.granted) {
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + true);
//                        } else if (p.shouldShowRequestPermissionRationale) {
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
//                        } else {
//                            LogUtils.error(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + false);
//                        }
//                    }

                    if (listener != null) {
                        listener.checkPermission(p);
                    }
                });
    }

    /***
     * 申请多个权限，获取合并后的详细信息
     *
     * @param permissions 权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     */
    public void requestEachCombinedInfo(String permissions[], ViewModelStoreOwner activityOrFragment, IPermissionResultInfoListener listener) {
        if (CollectionUtil.isEmpty(permissions)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        RxPermissions rxPermissions = getRxPermissions(activityOrFragment);
        //rxPermissions.setLogging(true);
        rxPermissions.requestEachCombined(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        String permissionName = permission.name;
                        LogUtil.i("======requestEachCombinedInfo====permissionName=" + permissionName);

//                        if (permission.granted) {//全部同意后调用
//
//                        } else if (permission.shouldShowRequestPermissionRationale) {//只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
//
//                        } else {//只要有一个选择：禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
//
//                        }
                        if (listener != null) {
                            listener.checkPermission(permission);
                        }
                    }
                });
    }

    /**
     * 检查某个权限是否被申请
     *
     * @param permisson          权限数组
     * @param activityOrFragment FragmentActivity或 Fragment实例
     * @return true:有该权限  fasle:无该权限
     */
    public boolean checkPermission(String permisson, ViewModelStoreOwner activityOrFragment) {
        //校验参数
        if (StringUtil.isEmpty(permisson)) {
            throw new NullPointerException("====权限数组不能为空=====");
        } else if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        //申请权限
        RxPermissions rxPermissions = getRxPermissions(activityOrFragment);
        //permissions.setLogging(true);
        return rxPermissions.isGranted(permisson);
    }

    /**
     * 获取RxPermissions实例
     **/
    private RxPermissions getRxPermissions(ViewModelStoreOwner activityOrFragment) {
        if (null == activityOrFragment) {
            throw new NullPointerException("====activity参数不能为空=====");
        }
        if (activityOrFragment instanceof FragmentActivity) {
            return new RxPermissions((FragmentActivity) activityOrFragment);
        } else if (activityOrFragment instanceof Fragment) {
            return new RxPermissions((Fragment) activityOrFragment);
        } else {
            throw new NullPointerException("====RxPermissions初始化失败,检查activityOrFragment类型=====");
        }
    }

    /**
     * 权限结果监听
     **/
    public interface IPermissionResultListener {
        void checkPermission(String permission, boolean access);
    }

    /**
     * 权限结果监听
     **/
    public interface IPermissionResultInfoListener {
        void checkPermission(Permission permission);
    }

//    //================使用范例=============================
//    //批量申请权限的处理
//    String permissons[] = {Manifest.permission.CAMERA,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            PermissionHelper2.getInstance().
//
//    requestEachCombinedInfo(permissons, MainActivity .this, new PermissionHelper2.IPermissionResultInfoListener() {
//        @Override
//        public void checkPermission (Permission permission){
//            if (permission.granted) {//All权限同意后调用
//                LogUtil.i("======权限都通过=======");
//            } else if (permission.shouldShowRequestPermissionRationale) {
//                //只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
//                LogUtil.i(" 拒绝权限，并没有勾选‘不再询问’,下次点击会继续弹出提示=");
//            } else {
//                // but有个问题：去设置页面手动开启权限后回到app，回调不能自动执行允许权限的代码，
//                // 即没办法自动进入App,需要重启App才正常，是否可以在onResume()里处理？
//                LogUtil.i("======弹框去设置界面=======");
//            }
//        }
//    });

}
