package com.android.commonlibrary.util.encrypt;

import android.util.Base64;

import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Title: AES加密解密工具类(Aes_128加解密)
 * description:
 * autor:pei
 * created on 2021/5/12
 */
public class Aes128 extends AES{

    //默认填充模式为"AES/ECB/PKCS5Padding"
    private String mFillModel = AES.ECB_PKCS5PADDING;

    private Aes128(){}

    private static class Holder {
        private static Aes128 instance = new Aes128();
    }

    public static Aes128 getInstance() {
        return Holder.instance;
    }

    /***
     * 设置加密解密字符集(不设置的话默认字符集为AES.UTF_8)
     *
     * @param charsetName 字符集,一般为 AES.UTF_8 或 AES.GBK
     * @return
     */
    public Aes128 setCharsetName(String charsetName){
        //字符集
        if (StringUtil.isEmpty(charsetName)) {
            throw new NullPointerException("===字符集charsetName不能为null,一般为 AES.UTF_8 或 AES.GBK ========");
        }
        this.mCharsetName = charsetName;
        return Aes128.this;
    }

    /***
     * 设置填充模式(不设置的话，默认为 AES.ECB_PKCS5PADDING)
     *
     * 注:一般不调用,除非有特别的需要替换填充模式才考虑
     *
     * @return
     */
    public Aes128 setFillModel(String fillModel){
        if (StringUtil.isEmpty(fillModel)) {
            throw new NullPointerException("===填充模式fillModel不能为null========");
        }
        this.mFillModel=fillModel;
        return Aes128.this;
    }

    /***
     * 初始化设置
     *
     * @param key 密钥(长度 >=16 的字符串)
     * @return
     */
    public void init(String key) {
        //密钥
        if (StringUtil.isEmpty(key) || key.length() < 16) {
            throw new SecurityException("=======密钥为null或者密钥长度小于16========");
        }
        this.mKey = key;
    }


    /***
     * 加密(AES_128)
     *
     * @param value: 要加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String value) {
        //打印基本信息
        printInfo(true);
        //检测参数
        check(value);
        //执行逻辑
        try {
            SecretKeySpec sKey = new SecretKeySpec(mKey.getBytes(mCharsetName), CIPHER_ALGORITHM);
            Cipher cipher = Cipher.getInstance(mFillModel);
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            byte[] decrypted = cipher.doFinal(value.getBytes(mCharsetName));
            return new String(Base64.encode(decrypted, Base64.DEFAULT), mCharsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 解密(AES_128)
     *
     * @param strBase64：aes加密后的字符串
     * @return 解密结果
     */
    public String decrypt(String strBase64) {
        //打印基本信息
        printInfo(false);
        //检测参数
        check(strBase64);
        //执行逻辑
        try {
            //先用base64解密
            byte[] sourceBytes = Base64.decode(strBase64.getBytes(mCharsetName), Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(mFillModel);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(mKey.getBytes(mCharsetName), CIPHER_ALGORITHM));
            byte[] decoded = cipher.doFinal(sourceBytes);
            return new String(decoded, mCharsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 检测参数
     *
     * @param value: 要 加密/解密 的字符串
     */
    private void check(String value) {
        //密钥
        boolean keyFlag = StringUtil.isEmpty(mKey) || mKey.length() < 16;
        //字符集
        boolean charsetNameFlag = StringUtil.isEmpty(mCharsetName);
        if (keyFlag || charsetNameFlag) {
            throw new SecurityException("=======请先调用初始化方法:init(String key,String charsetName) =======");
        }
        //判断要 加密/解密 的字符串
        if(StringUtil.isEmpty(value)){
            throw new NullPointerException("=====要 加密/解密 的字符串不能为空======");
        }
    }

    /***
     * 打印加/解密基本信息
     *
     * @param isEnCode true:加密基本信息打印
     *                 false：解密基本信息打印
     */
    private void printInfo(boolean isEnCode){
        String message=isEnCode==true?"加密":"解密";
        LogUtil.w("*******"+message+"基本信息*******");
        LogUtil.w("="+message+"方式: AES_128");
        LogUtil.w("="+message+"模式: "+mFillModel);
        LogUtil.w("="+message+"字符集: "+mCharsetName);
        LogUtil.w("*************************");
    }

}
