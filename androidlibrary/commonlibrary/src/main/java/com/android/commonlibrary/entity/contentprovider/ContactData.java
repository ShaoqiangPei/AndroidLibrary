package com.android.commonlibrary.entity.contentprovider;

import com.android.commonlibrary.entity.BaseEntity;

/**
 * Title: 手机联系人数据载体
 *
 * description:
 * autor:pei
 * created on 2021/3/2
 */
public class ContactData extends BaseEntity {

    private String name;//姓名
    private String number;//电话号码
    private String email;//邮箱

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
