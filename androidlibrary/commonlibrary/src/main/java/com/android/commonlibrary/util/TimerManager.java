package com.android.commonlibrary.util;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Title:定时器<只能启动一个定时器>
 * description:
 * autor:pei
 * created on 2019/10/17
 */
public class TimerManager {

    private static final long DELAY_TIME=0;//默认延时启动时间(毫秒)
    private static final long RECYCLE_TIME=1000;//默认循环时间间隔(毫秒)

    private Timer mTimer;
    private long mDelayTime=DELAY_TIME;//延时启动时间
    private long mRecycleTime=RECYCLE_TIME;//循环时间间隔

    private TimerManager(){}

    private static class Holder {
        private static TimerManager instance = new TimerManager();
    }

    public static TimerManager getInstance() {
        return Holder.instance;
    }

    /**启动定时器循环调用**/
    public void startRecycle(Context context,OnTimerListener onTimerListener){
        LogUtil.i("=====定时器循环启动开启======");
        mTimer=getTimer();
        //启动定时器
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(context!=null) {
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onTimerListener.schedule();
                        }
                    });
                }else{
                    onTimerListener.schedule();
                }
            }
        }, mDelayTime, mRecycleTime);
    }

    /**启动定时器延时调用**/
    public void startDelay(Context context,OnTimerListener onTimerListener){
        LogUtil.i("=====定时器延时启动开启======");
        mTimer=getTimer();
        //启动定时器
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(context!=null) {
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onTimerListener.schedule();
                            //取消定时器
                            TimerManager.this.cancel();
                        }
                    });
                }else{
                    onTimerListener.schedule();
                    //取消定时器
                    TimerManager.this.cancel();
                }
            }
        }, mDelayTime);
    }

    /**停止定时器**/
    public void cancel() {
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
            LogUtil.i("=====定时器取消======");
        }
    }

    /**
     * 设置延时启动时间
     *
     * @param delayTime 延时执行时间(单位毫秒),默认值为0
     * @return
     */
    public TimerManager setDelayTime(long delayTime) {
        mDelayTime = delayTime;
        return TimerManager.this;
    }

    /***
     * 设置循环时间间隔
     * (只在执行循环定时器时设置此参数有效)
     *
     * @param recycleTime 循环间隔时间(单位毫秒),默认值为1000,即1秒
     * @return
     */
    public TimerManager setRecycleTime(long recycleTime){
        mRecycleTime=recycleTime;
        return TimerManager.this;
    }

    private Timer getTimer(){
        if(mTimer==null){
            mTimer=new Timer();
        }else{
            mTimer.cancel();
            mTimer=null;
            mTimer=new Timer();
        }
        return mTimer;
    }


    public interface OnTimerListener{
        void  schedule();
    }

}
