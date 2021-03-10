package com.android.commonlibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;

import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

/**
 * Description:实现启动页view颜色渐变的工具类
 * <p>
 * Author:pei
 * Date: 2019/3/24
 */
public class LaunchUtil {

    /**
     * 默认渐变总时长
     **/
    private static final long DEFAULT_DELAY_TIME = 1500;//1.5秒
    /**
     * 默认渐变频率
     **/
    private static final long DEFAULT_CHANGE_TIME = 10;//10毫秒

    private long mDelayTime = DEFAULT_DELAY_TIME;
    private long mChangeTime = DEFAULT_CHANGE_TIME;
    private int mColors[];

    private LaunchUtil(){}

    private static class Holder {
        private static LaunchUtil instance = new LaunchUtil();
    }

    public static LaunchUtil getInstance() {
        return Holder.instance;
    }

    /**
     * 初始化
     *
     * @param startColor 如：R.color.white
     * @param endColor   如：R.color.blue
     */
    public LaunchUtil init(Context context,int startColor,int endColor){
        String startColorStr=changeColor(context,startColor);
        String endColorStr=changeColor(context,endColor);
        init(startColorStr,endColorStr);
        return this;
    }

    /**
     * 初始化
     *
     * @param startColor 八位或六位色值，如："#F3D266" 或 "#FFF3D266"
     * @param endColor   八位或六位色值，如："#FFFFFF" 或 "#FFFFFFFF"
     */
    public LaunchUtil init(String startColor,String endColor){
        //例如:colors[] = {Color.parseColor("#F3D266"), Color.parseColor("#ffffff")};

        if (StringUtil.isEmpty(startColor)) {
            throw new SecurityException("startColor为 八位或六位色值,(String数据类型)如：\"#F3D266\" 或 \"#FFF3D266\"");
        }
        if (StringUtil.isEmpty(endColor)) {
            throw new SecurityException("endColor为 八位或六位色值,(String数据类型)如：\"#F3D266\" 或 \"#FFF3D266\"");
        }
        mColors = new int[]{Color.parseColor(startColor), Color.parseColor(endColor)};
        return this;
    }


    /**
     * 将 R.color.color_ffffff 转成字符串"#FFFFFF"
     * @param context
     * @param id : R.color.color_ffffff
     * @return 字符串 "#FFFFFF"
     */
    public String changeColor(Context context, int id) {
        StringBuffer stringBuffer = new StringBuffer();
        int color = context.getResources().getColor(id);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        stringBuffer.append(Integer.toHexString(red));
        stringBuffer.append(Integer.toHexString(green));
        stringBuffer.append(Integer.toHexString(blue));

        return "#"+stringBuffer.toString();
    }

    /**
     * 未设置颜色变化总长时间，则默认DEFAULT_DELAY_TIME
     *
     * @return
     */
    public long getDelayTime() {
        return mDelayTime > 0 ? mDelayTime : DEFAULT_DELAY_TIME;
    }

    /**
     * 设置颜色变化时间总长(单位毫秒)
     **/
    public LaunchUtil setDelayTime(long delayTime) {
        this.mDelayTime = delayTime;
        return this;
    }

    public long getChangeTime() {
        return mChangeTime > 0 ? mChangeTime : DEFAULT_CHANGE_TIME;
    }

    /**
     * 设置颜色渐变频率(单位毫秒)
     *
     * @param
     */
    public LaunchUtil setChangeTime(long changeTime) {
        this.mChangeTime = changeTime;
        return this;
    }

    /**
     * 启动界面
     **/
    @SuppressLint("RestrictedApi")
    public void start(final View view, final LaunchUtil.OnFinishListener listener) {
        CountDownTimer countDownTimer = new CountDownTimer(getDelayTime(), getChangeTime()) {
            @Override
            public void onTick(long millisUntilFinished) {
                //计算渐变
                float result = ((float) millisUntilFinished / getDelayTime());
                ArgbEvaluator evaluator = new ArgbEvaluator();
                int evaluate = (int) evaluator.evaluate(result, mColors[1], mColors[0]);
                view.setBackgroundColor(evaluate);
            }

            @Override
            public void onFinish() {
                //关闭定时器
                cancel();
                listener.onFinish();
            }
        };
        countDownTimer.start();
    }

    public interface OnFinishListener {
        void onFinish();
    }
}

//========================使用范例=============
//        LaunchUtil.getInstance().init("#FF0000","#0000ff")//初始化时设置渐变起始和终止颜色
//                .setDelayTime(1500)//设置变化总时长,不设置的时候，默认时间是1.5秒
//                .setChangeTime(10)//设置变化频率,不设置的时候，默认时间是10毫秒
//                //渐变结束时的监听(启动页的时候，一般设置mView为最外层布局控件)
//                .start(mView,new LaunchUtil.OnFinishListener(){
//                @Override
//                public void onFinish(){
//                     //LogUtil.i("===启动页加载完毕====");
//                     //颜色渐变加载完毕的监听
//                    }
//                 });
