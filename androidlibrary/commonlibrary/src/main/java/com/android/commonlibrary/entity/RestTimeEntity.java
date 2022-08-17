package com.android.commonlibrary.entity;

/**
 * Title: 时间bean
 * description:
 * autor:pei
 * created on 2022/8/17
 */
public class RestTimeEntity extends BaseEntity{

    private long day = 0;
    private long hour = 0;
    private long minutes = 0;
    private long second = 0;

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }
}
