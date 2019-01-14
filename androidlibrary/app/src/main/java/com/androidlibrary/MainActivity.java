package com.androidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.commonlibrary.util.FormatUtil;
import com.android.commonlibrary.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.setDebug(true);

        LogUtil.i("====k1=="+ FormatUtil.formatKmDistance(1504,2));

    }

}


