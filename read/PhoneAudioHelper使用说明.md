## PhoneAudioHelper使用说明

### 简介
`PhoneAudioHelper`是一个获取手机系统默认声音的帮助类。可获取手机当前`静音/震动/响铃` 模式。还能获取手机当前默认响铃及提示音。


### 使用说明
#### 一. PhoneAudioHelper主要方法
`PhoneAudioHelper`主要有以下几个方法:
```
    /**
     * 获取手机模式: AudioManager.RINGER_MODE_SILENT(静音模式)
     *             AudioManager.RINGER_MODE_VIBRATE(震动模式)
     *             AudioManager.RINGER_MODE_NORMAL(声音模式)
     * @return int
     */
    public static int getDefultRingerMode()
    
    /**获取系统默认铃声**/
    public static void getDefultRingtone()
    
    /**获取系统默认的notification提示音**/
    public static void getDefultRingNotification()
    
    ......
```
更多方法，请参看源码。

#### 二. PhoneAudioHelper使用示例
各种模式下关于声音处理的示例：
```
        //获取手机当前模式
        int mode=PhoneAudioHelper.getDefultRingerMode();
        switch (mode) {
            case AudioManager.RINGER_MODE_SILENT://静音模式
                //若当前手机为静音模式的处理
                //......
                break;
            case AudioManager.RINGER_MODE_VIBRATE://震动模式
                //若当前手机为震动模式的处理
                //......
                break;
            case AudioManager.RINGER_MODE_NORMAL://声音模式
                //播放默认提示音
                PhoneAudioHelper.getDefultRingNotification();

//                //播放默认响铃
//                PhoneAudioHelper.getDefultRingtone();
                break;
            default:
                break;
        }
```
各种声音的获取及设置示例:
```
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
```

