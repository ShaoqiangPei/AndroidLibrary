package com.android.commonlibrary.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.android.commonlibrary.app.LibraryConfig;

/**
 * Title:手机系统声音帮助类
 * description:
 * autor:pei
 * created on 2020/2/9
 */
public class PhoneAudioHelper {

    /**
     * 获取手机模式: AudioManager.RINGER_MODE_SILENT(静音模式)
     *             AudioManager.RINGER_MODE_VIBRATE(震动模式)
     *             AudioManager.RINGER_MODE_NORMAL(声音模式)
     * @return int
     */
    public static int getDefultRingerMode(){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        return am.getRingerMode();
    }


    /**获取系统默认铃声**/
    public static void getDefultRingtone() {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE); //获取系统默认的notification提示音,Uri:通用资源标志符
        Ringtone mRingtone = RingtoneManager.getRingtone(LibraryConfig.getInstance().getApplication(), uri);
        mRingtone.play();
    }

    /**获取系统默认的notification提示音**/
    public static void getDefultRingNotification() {
        Uri mUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone mRingtone = RingtoneManager.getRingtone(LibraryConfig.getInstance().getApplication(), mUri);
        mRingtone.play();
    }

}
