package com.android.commonlibrary.io.nio;

import com.android.commonlibrary.io.StreamUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Title:NIO文件读写
 *
 * description:jdk1.7+
 * autor:pei
 * created on 2020/3/14
 */
public class NIOStreamUtil {

    /**
     * nio读文件的方式一
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile1(String filePath, String charsetName) {
        //检测要读文件的文件路径有效性
        File file = StreamUtil.checkReadFilePath(filePath);
        if (file == null) {
            return null;
        }
        FileChannel inChannel = null;
        try {
            //创建FileInputStream,已改文件输出流创建 FileChannel
            inChannel = new FileInputStream(file).getChannel();
            //将 FileChannel 里的数据映射成 bytebuffer
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            //创建gbk解码器
            Charset charset = Charset.forName(charsetName);
            //创建解码器对象
            CharsetDecoder decoder = charset.newDecoder();
            //使用解码器对象将 buffer 转成  charbuffer
            CharBuffer charBuffer = decoder.decode(buffer);
            //charBuffer 的 tostring（）方法转成字符串输出
            String content = charBuffer.toString();
            return content;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.i("=======FileNotFoundException===="+e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.i("=======IOException========="+e.getMessage());
            LogUtil.i("======出现此错误时,考虑切换下编码格式再运行看看==");
        } finally {
            StreamUtil.closeStream(inChannel);
        }
        return null;
    }

    /**
     * nio读文件的方式二
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile2(String filePath, String charsetName) {
        byte[]data=getBytes(filePath);
        String content= null;
        if(data!=null) {
            try {
                content = new String(data, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /***
     * 将文件读成 byte 数组
     *
     * @param filePath 文件路径
     * @return 文件的字节数组
     */
    public static byte[] getBytes(String filePath){
        //检测要读文件的文件路径有效性
        File file = StreamUtil.checkReadFilePath(filePath);
        if (file == null) {
            return null;
        }
        FileChannel inChannel=null;
        try {
            //创建FileInputStream,已改文件输出流创建 FileChannel
            inChannel=new FileInputStream(file).getChannel();
            //将 FileChannel 里的数据映射成 bytebuffer
            MappedByteBuffer buffer=inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            byte data[]=new byte[buffer.capacity()];
            buffer.get(data, 0, data.length);
            return data;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(inChannel);
        }
        return null;
    }

    /**
     * NIO 写文件
     *
     * @param message 要写入文件的字符串
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @param append true:表示在文件中追加内容, false:表示在文件中写新内容,覆盖以前内容
     * @return true:写文件成功  false：写文件失败
     */
    public static boolean writeFile(String message,String filePath,String charsetName,boolean append){
        boolean result=false;

        if(StringUtil.isEmpty(message)){
            LogUtil.i("======写入文件的message不能为空======");
            return result;
        }
        File file=StreamUtil.checkWriteFilePath(filePath);
        if(file==null){
            return result;
        }
        FileChannel outChannel=null;
        try {
            outChannel=new FileOutputStream(file,append).getChannel();
            //将 FileChannel 里的数据映射成 bytebuffer
            ByteBuffer buffer=null;
            if(StringUtil.isNotEmpty(charsetName)) {
                buffer = ByteBuffer.wrap(message.getBytes(charsetName));
            }else{
                buffer = ByteBuffer.wrap(message.getBytes());
            }
            buffer.clear();
            outChannel.write(buffer);
            result=true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(outChannel);
        }
        return result;
    }

    /**
     * NIO复制文件
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
        FileInputStream inputStream=null;
        FileOutputStream outputStream=null;
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {
            inputStream=new FileInputStream(readFile);
            outputStream=new FileOutputStream(writeFile);
            inChannel =inputStream.getChannel();
            outChannel=outputStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            result=true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(inputStream,outputStream);
            StreamUtil.closeStream(inChannel);
            StreamUtil.closeStream(outChannel);
        }
        return result;
    }

}
