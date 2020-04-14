package com.android.commonlibrary.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Title:格式化数字
 * Description:
 * <p>
 * Created by pei
 * Date: 2017/9/1
 */
public class FormatUtil {

    /**截取小数点后两位**/
    public static String format2Num(double f){
        return formatXNum(f,2);
    }

    /**保留两位小数，四舍五入**/
    public static double format2NumByCompute(double f){
        return formatXNumByCompute(f,2);
    }

    /**
     * 截取小数点后 n 位小数
     *
     * @param n 为 int 类型
     * @return
     */
    public static String formatXNum(double f, int n) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(n);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(f);
    }

    /**保留几位小数(四舍五入)**/
    public static double formatXNumByCompute(double f,int i){
        BigDecimal b=new BigDecimal(f);
        double f1=b.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     *  格式化距离为 km/m 数
     *
     *  保留一位小数的eg：504 转化后为 504.0m
     *                  1504 转化后为 1.5km
     *
     * @param distance 距离
     * @param n 四舍五入 保留n位小数
     * @return
     */
    public static String formatKmDistance(double distance, int n) {
        if (distance < 1000) {
            return formatXNumByCompute(distance,n) + "m";
        }
        return formatXNumByCompute(distance / 1000,n) + "km";
    }

    /**去掉字符串前面的零**/
    public static String removeZeroBeforeStr(String str){
        if(StringUtil.isNotEmpty(str)){
            String newStr = str.replaceAll("^(0+)", "");
            return newStr;
        }
        return null;
    }

    /***
     * 去掉字符串尾部的零
     *
     * 能去掉字符串尾部的零，但是不能去掉尾部的小数点。
     * 如 5000 会变成 5，但是 50.0 会变成 50.
     * @param str
     * @return
     */
    public static String removeZeroAfterStr(String str){
        if(StringUtil.isNotEmpty(str)){
            String newStr = str.replaceAll("(0+)$", "");
            return newStr;
        }
        return null;
    }

    /***
     * 去掉数字后面多余的0
     *
     * 主要去掉数字型字符串尾部用于保持精度的零。
     * 如 50.00 会变成 50
     *
     * @param number
     * @return
     */
    public static String removeZeroNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    /**除掉输入数值以0开头的情况,如输入“05”，则变成“5”**/
    public static String getCountString(String countStr){
        if(StringUtil.isNotEmpty(countStr)) {
            String tempStr = countStr.trim();
            if(isAllZeroOrPoint(tempStr)){
                return "0";
            }
            String value=removeZeroBeforeStr(tempStr).trim();
            if(value.startsWith(".")){
                value="0"+value;
            }
            return value;
        }
        return "0";
    }

    /**判断字符串是否只含0或者.或者是0和.混合*/
    private static boolean isAllZeroOrPoint(String str){
        if(StringUtil.isEmpty(str)){
            return false;
        }
        char charArray[]=str.toCharArray();
        for(char c:charArray){
            if(c!='0'&&c!='.'){
                return false;
            }
        }
        return true;
    }

}
