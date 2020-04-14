package com.android.commonlibrary.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:正则表达式的工具类
 *
 * Description:
 * 部分方法可直接使用，还有部分供参考。
 *
 * Created by pei
 * Date: 2019/1/12
 */
public class RegularUtil {

    /***
     * 判断是否为url
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if(null==str||str.length()==0){
            return false;
        }
        // 转换为小写
        str = str.toLowerCase();
        String[] regex = {"http://", "https://"};
        boolean isUrl = false;
        for (String s : regex) {
            if (str.contains(s)) {
                isUrl = true;
            }
        }
        return isUrl;
    }

    /**
     * 根据规则匹配字符串
     *
     * @param msg 字符串
     * @param regex 设置的规则，如:"^[a-zA-Z0-9]+$"
     * @return
     */
    public static boolean isRegex(String msg, String regex){
        if(StringUtil.isEmpty(msg)){
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        return matcher.matches();
    }

    /**字符串是否全为数字**/
    public static boolean isAllNumber(String msg){
       String regex="^\\d+$";
       return isRegex(msg,regex);
    }

    /**字符串是否含有数字**/
    public static boolean isContainNumber(String msg){
        String regex="^.*\\d.*$";
        return isRegex(msg,regex);
    }

    /**字符串是否不包含数字**/
    public static boolean isNoContainNumber(String msg){
        return !isContainNumber(msg);
    }

    /**字符串是否全为小写字母**/
    public static boolean isAllLowerCaseLetter(String msg){
        String regex="^[a-z]+$";
        return isRegex(msg,regex);
    }

    /**字符串是否含有小写字母**/
    public static boolean isContainLowerCaseLetter(String msg){
        String regex="^.*[a-z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否不含小写字母**/
    public static boolean isNoContainLowerCaseLetter(String msg){
        return !isContainLowerCaseLetter(msg);
    }

    /**字符串是否全为大写字母**/
    public static boolean isAllCapitalLetter(String msg){
        String regex="^[A-Z]+$";
        return isRegex(msg,regex);
    }

    /**字符串是否含有大写字母**/
    public static boolean isContainCapitalLetter(String msg){
        String regex="^.*[A-Z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否不含大写字母**/
    public static boolean isNoContainCapitalLetter(String msg){
        return !isContainCapitalLetter(msg);
    }

    /**字符串是否全为字母**/
    public static boolean isAllLetter(String msg){
        String regex="^[a-zA-Z]+$";
        return isRegex(msg,regex);
    }

    /**字符串是否含有字母**/
    public static boolean isContainLetter(String msg){
        String regex="^.*[a-zA-Z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否不含字母**/
    public static boolean isNoContainLetter(String msg){
        return !isContainLetter(msg);
    }

    /**字符串是否全为中文**/
    public static boolean isAllChinese(String msg){
        String regex="^[\\u4e00-\\u9fa5]+$";
        return isRegex(msg,regex);
    }

    /**字符串是否含有中文**/
    public static boolean isContainChinese(String msg){
        String regex="^.*[\\u4e00-\\u9fa5].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否不含中文**/
    public static boolean isNoContainChinese(String msg){
        return !isContainChinese(msg);
    }

    /**
     * 字符串是否含有 temp字符串
     *
     * @param msg
     * @param temp
     * @return
     */
    public static boolean isContainX(String msg,String temp){
        String regex="^.*["+temp+"].*$";
        return isRegex(msg,regex);
    }

    /**
     * 字符串是否含有小数点
     *
     * @param msg
     * @return
     */
    public static boolean isContainPoint(String msg){
        return isContainX(msg,".");
    }

    /**字符串是否以数字开头**/
    public static boolean isStartByNumber(String msg){
        String regex="^\\d.*$";
        return isRegex(msg,regex);
    }

    /**字符串是否以数字结尾**/
    public static boolean isEndByNumber(String msg){
        String regex="^.*\\d$";
        return isRegex(msg,regex);
    }

    /**字符串是否以小写字母开头**/
    public static boolean isStartByLowerCaseLetter(String msg){
        String regex="^[a-z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否以小写字母结尾**/
    public static boolean isEndByLowerCaseLetter(String msg){
        String regex="^.*[a-z]$";
        return isRegex(msg,regex);
    }

    /**字符串是否以大写字母开头**/
    public static boolean isStartByCapitalLetter(String msg){
        String regex="^[A-Z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否以大写字母结尾**/
    public static boolean isEndByCapitalLetter(String msg){
        String regex="^.*[A-Z]$";
        return isRegex(msg,regex);
    }

    /**字符串是否以字母开头**/
    public static boolean isStartByLetter(String msg){
        String regex="^[a-zA-Z].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否以字母结尾**/
    public static boolean isEndByLetter(String msg){
        String regex="^.*[a-zA-Z]$";
        return isRegex(msg,regex);
    }

    /**字符串是否以中文开头**/
    public static boolean isStartByChinese(String msg){
        String regex="^[\\u4e00-\\u9fa5].*$";
        return isRegex(msg,regex);
    }

    /**字符串是否以中文结尾**/
    public static boolean isEndByChinese(String msg){
        String regex="^.*[\\u4e00-\\u9fa5]$";
        return isRegex(msg,regex);
    }


    /**
     * 字符串长度是否为n
     * @param msg
     * @param n  验证长度
     * @return
     */
    public static boolean isLengthN(String msg,int n){
        String regex="^.{"+n+"}";
        return isRegex(msg,regex);
    }

    /**
     * 字符串长度至少为n
     * @param msg
     * @param n  验证最少长度
     * @return
     */
    public static boolean isLengthAtMinN(String msg,int n){
        String regex="^.{"+n+",}";
        return isRegex(msg,regex);
    }

    /**
     * 字符串长度最大为n
     * @param msg
     * @param n  验证最大长度
     * @return
     */
    public static boolean isLengthAtMaxN(String msg,int n){
        String regex="^.{0,"+n+"}";
        return isRegex(msg,regex);
    }

    /***
     * 字符串长度是否在 min-max 之间
     * @param msg
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return
     */
    public static boolean isLengthMainAndMax(String msg, int minLength, int maxLength) {
        String regex = "^.{" + minLength + "," + maxLength + "}";
        return isRegex(msg,regex);
    }


}
