package com.android.commonlibrary.util;


import android.text.TextUtils;

import java.util.List;

/**
 * Title:处理String的工具类
 *
 * Description:
 *
 * Created by pei
 * Date: 2019/1/12
 */
public class StringUtil {

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || input.trim().length() == 0 || input.equals("null"))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 规定字符串长度,不足规定长度的，左补零，一般针对数字型字符串
     *
     * @param str：原字符串
     * @param strLength：规定长度
     * @return
     */
    public static String addZeroForString(String str, int strLength){
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength){
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**去掉字符串前面的零**/
    public static String removeZeroBeforeStr(String str){
        if(StringUtil.isNotEmpty(str)){
            String newStr = str.replaceAll("^(0+)", "");
            return newStr;
        }
        return null;
    }

    /**
     * 按位数以 分隔符 将原字符串分割成几段
     *
     * @param s    原始字符串
     * @param tag  分隔符
     * @param each 按几位分割
     * @return
     */
    public static String divide(String s, String tag, int each) {
        StringBuffer buf = new StringBuffer();
        int length = s.length();
        int start = 0;
        int end = length / each == 0 ? length : each;
        while (start < end) {
            buf.append(s.substring(start, end));
            buf.append(tag);
            start += each;
            if (end + each <= length) {
                end += each;
            } else {
                end = length;
            }
        }
        if (buf.toString().contains(tag)) {
            buf.deleteCharAt(buf.toString().lastIndexOf(tag));
        }
        return buf.toString();
    }

    /***
     * 字符串中插入数据
     *
     * @param message 要处理的字符串
     * @param count 每隔 count 个char进行插入
     * @param tag 要插入的字符串(可以为空)
     * @return
     *
     * 举例：
     *     以 String message=“abcde”,插入tag="X"为例：
     *     count=0,得到结果为：XaXbXcXdXeX
     *     count=1,得到结果为：aXbXcXdXeX
     *     count=2,得到结果为：abXcdXe
     *     count=3,得到结果为：abcXde
     *     count=4,得到结果为：abcdXe
     *     count=5,得到结果为：abcdeX
     */
    public static String insertTag(String message,int count,String tag){
        //非空判断
        if(isEmpty(message)){
            LogUtil.e("=====要插入的message不能为null======");
            return null;
        }
        //count范围
        int max=message.length();
        if(count<0||count>max){
            LogUtil.e("=====count需在[0,"+max+"]之间======");
            return null;
        }
        //名称中间插入空格
        String split = "(.{"+count+"})";
        message = message.replaceAll(split, "$1"+tag);
        return message;
    }

    /**
     * 将字符串list集合转换为String，使用tag连接
     *
     * @param list
     * @param tag
     * @return
     */
    public static String convert(List<String> list, String tag) {
        StringBuffer buffer = new StringBuffer();
        for (String s : list) {
            buffer.append(s);
            buffer.append(tag);
        }
        if (buffer.toString().contains(tag)) {
            buffer.deleteCharAt(buffer.lastIndexOf(tag));
        }
        return buffer.toString();
    }


    /***
     * 将字符串按length分组成字符串数组
     *
     * @param s
     * @param length
     * @return
     */
    public static String[] subString(String s, int length) {
        if (TextUtils.isEmpty(s)) return new String[0];
        s = s.trim(); /*10个字分一组*/
        int size = s.length();
        if (size < length) return new String[]{s};
        String[] str; /*判断是不是被10整除*/
        if (size % length == 0) str = new String[size / length];
        else str = new String[(size / length + 1)];
        for (int i = 0; i < str.length; i++) {
            int sizeLn = size % length;
            if (sizeLn == 0) str[i] = s.substring(i * length, i * length + length);
            else if (i == str.length - 1) str[i] = s.substring(i * length, i * length + sizeLn);
            else str[i] = s.substring(i * length, i * length + length);
        }
        return str;
    }

    /**
     * 统计某个字符在字符串中出现的次数
     *
     * @param message 目标字符串
     * @param sub 目标子字符串
     * @return
     */
    public static int countMatched(String message, String sub){
        //判空，为空直接返回0，表示不存在
        if(isEmpty(message)||isEmpty(sub)){
            return 0;
        }
        int count = 0;
        int index;
        // 循环一次将出现的字符串索引向后移一位，计数器+1，并截取原字符串索引+1的字符串，
        // 如“abcdeeeee”第一次循环找到“e”索引4，substring(4+1)方法截取后得到新的字符串“eeee”，
        // 循环第二次找到“e”的索引0，substring(0+1)方法截取后得到新的字符串“eee”，，，以此类推
        while((index = message.indexOf(sub)) > -1){
            count++;
            message = message.substring(index + 1);
        }
        return count;
    }

    /**过滤汉字**/
    public static String filterChinese(String chin){
        return chin.replaceAll("[\\u4e00-\\u9fa5]", "");
    }

    /***
     * 字符串中提取数字
     *
     * @param message 字符串
     * @return null: 表示 message 中不含数字
     *        非null: 返回数字字符串,如 message=" 我23A7",返回字符串 "237"
     */
    public static String getNumberFromStr(String message){
        String tempStr="";
        if(isNotEmpty(message)){
            for(char c:message.toCharArray()){
                if(c>=48&&c<=57){
                    tempStr = tempStr + String.valueOf(c);
                }
            }
        }
        if(isNotEmpty(tempStr)){
            tempStr=tempStr.trim();
        }else{
            tempStr=null;
        }
        return tempStr;
    }


    /**
     * 字符串转换为16进制字符串
     **/
    public static String stringToHexString(String s) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = s.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转字符串
     *
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
        }
        return temp;
    }

}