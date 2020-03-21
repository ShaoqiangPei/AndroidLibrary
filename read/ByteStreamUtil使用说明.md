## ByteStreamUtil使用说明

### 概述
`ByteStreamUtil`是一个用于处理文本类型的`IO流`工具类，主要包含文本类文件的`读`,`写`和`复制`功能。以字节流的方式实现。

### 使用说明
#### 一. 前置
在使用`ByteStreamUtil`工具之前，你先要给自己的项目添加各种文件读写权限，包括在`Androidmanifast.xml`中添加读写权限，添加`FileProvider`文件读写权限，
然后在你项目中添加`Android 6.0+`手动授权库权限.
#### 二. ByteStreamUtil主要方法
`ByteStreamUtil`具有以下几个重要方法：
```
    /**
     * 字节流读文件
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile(String filePath,String charsetName)

    /***
     * 字节流写文件
     * @param message 要写入的内容
     * @param filePath 要写入的文件路径,若文件不存在,会自行创建
     * @param append true:表示在文件中追加内容, false:表示在文件中写新内容,覆盖以前内容
     *
     * @return true:写文件成功  false：写文件失败
     */
    public static boolean writeFile(String message,String filePath,boolean append)

    /**
     * 字节流复制文件
     *
     * @param readFilePath 要复制的文件路径
     * @param writeFilePath 新生成的文件路径
     * @return true：复制成功   false:复制失败
     */
    public static boolean copyFile(String readFilePath,String writeFilePath)
```
#### 三. 注意项
- 对于文件读的方法，若读出的内容出现乱码，可以考虑在读文件时，更换字符编码集，常用的字符编码集有: `StreamUtil.UTF_8`和`StreamUtil.GBK`
- 对于文件写的方法，我们有注意最后一个参数是一个boolean值，当为true时，表示给文件里面追加内容，当为false时，表示重写文件，即写的内容会完全覆盖原文件中的所有内容，这是需要注意的点。


