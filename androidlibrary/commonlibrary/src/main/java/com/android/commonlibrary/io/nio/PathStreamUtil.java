package com.android.commonlibrary.io.nio;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.commonlibrary.io.StreamUtil;
import com.android.commonlibrary.util.CollectionUtil;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Title:NIO之Path实现文件快速处理
 *
 * 注:需要 android O 的API,因为需要版本太高，此处制作了解之用。版本低的手机调用会导致崩溃。
 * description:jdk1.7+
 * autor:pei
 * created on 2020/3/14
 */
public class PathStreamUtil {

    /**
     * 按行读文件
     *
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 所有行文字集合,运行错误时返回 null
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<String> readFile(String filePath, String charsetName){
        if(StringUtil.isEmpty(charsetName)){
            LogUtil.i("====字符编码不能为空======");
            return null;
        }
        //检测要读文件的文件路径有效性
        File file = StreamUtil.checkReadFilePath(filePath);
        if (file == null) {
            return null;
        }
        Path path=Paths.get(filePath);
        try {
            List<String> lines=Files.readAllLines(path,Charset.forName(charsetName));
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * path实现写文件[不含文件追加功能,会覆盖之前内容]
     *
     * @param list 要写入文件的字符串集合
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return true:写文件成功  false：写文件失败
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean writeFile(List<String>list, String filePath, String charsetName){
        boolean result=false;
        if(CollectionUtil.isEmpty(list)){
            LogUtil.i("======写入文件的message不能为空======");
            return result;
        }
        File file= StreamUtil.checkWriteFilePath(filePath);
        if(file==null){
            return result;
        }
        Path path= Paths.get(filePath);
        try {
            Files.write(path, list, Charset.forName(charsetName));
            result=true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Path复制文件
     *
     * @param readFilePath 要复制的文件路径
     * @param writeFilePath 新生成的文件路径
     * @return true：复制成功   false:复制失败
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean copyFile(String readFilePath, String writeFilePath){
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
        //开始复制
        Path path=Paths.get(readFilePath);
        OutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream(writeFile);
            Files.copy(path, outputStream);
            result=true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            StreamUtil.closeStream(null,outputStream);
        }
        return result;
    }

}
