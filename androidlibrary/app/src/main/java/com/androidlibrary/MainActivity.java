package com.androidlibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.android.commonlibrary.dialog_fragment.SyDialogHelper;
import com.android.commonlibrary.mvp_frame.AppActivity;
import com.android.commonlibrary.permission.PermissionHelper;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.RandomUtil;
import com.android.commonlibrary.util.StringUtil;
import com.android.commonlibrary.util.TimerManager;
import com.android.commonlibrary.util.view.ViewUtil;
import com.android.commonlibrary.widget.TitleBar2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppActivity {

    private static final int PERMISSION_CODE=555;

    private Button mBtnTest;
    private TextView mTvTest;
    private TitleBar2 mTitleBar2;


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mBtnTest=getView(R.id.button);
        mTvTest=getView(R.id.textView);
        mTitleBar2=getView(R.id.title_bar2);

        //设置标题栏
        ViewUtil.setViewVisiable(mTitleBar2.getTvTitle());
        //设置标题栏文字
        mTitleBar2.getTvTitle().setText("计量系统(配料员)"); //设置文字

        mTitleBar2.setTextSize(mTitleBar2.getTvTitle(),18);//16sp
        mTitleBar2.setTextSizeAutoConfig(
                mTitleBar2.getTvTitle(),
                10,
                30,
                1);

        mTitleBar2.setTextColor(mTitleBar2.getTvTitle(), R.color.red); //设置文字颜色


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
        LogUtil.i("=======测试======");

        //计算单个概率,总概率为1
        boolean flag = RandomUtil.getProbability(0.6);
        if (flag) {
            LogUtil.i("=====总概率为1,该执行概率为0.6,是否会执行：是");
        } else {
            LogUtil.i("=====总概率为1,该执行概率为0.6,是否会执行：否");
        }

        //计算一组数据中单个item执行的概率
        Map<String,Double>map=new HashMap<>();
        //设置集合
        RandomUtil.putProbability(map,"逛街",0.6);
        RandomUtil.putProbability(map,"看电影",0.2);
        RandomUtil.putProbability(map,"吃饭",0.3);
        //是否吃饭
        boolean eatFlag=RandomUtil.getProbabilityByKey(map,"吃饭");
        if(eatFlag){
            LogUtil.i("===去吃饭===");
        }else{
            LogUtil.i("===做其他的事===");
        }
    }



}


