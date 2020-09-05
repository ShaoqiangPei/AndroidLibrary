package com.android.commonlibrary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密，只能单向加密，不能解密
 * 
 * @author pei
 * @create 2016-6-13
 *
 */
public class MD5Util {

    /***
     * 加密成 32 位 MD5字符串
     *
     * @param str 要加密的字符串
     * @return
     */
    public static String getMD5(String str) {
        if(str!=null){
            return getMD5(str.getBytes());
        }
        return "";
    }

    /***
     *  加密成 32 位 MD5字符串
     * @param data 要加密的字节数组
     * @return
     */
    public static String getMD5(byte data[]){
        if(data!=null){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(data);
                return getString2(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getString2(byte[] hash) {
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
