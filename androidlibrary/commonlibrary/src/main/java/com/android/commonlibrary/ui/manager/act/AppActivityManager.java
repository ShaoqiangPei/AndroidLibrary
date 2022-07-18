package com.android.commonlibrary.ui.manager.act;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.android.commonlibrary.app.LibraryConfig;
import com.android.commonlibrary.cacher.normal_cache.Cache;
import com.android.commonlibrary.util.LogUtil;

import java.util.List;
import java.util.Stack;

/**
 * @ClassName:AppManager
 * @Description:应用程序Activity管理类：用于Activity管理和应用程序退出
 * @version:V1.01
 * @author:pei
 * @create:2016-1-12 下午6:19:20
 */
public class AppActivityManager {

    private Stack<Activity> mActivityStack;

    private AppActivityManager() {
    }

    private static class SingletonHolder {
        private static AppActivityManager instance = new AppActivityManager();
    }

    public static AppActivityManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**获取Activity数目**/
    public int getActivitySize(){
        if(mActivityStack!=null){
            return mActivityStack.size();
        }
        return 0;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        if(mActivityStack!=null&&!mActivityStack.isEmpty()){
            return mActivityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if(!mActivityStack.isEmpty()){
            Activity activity = mActivityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity != null && activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束指定类名以外的Activity
     */
    public void finishOtherActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity != null &&!activity.getClass().equals(cls)) {
                if (null != activity) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        int activitySize=getActivitySize();
        for (int i = 0; i < activitySize; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * System.exit(0);会造成退出app后，再次进入时，会白屏或者黑屏3秒左右才进入广告页面
     */
    public void appExit() {
        LogUtil.i("=====退出程序=======");
        //关闭缓存
        Cache.getInstance().closeCache();
        //关闭activity
        finishAllActivity();
//        System.exit(0);
    }

    /**
     * 判断当前activity是否存在
     */
    public boolean isCurActivityExists(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context 要判断应用的包名
     * @return
     */
    public boolean isAppAlive(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : processInfos) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

}

