package com.android.commonlibrary.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;

/**
 * Title:android 获取mainfast中的元素信息
 *
 * Description:
 *  本类针对 androidMainfast 配置文件中的各种标签属性的获取,
 *  若无直接获取属性的方法，可以通过调用标签对象去获取相关标签内的属性
 *
 * Created by pei
 * Date: 2019/1/12
 */
public class MainfastUtil {

    /**
     * 在application应用<meta-data>元素。
     *   xml代码段：
     *  <application...>
     *       <meta-data android:name="data_Name" android:value="hello my application"></meta-data>
     *  </application>
     *
     */
    public static String getApplicationMetaData(String key, Context context) {
        String metaData = null;
        ApplicationInfo appInfo = getApplicationInfo(context);
        if(appInfo!=null) {
            metaData = appInfo.metaData.getString(key);
        }
        return metaData;
    }

    /**
     *  在Activity应用<meta-data>元素。
     *  xml代码段：
     *   <activity...>
     *       <meta-data android:name="key" android:value="hello my activity"></meta-data>
     *   </activity>
     *
     */
    public static String getActivityMetaData(String key, Context context) {
        String metaData = null;
        ActivityInfo info = getActivityInfo(context);
        Activity activity = getActivity(context);
        if (info != null) {
            //key为 “<meta-data android:name=" 属性的值
            metaData = info.metaData.getString(key);
        }
        return metaData;
    }

    /**
     *
     * 在service应用<meta-data>元素。
     *   xml代码段：
     *   <service android:name="MetaDataService">
     *      <meta-data android:value="hello my service" android:name="data_Name"></meta-data>
     *   </service>
     *
     *   注：Class<T>cls为服务
     */
    public static <T>String getServiceMetaData(String key,Class<T>cls,Context context){
        String metaData=null;
        ServiceInfo info= getServiceInfo(cls,context);
        if(info!=null){
            metaData=info.metaData.getString(key);
        }
        return  metaData;
    }

    /**
     * 在receiver应用<meta-data>元素。
     *   xml代码段:
     *    <receiver android:name="MetaDataReceiver">
     *            <meta-data android:value="hello my receiver" android:name="data_Name"></meta-data>
     *            <intent-filter>
     *                <action android:name="android.intent.action.PHONE_STATE"></action>
     *            </intent-filter>
     *    </receiver>
     *
     * 注：Class<T>cls为广播
     */
    public static <T>String getReceiverMetaData(String key,Class<T>cls,Context context){
        String metaData=null;
        ActivityInfo info= getReceiverInfo(cls,context);
        if(info!=null){
            metaData=info.metaData.getString(key);
        }
        return metaData;
    }

    /**获取provider的name属性**/
    public static String getProviderName(Context context){
        String name=null;
        ProviderInfo[] providers=getProviderInfo(context);
        if(providers!=null&&providers.length>0){
            for (ProviderInfo provider : providers) {
                name=provider.name;
                break;
            }
        }
        return name;
    }

    /**获取provider的authority属性**/
    public static String getProviderAuthority(Context context){
        String authority=null;
        ProviderInfo[] providers=getProviderInfo(context);
        if(providers!=null&&providers.length>0){
            for (ProviderInfo provider : providers) {
                authority=provider.authority;
                break;
            }
        }
        return authority;
    }

    /**
     * 获取provider的metadata属性
     */
    public static String getProviderMetaData(String key,Context context){
        String metaData=null;
        ProviderInfo[] providers=getProviderInfo(context);
        if(providers!=null&&providers.length>0){
            for (ProviderInfo provider : providers) {
                Bundle metaBundle=provider.metaData;
                if(metaBundle!=null){
                    metaData=provider.metaData.getString("key","Unkonown");
                    break;
                }
            }
        }
        LogUtil.i("==XXX===metaData====XXX=="+metaData);
        return metaData;
    }

    /**
     * 获取Application标签对象
     */
    public static ApplicationInfo getApplicationInfo(Context context){
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    /**
     * 获取Activity标签对象
     */
    public static ActivityInfo getActivityInfo(Context context){
        ActivityInfo info = null;
        Activity activity = getActivity(context);
        if(activity!=null) {
            try {
                info = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    /**
     * 获取Service标签对象
     * 注：Class<T>cls为服务
     */
    public static <T>ServiceInfo getServiceInfo(Class<T>cls,Context context){
        ServiceInfo info=null;
        //ComponentName cn=new ComponentName(context, MetaDataService.class);
        ComponentName cn=new ComponentName(context, cls);
        try {
            info = context.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取Recevier标签对象
     * 注：Class<T>cls为广播
     */
    public static<T>ActivityInfo getReceiverInfo(Class<T>cls,Context context){
        //ComponentName cn=new ComponentName(context, MetaDataReceiver.class);
        ComponentName cn=new ComponentName(context, cls);
        ActivityInfo info= null;
        try {
            info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取provider标签对象
     * @param context
     * @return
     */
    public static ProviderInfo[] getProviderInfo(Context context){
        ProviderInfo[] providers = null;
        PackageInfo info=null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_PROVIDERS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(info!=null){
            providers=info.providers;
        }
        return providers;
    }

    private static Activity getActivity(Context context) {
        Activity activity = null;
        try {
            activity = (Activity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }
}
