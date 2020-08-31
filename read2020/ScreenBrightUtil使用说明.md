## ScreenBrightUtil使用说明

### 简介
`ScreenBrightUtil`是一个快速 `获取/设置` 当前屏幕亮度的工具类，其还有 设置和取消 屏幕常亮的方法。对于需要涉及屏幕亮度功能使用的时候，有恨到帮助。

### 使用说明
#### 一. 需要权限
`ScreenBrightUtil`在使用前，需要在`Androidmanifast.xml`中添加如下权限：
```
<uses-permission android:name="android.permission.WRITE_SETTINGS"/>
```
#### 二. 主要方法
`ScreenBrightUtil`主要有以下几个方法：
```
    /***
     * 获取屏幕亮度
     *
     * 注：屏幕亮度最亮255，最暗0
     */
    public static int getScreenBrightness(Context context)

    /***
     * 设置屏幕亮度
     *
     * 注：屏幕亮度最亮255，最暗0
     */
    public static void setScreenBrightness(int brightness,Context context)

    /**设置屏幕最亮**/
    public static void setMaxBrightness(Context context)

    /**设置屏幕最暗**/
    public static void setMinBrightness(Context context)

    /***
     * 设置屏幕常亮
     * 需要在界面调用 "setContentView(R.layout.布局id);" 之前调用。
     * @param context
     */
    public static void setScreenKeepOn(Context context)

    /***
     * 清除屏幕常亮
     *
     * 常在activity的onPause()周期内调用(仅供参考)
     * @param context
     */
    public static void cancelScreenKeepOn(Context context)
```
