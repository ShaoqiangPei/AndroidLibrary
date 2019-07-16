package com.android.commonlibrary.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:分组工具类
 *
 * Author:pei
 * Date: 2019/7/6
 */
public class GroupUtil {

    /**值为null时，默认key**/
    public static final String DEFAULT_NULL_KEY="NULL";

    /**
     * 分组方法
     *
     * 若属性值为 null,则分组的 key=GroupUtil.DEFAULT_NULL_KEY
     */
    public static <T>Map<String, List<T>> getListGroup(List<T> list,String fieldName) {
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        if(StringUtil.isEmpty(fieldName)){
            return null;
        }
        HashMap<String, List<T>> ret = new HashMap<>();
        //同名的分为一组
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Object parameter=getFieldValueByName(fieldName,t);
            String parameterKey=null;
            if(parameter==null|| StringUtil.isEmpty(parameter.toString())){
                parameterKey=DEFAULT_NULL_KEY;
            }else if(parameter instanceof Integer){
                parameter=(int)parameter;
                //int转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof Float){
                parameter=(float) parameter;
                //float转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof Double){
                parameter=(double) parameter;
                //double转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof Long){
                parameter=(long) parameter;
                //long转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof Byte){
                parameter=(byte) parameter;
                //byte转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof Character){
                parameter=(char) parameter;
                //char转String作为key
                parameterKey=String.valueOf(parameter);
            }else if(parameter instanceof String){
                parameter=(String) parameter;
                //String转String作为key
                parameterKey=parameter.toString();
            }else if(parameter instanceof Boolean) {
                parameter = (boolean) parameter;
                //boolean转String作为key
                parameterKey=String.valueOf(parameter);
            }else{
                String classCastMessage="====类型转换错误:parameter="+parameter;
                LogUtil.e(classCastMessage);
                return null;
            }
            //如果不存在实体字段的键，则创建一个list，并将list添加至map
            if (!ret.containsKey(parameterKey)) {
                ArrayList<T> li = new ArrayList<>();
                li.add(t);
                ret.put(parameterKey, li);
            } else {
                //如果存在，则直接在list中添加一个即可
                ret.get(parameterKey).add(t);
            }
        }
        return ret;
    }

    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName,Object obj) {
        Object value = null;
        if (obj != null) {
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method = obj.getClass().getMethod(getter, new Class[]{});
                value = method.invoke(obj, new Object[]{});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
