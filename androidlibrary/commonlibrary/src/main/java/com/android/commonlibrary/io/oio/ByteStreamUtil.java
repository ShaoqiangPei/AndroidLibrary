package com.android.commonlibrary.io.oio;

import com.android.commonlibrary.io.StreamUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Title:字节流工具类
 *
 * 注：文件读写需要添加读写权限，包括manifast.xml中和Android6.0+手动权限
 * description: 1.字节流读文件固定了读缓冲区数组。
 *                可能出现读出内容乱码的原因：
 *                1.1 一个个字节读,当涉及汉字的时候，可能因为把汉字拆成一半来读导致乱码
 *                1.2 所读内容太少，不够填充读缓冲区时，可能会出现读出内容后结尾出现乱码
 *              2.写文件添加了IO流增强类，写文件快,不会出现乱码
 *              3.复制文件可用
 * 提供一个测试路径：String path="/data/data/com.androidlibrary/cache/
 *                此路径做测试可以很方便在在手机上查看。
 * autor:pei
 * created on 2020/3/12
 */
public class ByteStreamUtil {

    /**
     * 字节流读文件
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile(String filePath,String charsetName){
        //检测要读文件的文件路径有效性
        File file= StreamUtil.checkReadFilePath(filePath);
        if(file==null){
            return null;
        }
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(file);
            byte bytes[]=new byte[1024];
            int len=0;
            StringBuffer buffer=new StringBuffer();
            while ((len=inputStream.read(bytes))!=-1){
                String s=null;
                if(StringUtil.isNotEmpty(charsetName)){
                    s=new String(bytes,charsetName);
                }else{
                    //未设置编码格式则默认使用开发工具设置的编码格式
                    s=new String(bytes,0,len);
                }
                buffer.append(s);
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(inputStream,null);
        }
        return null;
    }

    /***
     * 字节流写文件
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
        OutputStream outputStream=null;
        PrintStream printStream=null;
        try {
            outputStream=new FileOutputStream(file,append);
            printStream=new PrintStream(outputStream);//io流写加强[针对文字]
            printStream.println(message);
            result=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(null,outputStream);
            StreamUtil.closeStream(null,printStream);
        }
        return result;
    }

    /**
     * 字节流复制文件
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
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            inputStream=new FileInputStream(readFile);
            outputStream=new FileOutputStream(writeFile);
            byte bytes[]=new byte[1024];
            int len=0;
            while ((len=inputStream.read(bytes))!=-1){
                //每读取一次,立刻写入文件输出流,读多少写多少
                outputStream.write(bytes,0,len);
            }
            result=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(inputStream,outputStream);
        }
        return result;
    }

}
