package com.android.commonlibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Title:监听程序安装，替换和卸载
 * Description:
 *
 *   需要在项目的 mainfast 文件中静态注册此Receiver,示例代码如下：
 *   <receiver android:name=".XXXReceiver"
 *             android:label="@string/app_name">
 *     <intent-filter>
 *          <action android:name="android.intent.action.PACKAGE_ADDED" />
 *          <action android:name="android.intent.action.PACKAGE_REPLACED" />
 *          <action android:name="android.intent.action.PACKAGE_REMOVED" />
 *          <data android:scheme="package" />
 *      </intent-filter>
 *   </receiver>
 *
 *   1. XXXReceiver 为你项目中的监听程序安装，替换和卸载的广告播类名，XXXReceiver需要继承AppInstallReceiver
 *   2. 当App的versionCode不同的时候，在安装时才会触发替换等广播
 *   3. 如无特别要求，你项目中的XXXReceiver只需要继承AppInstallReceiver，然后实现install，uninstall和replace即可
 *      如有更多要求,则需要重写AppInstallReceiver的onReceive方法，然后在mainfast中注册的时候，添加必要的action
 *
 *
 *   注：
 *   ACTION_PACKAGE_ADDED  一个新应用包已经安装在设备上，数据包括包名（最新安装的包程序不能接收到这个广播）
 *   ACTION_PACKAGE_REPLACED	一个新版本的应用安装到设备，替换之前已经存在的版本
 *   ACTION_PACKAGE_CHANGED	一个已存在的应用程序包已经改变，包括包名
 *   ACTION_PACKAGE_REMOVED	一个已存在的应用程序包已经从设备上移除，包括包名（正在被安装的包程序不能接收到这个广播）
 *   ACTION_PACKAGE_RESTARTED	用户重新开始一个包，包的所有进程将被杀死，所有与其联系的运行时间状态应该被移除，包括包名（重新开始包程序不能接收到这个广播）
 *   ACTION_PACKAGE_DATA_CLEARED	用户已经清楚一个包的数据，包括包名（清除包程序不能接收到这个广播）
 *
 * Created by pei
 * Date: 2017/9/27
 */
public abstract class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        String packageName = intent.getData().getSchemeSpecificPart();

        switch (intent.getAction()) {
            case Intent.ACTION_PACKAGE_ADDED://安装成功
//                LogUtil.e("===AppInstallReceiver==安装成功=====packageName="+packageName);
                install(context,intent);
                break;
            case Intent.ACTION_PACKAGE_REMOVED://卸载成功
//                LogUtil.e("===AppInstallReceiver==卸载成功=====packageName="+packageName);
                uninstall(context,intent);
                break;
            case Intent.ACTION_PACKAGE_REPLACED://替换成功
//                LogUtil.e("===AppInstallReceiver==替换成功=====packageName="+packageName);
//                //替换成功后删除apk
//                String apkPath= DownLoadService.DEST_FILE_DIR+ File.separator+DownLoadService.DEST_FILE_NAME;
//                FileUtil.delFile(apkPath);
//                LogUtil.e("===安装包已删除====");
                replace(context,intent);
                break;
            default:
                break;
        }
    }

    protected abstract void install(Context context, Intent intent);
    protected abstract void uninstall(Context context, Intent intent);
    protected abstract void replace(Context context, Intent intent);

}

//=====================项目中使用范例===========
//public class MyReceiver extends AppInstallReceiver{
//
//    @Override
//    protected void install(Context context, Intent intent) {
//        LogUtil.i("========安装========");
//    }
//
//    @Override
//    protected void uninstall(Context context, Intent intent) {
//        LogUtil.i("========卸载========");
//    }
//
//    @Override
//    protected void replace(Context context, Intent intent) {
//        LogUtil.i("========替换========");
//    }
//}