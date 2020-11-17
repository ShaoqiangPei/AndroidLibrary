package com.android.commonlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:activity跳转帮助类
 *
 * Author:pei
 * Date: 2019/7/3
 */
public class IntentHelper {

    private IntentHelper(){}

    private static class Holder {
        private static IntentHelper instance = new IntentHelper();
    }

    public static IntentHelper getInstance() {
        return Holder.instance;
    }

    /**基础的intent**/
    private Intent newIndexIntent(Context context, Class<?> cls) {
        Intent newIntent = new Intent(context, cls);
        return newIntent;
    }

    /**基础的界面跳转**/
    public void startAct(Context context,Class<?> cls) {
        Intent intent = newIndexIntent(context,cls);
        context.startActivity(intent);
    }

    /**用intent给下一个界面传带单个参数的界面跳转**/
    public Intent putParameter(Intent intent, String tag, Object parameter){
        if(parameter==null){
            throw new NullPointerException("=====传入参数不能为null=======");
        }
        if(parameter instanceof Short){
            intent.putExtra(tag, (short) parameter);
        }else if(parameter instanceof Integer){
            intent.putExtra(tag, (int) parameter);
        }else if(parameter instanceof Float){
            intent.putExtra(tag, (float) parameter);
        }else if(parameter instanceof Double){
            intent.putExtra(tag, (double) parameter);
        }else if(parameter instanceof Long){
            intent.putExtra(tag, (long) parameter);
        }else if(parameter instanceof Byte){
            intent.putExtra(tag, (byte) parameter);
        }else if(parameter instanceof Character){
            intent.putExtra(tag, (char) parameter);
        }else if(parameter instanceof String){
            intent.putExtra(tag, (String) parameter);
        }else if(parameter instanceof Boolean){
            intent.putExtra(tag, (boolean) parameter);
        }else if(parameter instanceof Bundle){
            intent.putExtra(tag, (Bundle) parameter);
        }else if(parameter instanceof Serializable){
            intent.putExtra(tag, (Serializable) parameter);
        }else if(parameter instanceof Parcelable){
            intent.putExtra(tag, (Parcelable) parameter);
        }else {
            throw new ClassCastException("======参数类型错误=====");
        }
        return intent;
    }

    /**带单个参数的界面跳转**/
    public void startParameterAct(Context context,Class<?> cls, String tag, Object parameter) {
        Intent intent = newIndexIntent(context,cls);
        Intent parameterIntent=putParameter(intent,tag,parameter);
        context.startActivity(parameterIntent);
    }

