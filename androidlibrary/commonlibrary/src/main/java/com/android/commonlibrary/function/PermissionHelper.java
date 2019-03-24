package com.android.commonlibrary.function;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.commonlibrary.util.LogUtil;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Description:app权限申请工具类
 *
 * 注意：此类时是针对在 activity 中进行权限申请的，只在 activity中调用生效
 *      若在 fragment 中调用则不起作用
 *
 * 在 PermissionHelper调用申请权限的同时还在要 mainfast.xml 中添加相应权限
 * 另外，在 mainfast 中需要另外添加安装未知文件来源的权限：
 *  <!--安装未知来源应用的权限 -->
 *  <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
 *
 * Created by pei
 * Date: 2017/9/20
 */
public class PermissionHelper {

    private static final int HANDLER_TAG=123;

    private Context mContext;
    private String mPermissions[];

    private Handler mHandler=new PermissionHandler(PermissionHelper.this);

    private PermissionHelper() {
    }

    private static class Holder {
        private static PermissionHelper instance = new PermissionHelper();
    }

    public static PermissionHelper getInstance() {
        return Holder.instance;
    }

    /**
     * 加基本权限
     **/
    public void checkPermissions(String permissions[], int requestCode,Context context) {
        this.mPermissions=permissions;
        this.mContext=context;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=mHandler.obtainMessage(HANDLER_TAG,requestCode);
                mHandler.sendMessage(message);
            }
        }).start();
    }

    private void requestPermissions(int requestCode) {
        PermissionGen.with((Activity) mContext)
                .addRequestCode(requestCode)
                .permissions(mPermissions)
                .request();
    }

    /**
     * 重写activity的onRequestPermissionsResult方法，并在里面调用此方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PermissionGen.onRequestPermissionsResult((Activity)mContext, requestCode, permissions, grantResults);
    }

    /**自定义handler类**/
    static class PermissionHandler extends Handler {
        //弱引用(引用外部类)
        WeakReference<PermissionHelper> mCls;

        PermissionHandler(PermissionHelper cls) {
            //构造弱引用
            mCls = new WeakReference<PermissionHelper>(cls);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //通过弱引用获取外部类.
            PermissionHelper cls = mCls.get();
            //进行非空再操作
            if (cls != null) {
                switch (msg.what){
                    case HANDLER_TAG:
                        int requestCode= (int) msg.obj;
                        cls.requestPermissions(requestCode);
                        removeMessages(HANDLER_TAG);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}

//============在activity中调用示例===============
//     以下为申请读写和网络权限的例子,将以下部分复制到自己的项目中,然后在需要申请权限的地方
//     调用 requestPermission() 方法,在requestSuccess()和requestFail()中做申请成功或失败的处理
//
//    需要在你的app_module对应的build.gradle中添加第三方库引用:implementation 'com.lovedise:permissiongen:0.0.6'
//    然后在你的activity中定义一个申请权限的常量： private static final int PERMISSION_CODE=555;(值自己定义)
//    最后在你需要申请权限的地方调用:requestPermission(PERMISSION_CODE) 方法,在requestSuccess()和requestFail()中做申请成功或失败的处理
//    注意 ：@PermissionSuccess 和 @PermissionFail 中的 code 为你定义的 code 的值
//    此权限申请只能在activity中用，fragment中不能使用。
//
//  private void requestPermission(int requestCode) {
//    String permissions[] = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE};
//    PermissionHelper.getInstance().checkPermissions(permissions, requestCode,MainActivity.this);
//}
//
//    @PermissionSuccess(requestCode = MainActivity.PERMISSION_CODE)
//    public void requestSuccess() {
//        //申请到权限后的处理
//        //......
//
//    }
//
//    @PermissionFail(requestCode = MainActivity.PERMISSION_CODE)
//    public void requestFail() {
//        //未获取到权限的处理
//        //......
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }