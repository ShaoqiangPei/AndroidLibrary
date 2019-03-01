package com.android.commonlibrary.util.sp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.commonlibrary.app.ComContext;
import com.android.commonlibrary.util.AppUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 存储信息的工具类
 *
 * @author pei
 * @created 2016-6-13
 * @description: 可读取的数据类型有-<code>boolean、int、float、long、String,List.</code> 存储单个String时自带加密
 * <p>
 * 需要读写权限： <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE">
 * </uses-permission>
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE">
 * </uses-permission>
 */
public class SpUtil {

//    private static final String FILE_NAME = "order_form_data";// 保存在手机里面的文件名
    private static final String FILE_NAME = getDefaultFileName();// 保存在手机里面的文件名
//    private static final String MAK = "f337eface810bd25"; // AES加密的密钥
    private static String MAK = null; // AES加密的密钥
    private static final int MODE = Context.MODE_PRIVATE;// 保密等级

    /**
     * 将包名拆分为默认文件名,形式为：包全称_data(若包中含"."替换成"_")
     * 类似于：com_test_data
     * @return
     */
    private static String getDefaultFileName(){
        String name= AppUtil.getPackageName();
        if(StringUtil.isNotEmpty(name)&&name.contains(".")){
            name=name.replace(".","_")+"_data";
        }
        return name;
    }

    /**
     * 设置加密key
     *
     * @param mak:为16位数字字母混合字符串
     */
    public static void setMAK(String mak){

        if(StringUtil.isEmpty(mak)||mak.length()<16){
            Log.e("SpUtil_setMAK_error","设置秘钥失败,请设置16位数字与字母混合长度的秘钥");
            return;
        }
        MAK=mak;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     **/
    public static void put(String key, Object object){
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();

        if (object == null) {
            editor.remove(key);
        } else if (object instanceof String) {
            if(StringUtil.isEmpty(MAK)){
                Log.e("SpUtil_put_error","存储失败,请在存储前设置存储秘钥");
                return;
            }
            String value = (String) object;
            try {
                editor.putString(key, encrypt(MAK, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 存储对象，对象需要实现Serializable接口
     **/
    public static void putObject(String key, Object object) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();
        if (object == null) {
            editor.remove(key);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                editor.putString(key, objectStr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (oos != null) {
                        oos.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获取对象
     **/
    public static Object getObject(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        try {
            String wordBase64 = sp.getString(key, "");
            // 将base64格式字符串还原成byte数组
            if (wordBase64 == null || wordBase64.equals("")) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            // 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将复杂对象转换成json数据储存,需要配合gson使用
     **/
    public static void putJsonObject(String key, Object obj) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();
        if(obj==null){
            editor.remove(key);
        }else{
            if(StringUtil.isEmpty(MAK)){
                Log.e("SpUtil_error","存储失败,请在存储前设置秘钥");
                return;
            }
            Gson gson =new Gson();
            String objStr=gson.toJson(obj);
            try {
                editor.putString(key, encrypt(MAK,objStr));
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.i("=====SpUtil存储putJsonObject错误====="+e.getMessage());
            }
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 取JsonObject，需要配合gson使用
     * @param key
     * @return
     */
    public static String getJsonObject(String key){
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        String str = null;
        if(StringUtil.isEmpty(MAK)){
            Log.e("SpUtil_error","获取失败,请在调用SpUtil类方法前先前设置秘钥");
            return str;
        }
        try {
            str = sp.getString(key, null);
            if (!TextUtils.isEmpty(str)){
                str=decrypt(MAK,str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }





    /**
     * 取String，默认取null
     **/
    public static String getString(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        String str = null;
        if(StringUtil.isEmpty(MAK)){
            Log.e("SpUtil_getString_error","获取失败,请在调用SpUtil类方法前先前设置秘钥");
            return str;
        }
        try {
            str = sp.getString(key, null);
            if (!TextUtils.isEmpty(str)) {
                str=decrypt(MAK,str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 取int，默认取0
     **/
    public static int getInt(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getInt(key, 0);
    }

    /**
     * 取int，默认取number
     **/
    public static int getInt(String key, int number){
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getInt(key, number);
    }

    /**
     * 取float，默认取0f
     **/
    public static float getFloat(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getFloat(key, 0f);
    }

    /**
     * 取float，默认取number
     **/
    public static float getFloat(String key, float number) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getFloat(key, 0f);
    }

    /**
     * 取long，默认取0L
     **/
    public static long getLong(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getLong(key, 0L);
    }

    /**
     * 取long，默认取number
     **/
    public static long getLong(String key, long number) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getLong(key, 0L);
    }

    /**
     * 取boolean，默认取false
     **/
    public static boolean getBoolean(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getBoolean(key, false);
    }

    /**
     * 存list
     **/
    public static void putList(String keyName, List<?> list) {
        int size = list.size();
        if (size < 1) {
            return;
        }
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                editor.putString(keyName + i, (String) list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Long) list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                editor.putFloat(keyName + i, (Float) list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Integer) list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                editor.putBoolean(keyName + i, (Boolean) list.get(i));
            }
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取list
     **/
    public static Map<String, ?> getMap(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        return sp.getAll();
    }

    /**
     * 移除某个key对应的value
     **/
    public static void removeKey(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     **/
    public static void clear() {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     **/
    public static boolean contains(String key) {
        SharedPreferences sp = ComContext.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     **/
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         **/
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit,apply方法是异步的，比同步的commit方法好
         **/
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {

            }
            editor.commit();
        }
    }

// ============================ AES 加密相关 =========================================
    /**
     * AES加密
     */
    public static String encrypt(String key, String value) throws Exception {
        byte[] rawKey = getRawKey(key.getBytes());
        byte[] result = encrypt(rawKey, value.getBytes());
        return toHex(result);
    }

    /**
     * AES解密
     */
    public static String decrypt(String key, String encrypted) throws Exception {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(encrypted)) {
            return null;
        }
        byte[] rawKey = getRawKey(key.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        byte[] raw=null;
        if(Build.VERSION.SDK_INT>=28) {
            raw=InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(seed, 32);
        }else{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom sr = null;
            if (Build.VERSION.SDK_INT >= 17) {
                sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
            } else {
                sr = SecureRandom.getInstance("SHA1PRNG");
            }
            sr.setSeed(seed);
            kgen.init(128, sr); // 192 and 256 bits may not be available
            SecretKey skey = kgen.generateKey();
            raw = skey.getEncoded();
        }
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        if (raw == null || clear == null) {
            return null;
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        if (StringUtil.isEmpty(hexString)) {
            return null;
        }
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    static final class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

}