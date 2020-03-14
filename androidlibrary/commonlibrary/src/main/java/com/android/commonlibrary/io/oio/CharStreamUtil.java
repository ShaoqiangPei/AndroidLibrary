package com.android.commonlibrary.io.oio;

import com.android.commonlibrary.io.StreamUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Title:字符流工具类
 *
 * 注：文件读写需要添加读写权限，包括manifast.xml中和Android6.0+手动权限
 * description:包含字节流转字符流后再读写文件的方法
 *             字符流读写文件的方法
 *             字符流复制文件的方法
 * 提供一个测试路径：String path="/data/data/com.androidlibrary/cache/
 *                此路径做测试可以很方便在在手机上查看。
 * autor:pei
 * created on 2020/3/14
 */
public class CharStreamUtil {

    /***
     * 字节流转字符流读文件
     *
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读取的文件内容
     */
    public static String readFileChange(String filePath,String charsetName){
        //检测要读文件的文件路径有效性
        File file= StreamUtil.checkReadFilePath(filePath);
        if(file==null){
            return null;
        }
        InputStream inputStream=null;
        Reader reader=null;
        try {
            inputStream=new FileInputStream(file);
            if(StringUtil.isNotEmpty(charsetName)) {
                reader = new InputStreamReader(inputStream, charsetName);
            }else{
                reader = new InputStreamReader(inputStream);
            }
            BufferedReader bufferedReader=new BufferedReader(reader);
            StringBuffer buffer=new StringBuffer();
            String content=null;
            while ((content=bufferedReader.readLine())!=null){
                buffer.append(content);
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(inputStream,null);
            StreamUtil.closeStream(reader,null);
        }
        return null;
    }

    /***
     * 字节流转字符流写文件
     * @param message 要写入的内容
     * @param filePath 要写入的文件路径,若文件不存在,会自行创建
     * @param append true:表示在文件中追加内容, false:表示在文件中写新内容,覆盖以前内容
     *
     * @return true:写文件成功  false：写文件失败
     */
    public static boolean writeFileChange(String message,String filePath,boolean append){
        boolean result=false;
        if(StringUtil.isEmpty(message)){
            LogUtil.i("======写入文件的message不能为空======");
            return result;
        }
        File file=StreamUtil.checkWriteFilePath(filePath);
        if(file==null){
            return result;
        }
        OutputStream outputStream=null;
        Writer writer=null;
        try {
            outputStream=new FileOutputStream(file,append);
            writer=new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter=new BufferedWriter(writer);
            bufferedWriter.write(message);
            result=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(null,outputStream);
            StreamUtil.closeStream(null,writer);
        }
        return result;
    }

    /**
     * 字符流读文件
     *
     * @param filePath 文件路径
     * @return 读取的文件内容
     */
    public static String readFile(String filePath){
        //检测要读文件的文件路径有效性
        File file=StreamUtil.checkReadFilePath(filePath);
        if(file==null){
            return null;
        }
        Reader reader=null;
        try {
            reader=new FileReader(file);
            StringBuffer buffer=new StringBuffer();
            int len=0;
            while((len=reader.read())!=-1){
                char c=(char)len;
                buffer.append(c);
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(reader,null);
        }
        return null;
    }

    /***
     * 字符流写文件
     * @param message 要写入的内容
     * @param filePath 要写入的文件路径,若文件不存在,会自行创建
     * @param append true:表示在文件中追加内容, false:表示在文件中写新内容,覆盖以前内容
     *
     * @return true:写文件成功  false：写文件失败
     */
    public static boolean writeFile(String message,String filePath,boolean append){
        boolean result=false;

        if(StringUtil.isEmpty(message)){
            LogUtil.i("======写入文件的message不能为空======");
            return result;
        }
        File file=StreamUtil.checkWriteFilePath(filePath);
        if(file==null){
            return result;
        }
        Writer writer=null;
        try {
            writer=new FileWriter(file,append);
            writer.write(message);
            result=true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(null,writer);
        }
        return result;
    }

    /**
     * 字符流复制文件
     *
     * @param readFilePath 要复制的文件路径
     * @param writeFilePath 新生成的文件路径
     * @return true：复制成功   false:复制失败
     */
    public static boolean copyFile(String readFilePath,String writeFilePath){
        boolean result=false;
        //要复制的文件做检测
        File readFile=StreamUtil.checkReadFilePath(readFilePath);
        if(readFile==null){
            return result;
        }
        //复制生成新文件做检测
        if(readFilePath.equals(writeFilePath)){
            LogUtil.i("======要复制的文件路径不能和新生成的文件路径重复======");
            return result;
        }
        File writeFile=StreamUtil.checkWriteFilePath(writeFilePath);
        if(writeFile==null){
            return result;
        }
        //开始复制文件
        Reader reader=null;
        Writer writer=null;
        try {
            reader = new FileReader(readFile);
            writer = new FileWriter(writeFile);
            int len = 0;
            while ((len = reader.read()) != -1) {
                writer.write((char) len);
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(reader,writer);
        }
        return result;
    }
}
