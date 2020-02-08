package com.android.commonlibrary.message_data;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Title:封装Handler防止内存泄漏
 * description:
 * autor:pei
 * created on 2020/2/8
 */
public class UIHandler<T> extends Handler {

    //弱引用(引用外部类)
    private WeakReference<T> weakReference;
    private OnUIHandListener mOnUIHandListener;

    public UIHandler(Object obj, OnUIHandListener listener) {
        if(obj==null){
            throw new NullPointerException("=====传入obj不能为空=====");
        }
        //构造弱引用
        weakReference = new WeakReference<>((T) obj);
        this.mOnUIHandListener=listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //通过弱引用获取外部类.
        Object obj = weakReference.get();
        //进行非空再操作
        if (obj != null&&mOnUIHandListener!=null) {
            mOnUIHandListener.uiHandle(obj,msg);
        }
        //移除消息
        removeMessages(msg.what);
    }

    public interface OnUIHandListener{
        void uiHandle(Object obj, Message msg);
    }

}
