package com.android.commonlibrary.entity.contentprovider;

import androidx.annotation.NonNull;

import com.android.commonlibrary.entity.BaseEntity;

/**
 * Title: 短信数据载体
 *
 * description:
 * autor:pei
 * created on 2021/3/2
 */
public class SmsData extends BaseEntity {

    private String address;//短信号码
    private String person;//人物
    private String body;//信息
    private String date;//时间
    private String type;//类型

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsData{" +
                "address='" + address + '\'' +
                ", person='" + person + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
