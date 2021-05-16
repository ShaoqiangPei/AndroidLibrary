package com.androidlibrary;

import android.Manifest;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.android.commonlibrary.dialog_fragment.SyDialogHelper;
import com.android.commonlibrary.mvp_frame.AppActivity;
import com.android.commonlibrary.permission.PermissionHelper;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.encrypt.AES;
import com.android.commonlibrary.util.encrypt.Aes256;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppActivity {

    private static final int PERMISSION_CODE=555;

    private Button mBtnTest;
    private TextView mTvTest;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mBtnTest=getView(R.id.button);
        mTvTest=getView(R.id.textView);

        //申请权限
        requestPermission(PERMISSION_CODE);
    }

    @Override
    public void setListener() {
        mBtnTest.setOnClickListener(this);

        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("=====自动点击执行了=====");
                SyDialogHelper.showDialogOneBtn("xiao学",mContext);
                LogUtil.i("=====自动点击执行完毕=====");
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.button:
                test();
                break;
            default:
                break;
         }
    }

    private void requestPermission(int requestCode) {
        String permissions[] = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionHelper.getInstance().checkPermissions(permissions, requestCode,MainActivity.this);
    }

    @PermissionSuccess(requestCode =PERMISSION_CODE)
    public void requestSuccess() {
        //申请到权限后的处理
        //......
        LogUtil.i("=======获得权限======");
    }

    @PermissionFail(requestCode =PERMISSION_CODE)
    public void requestFail() {
        //未获取到权限的处理
        //......
        LogUtil.i("=======没有权限======");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void test() {
        String message = "中国";
        String key = "46cc793c53dc451b";
        String iv="f%Z4F+qtFh624970";

        LogUtil.i("======Aes256加密流程======");
        //Aes256加密初始化
        Aes256.getInstance()
                //设置加密模式,一般使用默认,除非对填充模式有特殊要求才设置(不设置的话默认为AES.CBC_PKCS5PADDING)
                .setFillModel(AES.CBC_PKCS5PADDING)

                .setCharsetName(AES.UTF_8) //设置字符集(不设置的话默认为AES.UTF_8)
                .init(key,iv);//设置私钥,向量(偏移量)
        //Aes256加密
        String enCode256= Aes256.getInstance().encrypt(message);
        LogUtil.i("======Aes256加密结果: enCode256="+enCode256);
        //Aes256解密
        String deCode256= Aes256.getInstance().decrypt(enCode256);
        LogUtil.i("======Aes256解密结果: deCode256="+deCode256);


//        LogUtil.i("======Aes128加密流程======");
//        //Aes128加密初始化
//        Aes128.getInstance()
//                .setFillModel(AES.ECB_PKCS5PADDING) //设置加密模式(不设置的话默认为AES.ECB_PKCS5PADDING)
//                .setCharsetName(AES.UTF_8) //设置字符集(不设置的话默认为AES.UTF_8)
//                .init(key); //设置私钥
//        //Aes128加密
//        String enCode128= Aes128.getInstance().encrypt(message);
//        LogUtil.i("======Aes128加密结果: enCode128="+enCode128);
//        //Aes128解密
//        String deCode128= Aes128.getInstance().decrypt(enCode128);
//        LogUtil.i("======Aes128解密结果: deCode128="+deCode128);

    }


}


