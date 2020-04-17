package com.android.commonlibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import com.android.commonlibrary.app.LibraryConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Instruction:网络检测类
 *
 * Author:pei
 * Date: 2017/7/18
 * Description:
 */
public class NetUtil {

    public static final String WIFI="WIFI";//wifi网络
    public static final String MOBILE="MOBILE";//手机网络
    public static final String MOBILE_CMNET="CMNET";//手机网络之cmnet类型
    public static final String MOBILE_CMWAP="CMWAP";//手机网络之cmwap类型

    /***
     * 检查网络是否可用
     * 需要权限 ACCESS_NETWORK_STATE
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)LibraryConfig.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     * @return boolean
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) LibraryConfig.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**wifi是否打开**/
    public static boolean isWifiEnabled() {
        ConnectivityManager mgrConn = (ConnectivityManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 检查WIFI是否连接
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
     *
     * @return boolean
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) LibraryConfig.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 检测在连接网络类型 1.无网络（这种状态可能是因为手机停机，网络没有开启，信号不好等原因） 2.使用WIFI上网 3.CMWAP（中国移动代理）
     * 4.CMNET上网 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
     */
    @SuppressLint("DefaultLocale")
    public static String getNetType() {
        String netType = "";
        ConnectivityManager connMgr = (ConnectivityManager) LibraryConfig.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = NetUtil.MOBILE_CMNET;
            } else {
                netType = NetUtil.MOBILE_CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NetUtil.WIFI;
        }
        return netType;
    }

    /**VPN是否已经连接**/
    public static boolean isVpnConnected(){
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    LogUtil.i("isVpnUsed() NetworkInterface Name: " + intf.getName());
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true; // The VPN is up
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**获取网络信号强弱**/
    public static void signal(SignalStrengthLietener listener){
        if(isNetworkConnected()){
            if(isWifiConnected()){//wifi连接
                int level = getWifiLevel();
                if(level<70){//wifi信号强
                    listener.strong(NetUtil.WIFI,level);
                }else{//wifi信号弱
                    listener.weak(NetUtil.WIFI,level);
                }
            }else if(isMobileNetworkConnected()){//手机网络连接
                getMobileDbm(new MobileSignalListener() {
                    @Override
                    public void signal(int dbm, int level) {
                        if (dbm > -103) {//手机信号好
                            listener.strong(NetUtil.MOBILE,dbm);
                        } else {//手机信号差
                            listener.weak(NetUtil.MOBILE,dbm);
                        }
                    }
                });
            }
        }else{//当前无网络
            listener.disConnect();
        }
    }

    /**
     * 获取wifi网络信号强度值
     * 需要 ACCESS_WIFI_STATE 权限
     * @return
     */
    public static int getWifiLevel(){
        int level = Math.abs(((WifiManager) LibraryConfig.getInstance().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getRssi());
        return level;
    }

    /**获取手机信号强度值**/
    public static void getMobileDbm(MobileSignalListener mobileSignalListener){
        TelephonyManager telephonyManager= (TelephonyManager)LibraryConfig.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        SignalListener listener=new SignalListener(telephonyManager,mobileSignalListener);
        //开启监听
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        LogUtil.i("=======开启移动信号监听=======");
    }

    /**手机信号实际监听类**/
    private static class SignalListener extends PhoneStateListener{
        //  dbm<-115dBm 没有运营商网络,网络信号非常差
        // -95dBm~-75dBm 手机信号在满格和两三格之间波动  网络较差
        // -75dBm~-50dBm 手机信号显示满格  网络信号一般
        // -50dBm~0dBm 手机信号显示满格  网络信号非常好，基本处于基站附近
        //手机信号满格,不一定代表信号超强；但是,手机信号不满格,肯定就表示运营商信号不好

        private TelephonyManager telephonyManager;
        private MobileSignalListener listener;

        public SignalListener(TelephonyManager telephonyManager,MobileSignalListener listener){
            this.telephonyManager=telephonyManager;
            this.listener=listener;
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            Method method1 = null;
            int dbm=-1;
            int level=-1;

            try {
                method1 = signalStrength.getClass().getMethod("getDbm");
                dbm = Integer.valueOf(method1.invoke(signalStrength).toString());

                Method method2 = signalStrength.getClass().getMethod("getLteLevel");
                level = Integer.valueOf(method2.invoke(signalStrength).toString());
                //查看源码 level 格数显示为
                //mdb>-89 显示4格,信号优
                //mdb>-97 显示3格,信号好
                //mdb>-103 显示2格,信号一般
                //mdb>-113 显示1格,信号差
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if(listener!=null){
                listener.signal(dbm,level);//dbm:信号强度  level:信号格数
            }
            if(telephonyManager!=null){
                //停止监听
                telephonyManager.listen(SignalListener.this, PhoneStateListener.LISTEN_NONE);
                LogUtil.i("=======停止移动信号强度监听=========");
            }
        }
    }


    /**手机信号监听接口**/
    public interface MobileSignalListener{
        void signal(int dbm, int level);
    }

    /**信号强弱监听类(包括手机流量信号和wifi信号)**/
    public interface SignalStrengthLietener{
        void strong(String type, int dbm);//网络信号强
        void weak(String type, int dbm);//网络信号弱
        void disConnect();//网络未连接
    }

}
