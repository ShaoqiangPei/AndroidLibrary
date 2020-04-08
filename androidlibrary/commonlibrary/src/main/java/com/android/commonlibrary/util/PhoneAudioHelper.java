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

    /***
     * 设置音乐声音大小
     *
     * @param idex  音量
     */
    public static void setMusicVolume(int idex){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, idex, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    /**
     * 设置声音大小
     *
     * @param streamType  声音类型，可取为STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、
     *                    STREAM_RING（铃声）、STREAM_MUSIC（音乐）、STREAM_ALARM（闹铃声）
     * @param index  声音值
     * @param flag   标志位
     */
    public static void setStreamVolume(int streamType,int index,int flag){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(streamType,index,flag);
    }

    /***
     * 获取最大音量
     *
     * @param streamType 声音类型，可取为STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、
     *                   STREAM_RING（铃声）、STREAM_MUSIC（音乐）、STREAM_ALARM（闹铃声）
     * @return int
     */
    public static int getStreamMaxVolume(int streamType){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        return am.getStreamMaxVolume(streamType);
    }

    /***
     * 获取当前音量
     *
     * 注:取得当前手机的音量，最大值为7，最小值为0，当为0时，手机自动将模式调整为“震动模式”
     *
     * @param streamType 声音类型，可取为STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、
     *                   STREAM_RING（铃声）、STREAM_MUSIC（音乐）、STREAM_ALARM（闹铃声）
     * @return
     */
    public static int getStreamVolume(int streamType){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        return am.getStreamVolume(streamType);
    }

    /**获取最大通话音量**/
    public static int getMaxCallVolume(){
        return getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
    }

    /**获取当前通话音量**/
    public static int getCallVolume(){
        return getStreamVolume(AudioManager.STREAM_VOICE_CALL);
    }

    /**获取最大铃声音量**/
    public static int getMaxRingVolume(){
        return getStreamMaxVolume(AudioManager.STREAM_RING);
    }

    /**获取当前铃声音量**/
    public static int getRingVolume(){
        return getStreamVolume(AudioManager.STREAM_RING);
    }

    /**获取最大音乐音量**/
    public static int getMaxMusicVolume(){
        return getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**获取当前音乐音量**/
    public static int getMusicVolume(){
        return getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**获取最大提示音音量**/
    public static int getMaxAlarmVolume(){
        return getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }

    /**获取当前提示音音量**/
    public static int getAlarmVolume(){
        return getStreamVolume(AudioManager.STREAM_ALARM);
    }

    /***
     * 调节音量
     *
     * @param streamType  声音类型，可取为STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、
     *                    STREAM_RING（铃声）、STREAM_MUSIC（音乐）、STREAM_ALARM（闹铃声）
     *
     * @param direction  调整音量的方向，可取ADJUST_LOWER（降低）、ADJUST_RAISE（升高）、ADJUST_SAME（不变）
     * @param flags
     */
    public static void adjustStreamVolume(int streamType, int direction, int flags){
        AudioManager am = (AudioManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.AUDIO_SERVICE);
        am.adjustStreamVolume(streamType,direction,flags);
    }

}
