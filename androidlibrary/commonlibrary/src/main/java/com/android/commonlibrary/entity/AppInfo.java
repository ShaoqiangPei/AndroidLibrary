package com.android.commonlibrary.entity;

import android.graphics.drawable.Drawable;

/**
 * Title:应用信息相关model
 *
 * description:
 * autor:pei
 * created on 2021/3/9
 */
public class AppInfo extends BaseEntity{

    //应用图标
    private Drawable icon;

    //应用的名字
    private String appName;

    //应用版本名称,如: 1.0.0
    private String versionName;

    //应用包名,如: "com.test.app"
    private String packageName;

    //应用程序的大小
    private String apkSize;

    //表示用户程序
    private boolean isUserApp;

    //存储的位置.
    private boolean isSD;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public void setUserApp(boolean userApp) {
        isUserApp = userApp;
    }

    public boolean isSD() {
        return isSD;
    }

    public void setSD(boolean SD) {
        isSD = SD;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "icon=" + icon +
                ", appName='" + appName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", apkSize='" + apkSize + '\'' +
                ", isUserApp=" + isUserApp +
                ", isSD=" + isSD +
                '}';
    }
}
