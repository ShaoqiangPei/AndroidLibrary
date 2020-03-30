package com.android.commonlibrary.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:序列化对象基类
 *
 * Author:pei
 * Date: 2019/3/22
 */
public class BaseEntity implements Serializable{

    /**打印对象属性值**/
    public String printObject(){
        List<Map<String,Object>> filedInfos=getFiledsInfo(this);
        StringBuffer buffer=new StringBuffer();
        if(!filedInfos.isEmpty()){
            buffer=getBuffer(filedInfos,buffer);
        }
        return buffer.toString();
    }

    /**打印对象属性值**/
    public String objectToString(Object obj){
        List<Map<String,Object>> filedInfos=getFiledsInfo(obj);
        StringBuffer buffer=new StringBuffer();
        if(!filedInfos.isEmpty()){
            buffer=getBuffer(filedInfos,buffer);
        }else{
            if(obj!=null){
                String className=obj.getClass().getSimpleName();
                buffer.append(className);
            }else{
                buffer.append("objectToString方法调用参数为null");
            }
        }
        return buffer.toString();
    }

    private StringBuffer getBuffer(List<Map<String,Object>> filedInfos, StringBuffer buffer){
        if(buffer==null){
            buffer=new StringBuffer();
        }
        for(Map<String,Object> map:filedInfos){
            String tag=map.get("name").toString();
            if(!"$change".equals(tag)&&!"serialVersionUID".equals(tag)){
                String str=map.get("name")+"="+map.get("value")+"  ";
                buffer.append(str);
            }
        }
        return buffer;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    private List getFiledsInfo(Object obj){
        List<Map<String,Object>> list=new ArrayList();
        if(obj!=null) {
            Field fields[] = obj.getClass().getDeclaredFields();
            Map mapInfo = null;
            for (int i = 0; i < fields.length; i++) {
                String type=fields[i].getType().toString();
                String name=fields[i].getName();
                Object value=getFieldValueByName(name, type,obj);

                mapInfo = new HashMap();
                mapInfo.put("type", type);
                mapInfo.put("name", name);
                mapInfo.put("value",value);
                list.add(mapInfo);
            }
        }
        return list;
    }

    /**
     * 根据属性名获取属性值 
     */
    private Object getFieldValueByName(String fieldName, String type, Object obj){
        Object value=null;
        try {
            String firstLetter=fieldName.substring(0,1).toUpperCase();
            if(type!=null){
                String methodTag="get";
                if("boolean".equalsIgnoreCase(type)) {
                    methodTag = "is";
                }
                String getter=methodTag +firstLetter+fieldName.substring(1);
                Method method=obj.getClass().getMethod(getter,new Class[] {});
                value = method.invoke(obj, new Object[] {});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

}