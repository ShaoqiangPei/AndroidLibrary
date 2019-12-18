package com.android.commonlibrary.entity;

/**
 * Description:用于业务逻辑线程更新主线程数据传值的data
 *
 * Author:pei
 * Date: 2019/4/12
 */
public class UIData<T> extends BaseEntity{

    private int code;
    private String message;
    private int notifyId;
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

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    @Override
    public String toString() {
        return "UIData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", notifyId=" + notifyId +
                ", data=" + data +
                '}';
    }
}
