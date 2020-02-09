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
```
#### 二. PhoneAudioHelper使用示例
下面给出`PhoneAudioHelper`使用的一个示例:
```

```
