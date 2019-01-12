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
     * 判断是否为电话号码(粗略判断，第一位数为1，长度为11位)
     *
     * @param phoneNum:电话号码字符串
     * @return
     */
    public static boolean isMobileNO(String phoneNum) {
        String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1，"\\d{10}"代表后面是可以是0～9的数字，有10位。
        return phoneNum.matches(telRegex);
    }

    /**
     * 验证码验证
     *
     * @param code 验证码字符串
     * @param length 规定长度
     * @return
     */
    public static boolean isValidateCode(String code,int length) {
        String telRegex = "\\d{"+length+"}";
        return code.matches(telRegex);
    }

    /**
     * 校验密码（是否为startLength-endLength位数字和字母的组合）
     *
     * @param code
     * @return
     */
    public static boolean isPassWord(String code,int startLength,int endLength) {
        String telRegex = "[0-9A-Za-z]{"+startLength+","+endLength+"}";
        boolean isMix = code.matches(telRegex);
        boolean isResult = false;
        if (isMix) {
            Pattern isLetter = Pattern.compile("[A-Za-z]");
            if (isLetter.matcher(code).find()) {
                Pattern isNumber = Pattern.compile("[0-9]");
                isResult = isNumber.matcher(code).find();
            }

        }
        return isResult;
    }

    /**
     * 验证是否为startLength位到endLength位的数字
     *
     * @param code 要验证的字符串
     * @param startLength 最少的位数
     * @param endLength 最多的位数
     * @return
     */
    public static boolean isLessOrMoreNum(String code,int startLength,int endLength) {
        String telRegex = "\\d{"+startLength+","+endLength+"}";
        return code.matches(telRegex);
    }

    /**
     * 验证桌号(以字母开头，数字结尾)
     *
     * @param tableNo
     * @return
     */
    public static boolean isTableNo(String tableNo) {
        String telRegex = "^[a-zA-Z0-9]+$";
        return tableNo.matches(telRegex);
    }

    /**
     * 验证账号昵称(最短长度为startLength,最长长度为endLength,可以包含字母和中文)
     *
     * @param userName 昵称字符串
     * @param startLength 最短长度
     * @param endLength 最长长度
     * @return
     */
    public static boolean isUserName(String userName,int startLength,int endLength) {
        String userRegex = "^[a-zA-Z\\u4E00-\\u9FA5]{"+startLength+","+endLength+"}";
        return userName.matches(userRegex);
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     *
     * @param cardNum 待检验的原始卡号
     * @return 返回是否包含
     * @author fenggaopan 2015年7月21日 上午9:49:40
     */
    public static boolean isContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    /**
     * 根据规则匹配字符串
     *
     * @param msg 字符串
     * @param regex 设置的规则，如:"^[a-zA-Z0-9]+$"
     * @return
     */
    public static boolean isRegex(String msg,String regex){
        return msg.matches(regex);
    }

}
