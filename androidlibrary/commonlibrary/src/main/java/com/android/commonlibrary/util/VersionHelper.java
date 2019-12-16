package com.android.commonlibrary.util;

/**
 * Title: app版本判断工具类
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class VersionHelper{

    /**
     * 判断版本，版本号为 1.2.3样式，返回ture时表示当前版本过低，需要更新
     *
     * @param loadVersion    官网app版本号
     * @param currentVersion 当前app版本号
     * @return
     */
    public static boolean isLowerVersion(String loadVersion, String currentVersion) {
        if (StringUtil.isNotEmpty(loadVersion) && StringUtil.isNotEmpty(currentVersion)) {
            if (loadVersion.contains(".") && currentVersion.contains(".")) {
                String oldVersion[] = loadVersion.split("\\.");
                String newVersion[] = currentVersion.split("\\.");
                try {
                    int oldNum0 = Integer.valueOf(oldVersion[0]);
                    int newNum0 = Integer.valueOf(newVersion[0]);
                    if (newNum0 > oldNum0) {
                        return false;
                    } else if (newNum0 < oldNum0) {
                        return true;
                    } else {
                        int oldNum1 = Integer.valueOf(oldVersion[1]);
                        int newNum1 = Integer.valueOf(newVersion[1]);
                        if (newNum1 > oldNum1) {
                            return false;
                        } else if (newNum1 < oldNum1) {
                            return true;
                        } else {
                            int oldNum2 = Integer.valueOf(oldVersion[2]);
                            int newNum2 = Integer.valueOf(newVersion[2]);
                            if (newNum2 < oldNum2) {
                                return true;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

}

//==============使用范例================
//
//    //获取当前app版本号,如:1.0.0
//    String currentVersionName=AppUtil.getVersionName()
//
//    //逻辑判断
//    if (VersionHelper.isLowerVersion(versionName, currentVersionName)) {//更新app
//       //弹出是否更新的dialog
//       updateAppDialog(url);
//    } else {//不更新
//       //进入app
//       intoApp();
//    }