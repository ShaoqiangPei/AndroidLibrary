package com.androidlibrary;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.android.commonlibrary.dialog_fragment.SyDialogHelper;
import com.android.commonlibrary.mvp_frame.AppActivity;
import com.android.commonlibrary.permission.PermissionHelper;
import com.android.commonlibrary.util.AgreementDefaultHelper;
import com.android.commonlibrary.util.LogUtil;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

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
        SyDialogHelper.showDialogOneBtn("大家好",mContext);

//        AgreementDefaultHelper.showDefaultAgreementDialog(mContext, "测试app",
//                R.color.red, null,
//                R.color.colorAccent,R.color.green,
//                //跳转用户协议界面的监听
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====跳转用户协议=======");
//                    }
//                },
//                //跳转隐私协议界面的监听
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====跳转隐私协议=======");
//                    }
//                },
//                //取消按钮监听,一般执行退出app的操作
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====退出app=======");
//                    }
//                },
//                //确定按钮的操作,一般处理进入app的流程
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LogUtil.i("====进入app=======");
//                    }
//                });
    }

}


