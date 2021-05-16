package com.android.commonlibrary.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.android.commonlibrary.app.LibraryConfig;
import com.android.commonlibrary.entity.AppInfo;
import com.android.commonlibrary.util.encrypt.MD5Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 跟App相关的辅助类
 *
 * @author pei
 * @create 2016-6-13
 * 可能涉及到的权限：
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>   
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/> 
 * <uses-permission android:name="android.permission.CALL_PHONE" />
 */
public class AppUtil {


    private AppUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 获取App安装包信息
     **/
    private static PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            PackageManager packageManager = LibraryConfig.getInstance().getApplication().getPackageManager();
            info = packageManager.getPackageInfo(LibraryConfig.getInstance().getApplication().getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return info;
    }

    /**获取设备安装的app列表信息**/
    public static List<AppInfo> getAppInfos(Context context){
        //首先获取到包的管理者
        PackageManager packageManager = context.getPackageManager();
        //获取到所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        for (PackageInfo installedPackage : installedPackages) {
            AppInfo appInfo = new AppInfo();
            //程序包名
            String packageName = installedPackage.packageName;
            appInfo.setPackageName(packageName);
            //获取到图标
            Drawable icon = installedPackage.applicationInfo.loadIcon(packageManager);
            appInfo.setIcon(icon);
            //获取到应用的名字
            String appName = installedPackage.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setAppName(appName);
            //获取到应用的版本名称
            String versionName = installedPackage.versionName;
            appInfo.setVersionName(versionName);
            //获取到安装包的路径
            String sourceDir = installedPackage.applicationInfo.sourceDir;
            File file = new File(sourceDir);
            //获取到安装apk的大小
            long apkSize = file.length();
            //格式化apk的大小
            appInfo.setApkSize(Formatter.formatFileSize(context,apkSize));
            int flags = installedPackage.applicationInfo.flags;
            //判断当前是否是系统app
            if((flags & ApplicationInfo.FLAG_SYSTEM) !=0){
                //那么就是系统app
                appInfo.setUserApp(false);
            }else{
                //那么就是用户app
                appInfo.setUserApp(true);
            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0){
                //那么当前安装的就是sd卡
                appInfo.setSD(true);
            }else{
                //那么就是手机内存
                appInfo.setSD(false);
            }
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    /***
     * 根据包名打开一个app
     *
     * @param context
     * @param packageName: 包名全称,如："com.ss.android.article.lite"
     */
    public static boolean openAppByPackageName(Context context,String packageName){
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getPackageManager();
            List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                        .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                //重点是加这个
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);

                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            LogUtil.e("=====打开应用错误===errormessage="+e.getMessage());
        }
        return false;
    }

    /**
     * 获取应用程序名称
     **/
    public static String getAppName() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            int labelRes = packageInfo.applicationInfo.labelRes;
            return LibraryConfig.getInstance().getApplication().getResources().getString(labelRes);
        }
        return null;
    }

    /**
     * 获取应用程序包名
     * eg:com.example.mydemo
     */
    public static String getPackageName() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.packageName;
        }
        return null;
    }

    /***
     *  根据应用程序包名 获取 应用签名
     *
     * @param packageName eg:com.example.mydemo
     * @return 经MD5加密后的32位字符串
     */
    public static String getSignatureByPackageName(String packageName){
        if(StringUtil.isEmpty(packageName)){
            LogUtil.e("=====获取签名失败，应用程序包名为 null=====");
            return null;
        }
        PackageManager localPackageManager = LibraryConfig.getInstance().getApplication().getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                LogUtil.e("信息为 null, 应用程序包名 = " + packageName);
                return null;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            LogUtil.e("包名没有找到...");
            return null;
        }
        Signature[] arrayOfSignature = localPackageInfo.signatures;
        if ((arrayOfSignature == null) || (arrayOfSignature.length == 0)){
            LogUtil.e("===signs is null====");
            return null;
        }
        Signature sign=arrayOfSignature[0];
        String signature=null;
        if(sign!=null){
            //md5加密，生成32位字符串签名
            signature= MD5Util.getMD5(sign.toByteArray());
        }
        return signature;
    }

    /**
     * 获取应用程序版本号
     * eg:1
     **/
    public static int getVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return -1;
    }

    /**
     * 获取应用程序版本名称
     * eg:1.0.0
     **/
    public static String getVersionName() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    /**
     * 获取本应用图标 bitmap
     * @param context
     */
    public static synchronized Bitmap getAppIconBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }


    /**
     * 获取手机是否root信息
     * @return
     *    
     */
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }

    private static TelephonyManager getTelephonyManager() {
        TelephonyManager tm = (TelephonyManager) LibraryConfig.getInstance().getApplication().getSystemService(TELEPHONY_SERVICE);
        return tm;
    }

    /**
     * 获取 IMEI
     * 移动设备国际识别码,是手机设备的唯一识别号码
     * eg:866968025896771
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId() {
        TelephonyManager tm = getTelephonyManager();
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 获取 设备uuid
     * 优先用androidId生成uuid,
     * 若uuid获取不到,则用Android其他设备信息拼成uuid
     * 若设备信息还是获取不到，则随机生成一个uuid
     *
     * eg:uuid格式：00995657cc093f88966c652e25cb6dbc
     */
    public static String getUUId() {
        String uuid=AppUtil.getAndroidId();
        if(StringUtil.isEmpty(uuid)){
            uuid=getRandomUUId();
        }else{
            uuid= UUID.nameUUIDFromBytes((uuid).getBytes(Charset.forName("UTF-8"))).toString();
        }
        uuid=uuid.replaceAll("-","");
        return uuid;
    }

    /**生成唯一的uuid**/
    private static String getRandomUUId() {
        String uuid;
        String m_szDevIDShort = "70" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            String serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            uuid = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化,随意值
            String serial = "serial";
            uuid = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        if (TextUtils.isEmpty(uuid)) {
            //生成随机uuid
            uuid = UUID.randomUUID().toString();
        }
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    /**
     * 获取 IMSI
     * 国际移动用户识别码，储存在SIM卡中，可用于区别移动用户的有效信息
     * eg:460021723187258
     */
    @SuppressLint("MissingPermission")
    public static String getSIMId(){
        TelephonyManager tm=getTelephonyManager();
        if(tm!=null){
            return tm.getSubscriberId();
        }
        return null;
    }

    /**
     * 获取手机型号
     * eg:A31
     */
    public static String getDeviceModel(){
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     * eg:OPPO
     */
    public static String getDeviceBrand(){
        return Build.BRAND;
    }

    /**
     * 获取设备名称
     * eg:UBX
     */
    /**设备名称**/
    public static String getDeviceName() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取系统版本号(用户可见的版本字符串)
     * eg:4.4.4
     */
    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前设备的sdk版本号
     * @return
     */
    public static int getSDKVersion(){
       return Build.VERSION.SDK_INT;
    }

    /**
     * 判断手机是否插入SIM卡
     */
    public static boolean isHaveSIM(){
        TelephonyManager tm=getTelephonyManager();
        if(tm!=null){
            @SuppressLint("MissingPermission")
            String simSer=tm.getSimSerialNumber();
            if(simSer!=null&&!"".equals(simSer)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取电话号码(不一定获取得到)
     *
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNum(){
        if(isHaveSIM()){//sim卡已插入
            TelephonyManager tm=getTelephonyManager();
            if(tm!=null){
                return tm.getLine1Number();
            }
        }
        return null;
    }

    /**获取SIM卡运营商名称**/
    public static String getSIMOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator(); //sim提供者
        String str = "";
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007") || operator.equals("46020") || operator.equals("46004")) {
                str = "中国移动";
            } else if (operator.equals("46001") || operator.equals("46006") || operator.equals("46009") || operator.equals("46010")) {
                str = "中国联通";
            } else if (operator.equals("46003") || operator.equals("46005") || operator.equals("46011")) {
                str = "中国电信";
            } else if (operator.equals("")) {
                str = "无sim卡";
            } else {
                str = "未知sim卡";
            }
        } else {
            str = "SIM卡错误";
        }
        return str;
    }

    /**
     *Settings.Secure.ANDROID_ID 是一串64位的编码（十六进制的字符串），是随机生成的设备的第一个引导，
     * 其记录着一个固定值，通过它可以知道设备的寿命（在设备恢复出厂设置后，该值可能会改变）。
     * ANDROID_ID也可视为作为唯一设备标识号的一个好选择。
     */
    public static String getAndroidId(){
        String androidId = Settings.Secure.getString(LibraryConfig.getInstance().getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    private static WifiInfo getWifiInfo(){
        WifiInfo wifiInfo=null;
        WifiManager wifiManager= (WifiManager) LibraryConfig.getInstance().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager!=null){
            wifiInfo=wifiManager.getConnectionInfo();
        }
        return wifiInfo;
    }

    /**
     * 获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    public static String getMacAddress(){
        WifiInfo wifiInfo=getWifiInfo();
        if(wifiInfo!=null){
            return wifiInfo.getMacAddress();
        }
        return null;
    }


    /**
     * 手机CPU信息
     */
    public static String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"",""};;
        String[] arrayOfString;

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + "  ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();

            return "CPU型号:"+cpuInfo[0]+"  CPU频率:"+cpuInfo[1];

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**获取手机IP地址**/
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    /**
     * 判断apk是否已安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(String packageName) {
        PackageManager pm = LibraryConfig.getInstance().getApplication().getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return installed;
    }

    /**
     * 获取已安装Apk文件的源Apk文件路径
     *
     * @return
     */
    public static String getSourceApkPath() {
        return LibraryConfig.getInstance().getApplication().getApplicationInfo().sourceDir;
    }


    /****
     * 打开网页
     */
    public static void goToWebPage(String url, Context context){
        Uri uri = Uri.parse(url);//"http://google.com"
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context){
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**去拨打电话界面**/
    public static void goToPhone(Context context,String phoneNum) {
        if (StringUtil.isEmpty(phoneNum)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    /**去辅助服务界面(无障碍)**/
    public static void goToAccessibility(Context context){
        //手动开启辅助服务
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

}
