## StreamUtil使用说明

### 概述
`StreamUtil`是一个`IO流`工具类，主要给`IO流`提供关闭的方法，以及各种字符集常量。

### 使用说明
#### 一. 包含的字符集编码格式
`StreamUtil`包含以下几种字符集编码格式:
```
    //编码格式
    public static final String UTF_8="UTF-8";
    public static final String UTF_16="UTF-16";
    public static final String UTF_32="UTF-32";
    public static final String GBK="GBK";//中文简体与繁体
    public static final String GB2312="GB2312";//中文简体
    public static final String GB18030="GB18030";
    public static final String ANSI="ANSI";

```
我们一般常用的就两种：`StreamUtil.UTF_8`,`StreamUtil.GBK`.
#### 二. 主要方法
`StreamUtil`包含以下几个方法,主要用于处理`IO流`相关问题。
```
    /***
     *  关闭流
     * @param in 输入流
     * @param out 输出流
     */
    public static void closeStream(InputStream in, OutputStream out)
    
        /***
     *  关闭流
     * @param in 输入流
     * @param out 输出流
     */
    public static void closeStream(Reader in, Writer out)
    
    /**关闭文件通道**/
    public static void closeStream(FileChannel channel)
    
   /**
     * 判断要读的文件路径是否为有效路径
     *
     * @param filePath 要读出文件的文件路径
     * @return null:表示文件路径无效  file：文件路径有效则返回file对象
     */
    public static File checkReadFilePath(String filePath)
    
   /**
     * 判断将要写入的文件路径是否为有效路径
     *
     * @param filePath 要写入文件的文件路径
     * @return null:表示文件路径无效  file：文件路径有效则返回file对象
     */
    public static File checkWriteFilePath(String filePath)
```

