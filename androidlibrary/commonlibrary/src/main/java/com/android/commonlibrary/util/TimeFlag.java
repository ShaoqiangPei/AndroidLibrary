package com.android.commonlibrary.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:时间节点器
 * description:主要用于统计某个 方法/代码块 执行耗时
 * autor:pei
 * created on 2019/11/28
 */
public class TimeFlag {

    private Map<Object,Long>map=new HashMap<>();

    private TimeFlag(){}

    private static class Holder {
        private static TimeFlag instance = new TimeFlag();
    }

    public static TimeFlag getInstance() {
        return Holder.instance;
    }

    /**设置int类型起始时间戳**/
    public void startFlag(int flag) {
        if (flag <= 0) {
            throw new NumberFormatException("=====flag必须为大于0的整数=====");
        }
        long time = System.currentTimeMillis();
        map.put(flag, time);
        LogUtil.i("====== TimeFlag设置起始时间戳(flag为int类型)flag=" + flag +" ======");
    }

    /**设置String类型起始时间戳**/
    public void startFlag(String flag) {
        if(StringUtil.isEmpty(flag)){
            throw new NullPointerException("=====flag不能为空=====");
        }
        long time = System.currentTimeMillis();
        map.put(flag, time);
        LogUtil.i("====== TimeFlag设置起始时间戳(flag为String类型)flag=" + flag +" ======");
    }

    /**
     * 获取 flag节点下时间段，返回"-1"表示没有设置时间起点，无法计算用时。
     *
     * @param flag  为 int 或 String
     * @return
     */
    public long stopFlag(Object flag){
        if(flag==null){
            throw new NullPointerException("=====flag不能为空=====");
        }
        Object obj=null;
        if(flag instanceof Integer){
            int tempFlag= (int) flag;
            if (tempFlag <= 0) {
                throw new NumberFormatException("=====flag必须为大于0的整数=====");
            }
            obj=map.get(tempFlag);
        }else if(flag instanceof String){
            String tempKey= flag.toString();
            obj=map.get(tempKey);
        }else{
            throw new ClassCastException("=====flag只接收 integer 和 String 数据类型=====");
        }
        if (obj != null) {
            long tempTime = 0;
            try {
                tempTime = (long) obj;
            } catch (Exception e) {
                e.printStackTrace();
                tempTime = -1;
            }
            if (tempTime > 0) {
                long time = System.currentTimeMillis();
                map.remove(flag);
                return time - tempTime;
            }
        }
        return -1;
    }

    /**
     * 获取 flag节点下时间段，返回"-1"表示没有设置时间起点，无法计算用时。
     *
     * @param flag 为 int 或 String 类型
     * @return
     */
    public String stopFlagByString(Object flag) {
        long tempTime=stopFlag(flag);
        String tempStr = "-1";
        if (tempTime == -1) {
            LogUtil.i( "===== TimeFlag=" + flag + "  用时: -1(未设置开始时间戳或时间转换错误)");
        } else {
            tempStr = formatTime(tempTime);
            LogUtil.i( "====== TimeFlag=" + flag + "  用时: " + tempStr + " ======");
        }
        return tempStr;
    }

    /**获取时间戳个数**/
    public int getFlagSize(){
        return map.size();
    }

    /**清空所有时间戳**/
    public void clearFlag(){
        map.clear();
    }

    /**毫秒转化时分秒毫秒**/
    public String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

}
