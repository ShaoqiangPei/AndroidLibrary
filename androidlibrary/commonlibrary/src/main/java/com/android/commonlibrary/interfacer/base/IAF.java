package com.android.commonlibrary.interfacer.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import java.io.Serializable;
import java.util.List;

/**
 * Title: Activity与Fragment的公用接口
 * description:
 * autor:pei
 * created on 2022/7/13
 */
public interface IAF extends View.OnClickListener,ITop{

    /**设置布局**/
    int getContentViewId();

    /**界面数据初始化**/
    void initData();

    /**界面UI事件监听**/
    void setListener();

    /**用于初始化控件的**/
    <T>T getView(int rId);

    /**获取TextView的值**/
    String getTextOfView(TextView textView);

    /**获取非空String**/
    String getNotEmptyString(String str);

    /**长吐司**/
    void showToast(String msg);

    /**短吐司**/
    void showShortToast(String msg);

    /**基础的界面跳转**/
    void startAct(Class<?> cls);

    /**带单个参数的界面跳转**/
    void startParameterAct(Class<?> cls, String tag, Object parameter);

    /**用intent接收上一个界面传过来的int**/
    int getIntParameter(String tag);

    /**用intent接收上一个界面传过来的String**/
    String getStringParameter(String tag);

    /**用intent接收上一个界面传过来的boolean**/
    boolean getBooleanParameter(String tag);

    /**用intent接收上一个界面传过来的Bundle**/
    Bundle getBundleParameter(String tag);

    /**用intent接收上一个界面传过来的对象，对象需要实现Serializable**/
    Serializable getSerializableObject(String tag);

    /**用intent接收上一个界面传过来的对象，对象需要实现Parcelable**/
    Parcelable getParcelableObject(String tag);

    /**带List<Integer>list的界面跳转**/
    void startIntegerListAct(Class<?> cls, String tag, List<Integer> list);

    /**用intent接收上一个界面传过来的list<Integer>list**/
    List<Integer> getIntegerList(String tag);

    /**带List<String>list的界面跳转**/
    void startStringListAct(Class<?> cls, String tag, List<String> list);

    /**用intent接收上一个界面传过来的list<String>list**/
    List<String> getStringList(String tag);

    /**带List<Parcelable>list的界面跳转**/
    void startParcelableListAct(Class<?> cls, String tag, List<? extends Parcelable> list);

    /**用intent接收上一个界面传过来的list<Parcelable>list**/
    List<? extends Parcelable> getParcelableList(String tag);

    /***
     * 带List<Serializable>list的界面跳转
     *
     * @param cls
     * @param bundle  若有携带有信息的bundle需要传,则此处传该bundle对象
     *                若没有bundle需要传,则此处传null就行
     * @param tag
     * @param list
     */
    void startSerializableListAct(Class<?> cls, Bundle bundle, String tag, List<? extends Serializable> list);

    /**用intent接收上一个界面传过来的list<Serializable>list**/
    List<? extends Serializable> getSerializableList(String tag);


}
