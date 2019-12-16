package com.android.commonlibrary.entity;

import android.annotation.SuppressLint;

/**
 * Description:网络通讯返回数据最外层数据结构体(备用)
 *
 * Author:pei
 * Date: 2019/3/21
 */
@SuppressLint("ParcelCreator")
public class ResponseData<T> extends BaseEntity {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
