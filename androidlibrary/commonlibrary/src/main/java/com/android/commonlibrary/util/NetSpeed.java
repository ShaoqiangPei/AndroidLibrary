package com.android.commonlibrary.util;

import android.content.Context;
import android.net.TrafficStats;
import android.widget.TextView;

import com.android.commonlibrary.app.LibraryConfig;

import java.text.DecimalFormat;

/**
 * Title:测网速工具类
 * description:
 * autor:pei
 * created on 2020/3/11
 */
public class NetSpeed {

    private static final long DELAY_TIME=500;//默认延时启动时间(毫秒)
    private static final long RECYCLE_TIME=1500;//默认循环时间间隔(毫秒)

    private long mLastTotalRxBytes;
    private long mLastTimeStamp;
    private long mDelayTime=DELAY_TIME;//延时启动时间
    private long mRecycleTime=RECYCLE_TIME;//循环时间间隔

    private NetSpeed(){}

    private static class Holder {
        private static NetSpeed instance = new NetSpeed();
    }

    public static NetSpeed getInstance() {
        return Holder.instance;
    }

    /**
     * 设置延时启动时间
     *
     * @param delayTime 单位毫秒,默认500毫秒
     * @return
     */
    public NetSpeed setDelayTime(long delayTime) {
        mDelayTime = delayTime;
        return NetSpeed.this;
    }

    /***
     * 设置循环时间间隔
     *
     * @param recycleTime 单位毫秒,默认1500毫秒
     * @return
     */
    public NetSpeed setRecycleTime(long recycleTime){
        mRecycleTime=recycleTime;
        return NetSpeed.this;
    }

    /**开始监测网速**/
    public void start(TextView view){
        //定时器循环
        TimerManager.getInstance()
                .setDelayTime(mDelayTime)//设置延时启动时间，默认为0
                .setRecycleTime(mRecycleTime)//设置循环时间间隔,默认为1000,即1秒
                //context设置为null时可执行非ui的逻辑，context不为null时可执行更新ui逻辑
                .startRecycle(LibraryConfig.getInstance().getApplication(),new TimerManager.OnTimerListener() {
                    @Override
                    public void schedule() {
                        //循环执行逻辑
                        String speed=getNetSpeed();
                        view.setText(speed);
                    }
                });
    }

    /**取消网速监测**/
    public void cancel(){
        TimerManager.getInstance().cancel();
        LogUtil.i("======取消网速监测=======");
    }

    /***
     * 获取某一时刻网速
     *
     * 需要结合定时器更新
     * @return
     */
    public String getNetSpeed(){
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long differTimeStamp=nowTimeStamp - mLastTimeStamp;

        long speed = differTimeStamp!=0?((nowTotalRxBytes - mLastTotalRxBytes) * 1000 / differTimeStamp):0;//毫秒转换

        mLastTimeStamp = nowTimeStamp;
        mLastTotalRxBytes = nowTotalRxBytes;

        String netSpeed=formatNetSpeed(speed);

        LogUtil.i("======实时网速监测===netSpeed="+netSpeed);
        return netSpeed;
    }

    /**将byte自动转换为其他单位**/
    public String formatNetSpeed(long bytes) {
        String speedString=null;

        DecimalFormat decimalFormat =new DecimalFormat("0.00");
        if (bytes >=1048576d) {
            speedString =decimalFormat.format(bytes /1048576d) +" MB/s";
        }else if(bytes >=1024d){
            speedString =decimalFormat.format(bytes /1024d) +" KB/s";
        }else if(bytes >=0d){
            speedString =decimalFormat.format(bytes) +" K/s";
        }
        return speedString;
    }

    private long getTotalRxBytes(){
        int uid=LibraryConfig.getInstance().getApplication().getApplicationInfo().uid;
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }

}
