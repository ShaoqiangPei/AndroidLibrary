package com.androidlibrary;

import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.NetUtil;
import com.android.commonlibrary.util.ScreenUtil;
import com.android.commonlibrary.util.SpannableStringUtil;
import com.android.commonlibrary.widget.TitleBar;

import butterknife.BindView;

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


