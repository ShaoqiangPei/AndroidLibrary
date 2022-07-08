package com.android.commonlibrary.io;

import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;

/**
 * Title:IO流操作工具
 * description:
 * autor:pei
 * created on 2020/3/12
 */
public class StreamUtil {

    //编码格式
    public static final String UTF_8="UTF-8";
    public static final String UTF_16="UTF-16";
    public static final String UTF_32="UTF-32";
    public static final String GBK="GBK";//中文简体与繁体
    public static final String GB2312="GB2312";//中文简体
    public static final String GB18030="GB18030";
    public static final String ANSI="ANSI";

    /***
     *  关闭流
     * @param in 输入流
     * @param out 输出流
     */
    public static void closeStream(InputStream in, OutputStream out){
        if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(out!=null){
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     *  关闭流
     * @param in 输入流
     * @param out 输出流
     */
    public static void closeStream(Reader in, Writer out){
        if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(out!=null){
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**关闭文件通道**/
    public static void closeStream(FileChannel channel){
        if(channel!=null){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 判断要读的文件路径是否为有效路径
     *
     * @param filePath 要读出文件的文件路径
     * @return null:表示文件路径无效  file：文件路径有效则返回file对象
     */
    public static File checkReadFilePath(String filePath){
        if(StringUtil.isEmpty(filePath)){
            LogUtil.i("======文件路径不能为空======");
            return null;
        }
        File file=new File(filePath);
        if(!file.exists()){
            LogUtil.i("======文件不存在(请检查文件路径或文件名是否正确)======");
            return null;
        }
        if(!file.isFile()){
            LogUtil.i("======给定路径获取的不是文件(可能是文件夹路径?),请检查======");
            return null;
        }
        return file;
    }

    /**
     * 判断将要写入的文件路径是否为有效路径
     *
     * @param filePath 要写入文件的文件路径
     * @return null:表示文件路径无效  file：文件路径有效则返回file对象
     */
    public static File checkWriteFilePath(String filePath){
        if(StringUtil.isEmpty(filePath)){
            LogUtil.i("======文件路径不能为空======");
            return null;
        }
        //若文件路径中含不存在的文件夹,则需要提前创建
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!file.isFile()){
            LogUtil.i("======文件路径错误(可能是文件夹路径),请检查======");
            return null;
        }
        return file;
    }

}
