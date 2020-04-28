package com.android.commonlibrary.a_test.interfacer;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Title:AppActivity与AppFragment的公用接口
 * Description:
 * <p>
 * Created by pei
 * Date: 2018/3/15
 */
public interface IActivityX extends View.OnClickListener{



    /**获取TextView的值**/
    String getTextOfView(TextView textView);

    /**获取非空String**/
    String getNotEmptyString(String str);

    /**长吐司**/
    void showToast(String msg);

    /**短吐司**/
    void showShortToast(String msg);

    /**用于初始化控件的**/
    <T>T getView(int rId);

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
}
