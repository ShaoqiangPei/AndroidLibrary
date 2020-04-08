package com.androidlibrary;

import android.Manifest;
import android.media.AudioManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.android.commonlibrary.activity.AppActivity;
import com.android.commonlibrary.permission.PermissionHelper;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.NumberUtil;
import com.android.commonlibrary.util.PhoneAudioHelper;

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
        //获取手机模式
        int model=PhoneAudioHelper.getDefultRingerMode();

        //获取当前音乐音量
        int voice=PhoneAudioHelper.getMusicVolume();
        //获取最大音乐音量
        int maxVoice=PhoneAudioHelper.getMaxMusicVolume();
        LogUtil.i("====1====voice="+voice+"  maxVoice="+maxVoice);

//        //设置音乐音量
//        PhoneAudioHelper.setStreamVolume(AudioManager.STREAM_MUSIC, maxVoice, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//        LogUtil.i("====2====voice=" + voice + "  maxVoice=" + maxVoice);

        //获取当前通话音量
        int call=PhoneAudioHelper.getCallVolume();
        //获取最大通话音量
        int maxCall=PhoneAudioHelper.getMaxCallVolume();

        //获取当前铃声音量
        int ring=PhoneAudioHelper.getRingVolume();
        //获取最大铃声音量
        int maxRing=PhoneAudioHelper.getMaxRingVolume();

        //获取当前提示音量
        int alarm= PhoneAudioHelper.getAlarmVolume();
        //获取最大提示音量
        int maxAlarm=PhoneAudioHelper.getMaxAlarmVolume();

        StringBuffer buffer=new StringBuffer();
        buffer.append("获取手机模式:"+model+"\n");
        buffer.append("获取当前音乐音量:"+voice+"\n");
        buffer.append("获取最大音乐音量:"+maxVoice+"\n");
        buffer.append("获取当前通话音量:"+call+"\n");
        buffer.append("获取最大通话音量:"+maxCall+"\n");
        buffer.append("获取当前铃声音量:"+ring+"\n");
        buffer.append("获取最大铃声音量:"+maxRing+"\n");
        buffer.append("获取当前提示音量:"+alarm+"\n");
        buffer.append("获取最大提示音量:"+maxAlarm+"\n");

        mTvTest.setText(buffer.toString());
    }

}


