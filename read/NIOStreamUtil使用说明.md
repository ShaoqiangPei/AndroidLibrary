## NIOStreamUtil使用说明

### 概述
`NIOStreamUtil`是一个以`NIO`实现数据流操作的`IO流`工具类，可实现文件的`读`、`写`及`复制`功能，效率高于传统的`IO流`。

### 使用说明
#### 一.前置
在使用`NIOStreamUtil`工具之前，你先要给自己的项目添加各种文件读写权限，包括在`Androidmanifast.xml`中添加读写权限，添加`FileProvider`文件读写权限，
然后在你项目中添加`Android 6.0+`手动授权库权限.

二.NIOStreamUtil主要方法
`NIOStreamUtil`具有以下几个重要方法：
```
    /**
     * nio读文件的方式一
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile1(String filePath, String charsetName)
    
   /**
     * nio读文件的方式二
     * @param filePath 文件路径
     * @param charsetName 字符编码格式,如：StreamUtil.UTF_8,StreamUtil.GBK 等.
     *                     此参数为null时，返回编译器默认编码格式
     * @return 读出的文件内容
     */
    public static String readFile2(String filePath, String charsetName) 
    
    /***
     * 将文件读成 byte 数组
     *
     * @param filePath 文件路径
     * @return 文件的字节数组
     */
    public static byte[] getBytes(String filePath)
    
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
    public static boolean writeFile(String message,String filePath,String charsetName,boolean append)  
    
        /**
     * NIO复制文件
     *
     * @param readFilePath 要复制的文件路径
     * @param writeFilePath 新生成的文件路径
     * @return true：复制成功   false:复制失败
     */
    public static boolean copyFile(String readFilePath,String writeFilePath)
```
其中`getBytes(String filePath)`方法也可实现图片类文件的读取，示例如下：
```
    /**读图片**/
    private void readImage(){
        String path="/data/data/com.testdemo/cache/1.jpg";
        byte bytes[]=NIOStreamUtil.getBytes(path);
        if(bytes!=null){
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            mImv.setImageBitmap(bitmap);
        }
    }
```
#### 三. 注意项
- 对于文件读的方法，若读出的内容出现乱码，可以考虑在读文件时，更换字符编码集，常用的字符编码集有: StreamUtil.UTF_8和StreamUtil.GBK
- 对于文件写的方法，我们有注意最后一个参数是一个boolean值，当为true时，表示给文件里面追加内容，当为false时，表示重写文件， 
即写的内容会完全覆盖原文件中的所有内容，这是需要注意的点。
