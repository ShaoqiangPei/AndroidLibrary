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
    public static String formatXNum(double f,int n) {
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
    public static String formatKmDistance(double distance,int n) {
        if (distance < 1000) {
            return formatXNumByCompute(distance,n) + "m";
        }
        return formatXNumByCompute(distance / 1000,n) + "km";
    }

    /**去掉数字后面多余的0**/
    public static String removeZeroNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

}
