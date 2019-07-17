package com.androidlibrary;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.activity.AppFragActivity;
import com.android.commonlibrary.util.LogUtil;

public class MainActivity extends AppActivity {

    private Button mBtnTest;
    private TextView mTvTest;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
//        LogUtil.setDebug(true);

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

    }

}


