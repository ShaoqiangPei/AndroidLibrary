package com.androidlibrary.activity;


import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;

import com.android.commonlibrary.frame.af.AppActivity;
import com.android.commonlibrary.interfacer.frame.struct.mvp.PrePresenter;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.ScreenUtil;
import com.androidlibrary.R;

//@RequiresApi(api = Build.VERSION_CODES.N)
//public class MainActivity extends AppActivity {
//
//    private Button mBtnTest;
//    private TextView mTvTest;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public void initData() {
//        mBtnTest=getView(R.id.button);
//        mTvTest=getView(R.id.textView);
//
//
//    }
//
//    @Override
//    public void setListener() {
//        mBtnTest.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//
//        switch (v.getId()) {
//            case R.id.button:
//                test();
//                float ft=ScreenUtil.px2dp(1080,MainActivity.this);
//                LogUtil.i("========ft="+ft);
//                break;
//            default:
//                break;
//         }
//    }
//
//    private void test() {
//       ScreenUtil.printScreenInfo();
//    }
//
//
//}

//@RequiresApi(api = Build.VERSION_CODES.N)
//public class MainActivity extends AppActivity implements MainContract.View{
//
//    private Button mBtnTest;
//    private TextView mTvTest;
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public PrePresenter getPresenter() {
//        return new MainPresenter(this,this);
//    }
//
//    @Override
//    public void initData() {
//        mBtnTest=getView(R.id.button);
//        mTvTest=getView(R.id.textView);
//
//
//    }
//
//    @Override
//    public void setListener() {
//        mBtnTest.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//
//        switch (v.getId()) {
//            case R.id.button:
//                test();
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void test() {
//        ((MainPresenter)mPresenter).login();
//    }
//
//
//    @Override
//    public void loginSuccess() {
//         LogUtil.i("======登录成功=======");
//    }
//
//    @Override
//    public void loginFail(String msg) {
//
//    }
//
//}

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppActivity implements MainContract.View{

    private Button mBtnTest;
    private TextView mTvTest;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public PrePresenter getPresenter() {
        return new MainPresenter(this,this);
    }


    @Override
    public void initData() {
        mBtnTest=getView(R.id.button);
        mTvTest=getView(R.id.textView);

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

    private void test() {
        ((MainPresenter)getPresenter()).login();
    }


    @Override
    public void loginSuccess() {
        LogUtil.i("======登录成功==77=====");
    }

    @Override
    public void loginFail(String msg) {

    }
}