    /**用intent接收上一个界面传过来的Short**/
    public short getShortParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getShortExtra(tag, (short) 0);
    }

    /**用intent接收上一个界面传过来的int**/
    public int getIntParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getIntExtra(tag, 0);
    }

    /**用intent接收上一个界面传过来的float**/
    public float getFloatParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getFloatExtra(tag, 0f);
    }

    /**用intent接收上一个界面传过来的double**/
    public double getDoubleParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getDoubleExtra(tag, 0d);
    }

    /**用intent接收上一个界面传过来的long**/
    public long getLongParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getLongExtra(tag, 0L);
    }

    /**用intent接收上一个界面传过来的byte**/
    public byte getByteParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getByteExtra(tag, (byte) 0);
    }

    /**用intent接收上一个界面传过来的char**/
    public char getCharParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getCharExtra(tag, ' ');
    }

    /**用intent接收上一个界面传过来的String**/
    public String getStringParameter(Context context,String tag) {
        Object parameter=((Activity)context).getIntent().getStringExtra(tag);
        if(parameter!=null){
            return parameter.toString();
        }
        return null;
    }

    /**用intent接收上一个界面传过来的boolean**/
    public boolean getBooleanParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getBooleanExtra(tag,false);
    }

    /**用intent接收上一个界面传过来的Bundle**/
    public Bundle getBundleParameter(Context context,String tag) {
        return ((Activity)context).getIntent().getBundleExtra(tag);
    }

    /**用intent接收上一个界面传过来的对象，对象需要实现Serializable**/
    public Serializable getSerializableObject(Context context,String tag) {
        return ((Activity)context).getIntent().getSerializableExtra(tag);
    }

    /**用intent接收上一个界面传过来的对象，对象需要实现Parcelable**/
    public Parcelable getParcelableObject(Context context,String tag) {
        return ((Activity)context).getIntent().getParcelableExtra(tag);
    }

    /**用intent给下一个界面传int集合**/
    public Intent putIntList(Intent intent, String tag, List<Integer> list){
        intent.putIntegerArrayListExtra(tag, (ArrayList<Integer>) list);
        return intent;
    }

    /**带List<Integer>list的界面跳转**/
    public void startIntegerListAct(Context context,Class<?> cls, String tag, List<Integer> list) {
        Intent intent = newIndexIntent(context,cls);
        Intent listIntent = putIntList(intent, tag, list);
        context.startActivity(listIntent);
    }

    /**用intent接收上一个界面传过来的list<Integer>list**/
    public List<Integer> getIntegerList(Context context,String tag) {
        return ((Activity)context).getIntent().getIntegerArrayListExtra(tag);
    }

    /**用intent给下一个界面传String集合**/
    public Intent putStringList(Intent intent, String tag, List<String> list){
        intent.putStringArrayListExtra(tag, (ArrayList<String>) list);
        return intent;
    }

    /**带List<String>list的界面跳转**/
    public void startStringListAct(Context context,Class<?> cls, String tag, List<String> list) {
        Intent intent = newIndexIntent(context,cls);
        Intent listIntent = putStringList(intent, tag, list);
        context.startActivity(listIntent);
    }

    /**用intent接收上一个界面传过来的list<String>list**/
    public List<String> getStringList(Context context,String tag) {
        return ((Activity)context).getIntent().getStringArrayListExtra(tag);
    }

    /**用intent给下一个界面传object集合,object需要实现Parcelable接口**/
    public Intent putParcelableList(Intent intent, String tag, List<? extends Parcelable> list){
        intent.putParcelableArrayListExtra(tag, (ArrayList<? extends Parcelable>) list);
        return intent;
    }

    /**带List<Parcelable>list的界面跳转**/
    public void startParcelableListAct(Context context,Class<?> cls, String tag, List<? extends Parcelable> list) {
        Intent intent = newIndexIntent(context,cls);
        Intent listIntent = putParcelableList(intent, tag, list);
        context.startActivity(listIntent);
    }

    /**用intent接收上一个界面传过来的list<Parcelable>list**/
    public List<? extends Parcelable> getParcelableList(Context context,String tag) {
        return ((Activity)context).getIntent().getParcelableArrayListExtra(tag);
    }

    /***
     * 用intent给下一个界面传object集合,object需要实现Serializable接口
     *
     * @param intent
     * @param bundle 若有携带有信息的bundle需要传,则此处传该bundle对象
     *               若没有bundle需要传,则此处传null就行
     * @param tag
     * @param list
     * @return
     */
    public Intent putSerializableList(Intent intent, Bundle bundle,String tag, List<? extends Serializable> list){
        if(bundle==null){
            bundle=new Bundle();
        }
        bundle.putSerializable(tag,(Serializable)list);//序列化,要注意转化(Serializable)
        intent.putExtras(bundle);//发送数据
        return intent;
    }

    /***
     * 带List<Serializable>list的界面跳转
     *
     * @param context
     * @param cls
     * @param bundle  若有携带有信息的bundle需要传,则此处传该bundle对象
     *                若没有bundle需要传,则此处传null就行
     * @param tag
     * @param list
     */
    public void startSerializableListAct(Context context,Class<?> cls, Bundle bundle,String tag, List<? extends Serializable> list) {
        Intent intent = newIndexIntent(context,cls);
        Intent listIntent = putSerializableList(intent, bundle,tag, list);
        context.startActivity(listIntent);
    }

    /**用intent接收上一个界面传过来的list<Serializable>list**/
    public List<? extends Serializable> getSerializableList(Context context,String tag) {
        return (List<? extends Serializable>) ((Activity)context).getIntent().getSerializableExtra(tag);
    }
}
