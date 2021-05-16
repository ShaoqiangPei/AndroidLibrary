package com.android.commonlibrary.util.encrypt;

/**
 * Title: AES加密解密基类
 *
 * description:
 * autor:pei
 * created on 2021/5/12
 */
public class AES {

    /**字符集**/
    public static final String UTF_8 = "utf-8";
    public static final String GBK = "gbk";

    /**算法/模式/填充**/
    //16字节加密后数据长度变为16,不支持不满16字节的加密
    public static final String CBC_NOPADDING="AES/CBC/NoPadding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String CBC_PKCS5PADDING="AES/CBC/PKCS5Padding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String CBC_ISO10126PADDING="AES/CBC/ISO10126Padding";

    //16字节加密后数据长度变为16,不满16字节加密后长度为原始数据长度
    public static final String CFB_NOPADDING="AES/CFB/NoPadding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String CFB_PKCS5PADDING="AES/CFB/PKCS5Padding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String CFB_ISO10126PADDING="AES/CFB/ISO10126Padding";

    //16字节加密后数据长度变为16,不支持不满16字节的加密
    public static final String ECB_NOPADDING="AES/ECB/NoPadding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String ECB_PKCS5PADDING="AES/ECB/PKCS5Padding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String ECB_ISO10126PADDING="AES/ECB/ISO10126Padding";

    //16字节加密后数据长度变为16,不满16字节加密后长度为原始数据长度
    public static final String OFB_NOPADDING="AES/OFB/NoPadding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String OFB_PKCS5PADDING="AES/OFB/PKCS5Padding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String OFB_ISO10126PADDING="AES/OFB/ISO10126Padding";

    //16字节加密后数据长度变为16,不支持不满16字节的加密
    public static final String PCBC_NOPADDING="AES/PCBC/NoPadding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String PCBC_PKCS5PADDING="AES/PCBC/PKCS5Padding";

    //16字节加密后数据长度变为32,不满16字节加密后长度为16字节
    public static final String PCBC_ISO10126PADDING="AES/PCBC/ISO10126Padding";


    /**采用AES加密算法**/
    protected static final String CIPHER_ALGORITHM = "AES";

    //私钥,AES固定格式为 128/192/256 bits.即：16/24/32 bytes
    protected String mKey = null; //密钥
    protected String mCharsetName = AES.UTF_8; //默认字符集为AES.UTF_8


}
