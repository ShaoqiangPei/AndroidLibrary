package com.android.commonlibrary.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description:排列工具类
 *
 * Author:pei
 * Date: 2019/7/5
 */
public class CompareSortor<T> implements Comparator<T> {

    private String mFieldName;
    private boolean mReverse;//是否为降序排列，默认false

    private CompareSortor() {}

    private static class Holder {
        private static CompareSortor instance = new CompareSortor();
    }

    public static CompareSortor getInstance(){
        return CompareSortor.Holder.instance;
    }

    /**升序排列数值集合**/
    public List<T> sortList(List<T>list) {
        mReverse=false;
        return getFieldList(list);
    }

    /**升序排列对象集合**/
    public List<T>sortList(List<T>list,String fieldName){
        mReverse=false;
        return getObjectList(list,fieldName);
    }

    /**降序排列数值集合**/
    public List<T>reverseList(List<T>list){
        mReverse=true;
        return getFieldList(list);
    }

    /**降序排列对象集合**/
    public List<T>reverseList(List<T>list,String fieldName){
        mReverse=true;
        return getObjectList(list,fieldName);
    }

    /**自定义排列规则**/
    public List<T>customList(List<T>list,Comparator<T>comparator){
        if(CollectionUtil.isNotEmpty(list)){
            Collections.sort(list, comparator);
            return list;
        }
        List<T>newList=new ArrayList<>();
        return newList;
    }

    @Override
    public int compare(Object t1, Object t2) {
        if (t1 instanceof Integer) {//int
            return mReverse ? reverseCompare((int) t1, (int) t2) : sortCompare((int) t1, (int) t2);
        } else if (t1 instanceof Float) {//float
            return mReverse ? reverseCompare((float) t1, (float) t2) : sortCompare((float) t1, (float) t2);
        } else if (t1 instanceof Double) {//double
            return mReverse ? reverseCompare((double) t1, (double) t2) : sortCompare((double) t1, (double) t2);
        } else if (t1 instanceof Long) {//long
            return mReverse ? reverseCompare((long) t1, (long) t2) : sortCompare((long) t1, (long) t2);
        } else if (t1 != null && t2 != null) {//object
            Object obj1 = getFieldValueByName(mFieldName, t1);
            Object obj2 = getFieldValueByName(mFieldName, t2);
            return compare(obj1, obj2);
        } else {
            String classCastException = "强转异常：" + mFieldName + "不能转化成int,float,double,long中的任何一种类型";
            throw new ClassCastException(classCastException);
        }
    }

    /**int升序**/
    private int sortCompare(int t1,int t2){
        int i=0;
        int compare=t1-t2;
        if(compare>0){
            i=1;
        }else if(compare<0){
            i=-1;
        }
        return i;
    }

    /**float升序**/
    private int sortCompare(float t1,float t2){
        int i=0;
        float compare=t1-t2;
        if(compare>0){
            i=1;
        }else if(compare<0){
            i=-1;
        }
        return i;
    }

    /**double升序**/
    private int sortCompare(double t1,double t2){
        int i=0;
        double compare=t1-t2;
        if(compare>0){
            i=1;
        }else if(compare<0){
            i=-1;
        }
        return i;
    }

    /**long升序**/
    private int sortCompare(long t1,long t2){
        int i=0;
        long compare=t1-t2;
        if(compare>0){
            i=1;
        }else if(compare<0){
            i=-1;
        }
        return i;
    }

    /**int降序**/
    private int reverseCompare(int t1,int t2){
        int i=0;
        int compare=t1-t2;
        if(compare>0){
            i=-1;
        }else if(compare<0){
            i=1;
        }
        return i;
    }

    /**float降序**/
    private int reverseCompare(float t1,float t2){
        int i=0;
        float compare=t1-t2;
        if(compare>0){
            i=-1;
        }else if(compare<0){
            i=1;
        }
        return i;
    }

    /**double降序**/
    private int reverseCompare(double t1,double t2){
        int i=0;
        double compare=t1-t2;
        if(compare>0){
            i=-1;
        }else if(compare<0){
            i=1;
        }
        return i;
    }

    /**long降序**/
    private int reverseCompare(long t1,long t2){
        int i=0;
        long compare=t1-t2;
        if(compare>0){
            i=-1;
        }else if(compare<0){
            i=1;
        }
        return i;
    }

    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName,Object obj) {
        Object value = null;
        if (obj != null) {
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method = obj.getClass().getMethod(getter, new Class[]{});
                value = method.invoke(obj, new Object[]{});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**排列数值集合**/
    private List<T>getFieldList(List<T>list) {
        List<T> newlist = new ArrayList<>();
        this.mFieldName=null;
        if (CollectionUtil.isNotEmpty(list)) {
            Collections.sort(list, CompareSortor.this);
            for (T t : list) {
                newlist.add(t);
            }
        }
        return newlist;
    }

    /**排列对象集合**/
    private List<T>getObjectList(List<T>list,String fieldName){
        if(StringUtil.isNotEmpty(fieldName)){
            List<T>newlist=new ArrayList<>();
            if(CollectionUtil.isNotEmpty(list)){
                this.mFieldName=fieldName;
                Collections.sort(list,CompareSortor.this);
                for(T t:list){
                    newlist.add(t);
                }
            }
            return newlist;
        }else{
            String nullPointerException="空指针：调用getArraysList(List<T>list,String fieldName)方法时对象属性名称fieldName不能为空";
            throw new NullPointerException(nullPointerException);
        }
    }
}
