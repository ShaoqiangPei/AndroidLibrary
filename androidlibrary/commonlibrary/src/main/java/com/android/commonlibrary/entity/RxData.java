package com.android.commonlibrary.entity;

import com.android.commonlibrary.entity.BaseEntity;

/**
 * Description:RxBus数据实体
 *
 * Author:pei
 * Date: 2019/3/21
 */
public class RxData<T> extends BaseEntity {

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
        return "RxData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
