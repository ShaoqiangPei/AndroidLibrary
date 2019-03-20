package com.androidlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.commonlibrary.util.LogUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setListener();


    }

    private void initData(){
//        LogUtil.setDebug(true);

        mBtnTest=findViewById(R.id.button);

    }

    private void setListener(){
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}


