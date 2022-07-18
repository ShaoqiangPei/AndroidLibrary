package com.androidlibrary;


import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.android.commonlibrary.mvp_frame.AppActivity;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.ScreenUtil;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppActivity {

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
                float ft=ScreenUtil.px2dp(1080,MainActivity.this);
                LogUtil.i("========ft="+ft);
                break;
            default:
                break;
         }
    }

    private void test() {
       ScreenUtil.printScreenInfo();
    }


}


