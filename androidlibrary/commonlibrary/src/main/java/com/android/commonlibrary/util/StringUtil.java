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

    /**过滤汉字**/
    public static String filterChinese(String chin){
        return chin.replaceAll("[\\u4e00-\\u9fa5]", "");
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