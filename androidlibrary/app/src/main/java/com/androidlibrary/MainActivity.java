package com.androidlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.commonlibrary.cacher.simple_cache.SimpleCache;
import com.android.commonlibrary.util.LogUtil;

import org.json.JSONObject;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBtnTest;
    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setListener();
    }

    private void initData(){
        LogUtil.setDebug(true);

        mBtnTest=findViewById(R.id.button);
        mTvTest=findViewById(R.id.textView);

    }

    private void setListener(){
        mBtnTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 test();
            }
        });
    }

    private void test() {

    }
}


