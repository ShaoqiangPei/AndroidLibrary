package com.androidlibrary;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.android.commonlibrary.dialog_fragment.SyDialogHelper;
import com.android.commonlibrary.mvp_frame.AppActivity;
import com.android.commonlibrary.util.LogUtil;

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
//        String permissions[] = {
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        PermissionHelper.getInstance().checkPermissions(permissions, requestCode,MainActivity.this);
    }

//    @PermissionSuccess(requestCode =PERMISSION_CODE)
//    public void requestSuccess() {
//        //申请到权限后的处理
//        //......
//        LogUtil.i("=======获得权限======");
//    }
//
//    @PermissionFail(requestCode =PERMISSION_CODE)
//    public void requestFail() {
//        //未获取到权限的处理
//        //......
//        LogUtil.i("=======没有权限======");
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


    private void test() {


    }


}


