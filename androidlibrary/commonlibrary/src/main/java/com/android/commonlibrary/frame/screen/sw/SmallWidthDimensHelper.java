package com.android.commonlibrary.frame.screen.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: 生成Android布局分辨率(最小宽度适配smallWidth)
 *
 * description: 使用说明：
 *              Android项目中建一个Javamodule,将此类拷贝进去
 *              然后在 main(String[] args) 中执行以下代码生成 values文件夹:
 *
 *         SmallWidthDimensHelper helper=new SmallWidthDimensHelper();
 *         helper.setBase(1080,1920) //设计图基准宽高
 *                 .setDefaultScale(1.0f) //默认缩放比
 *                 .createSmallWidthDimens(); //最小宽度适配
 *
 *
 * autor:pei
 * created on 2022/6/6
 */
public class SmallWidthDimensHelper {

    //基准宽度和高度(通常设置成UI图的分辨率的高度和宽度)
    private static final float DEFAULT_SCALE=1f;

    private int mBaseWidth;
    private int mBaseHeight;
    private float mScale;

    //生成Dimens文件的路径(项目的res文件夹)
    private String FILE_PATH;
    private static String PX_TEMPLATE = "<dimen name=\"dp_{0}\">{1}dp</dimen>\n";

    /**设置ui设计图基准分辨率宽高**/
    public SmallWidthDimensHelper setBase(int baseWidth, int baseHeight){
        this.mBaseWidth=baseWidth;
        this.mBaseHeight=baseHeight;
        return this;
    }

    /**设置默认value包中dp缩放比例**/
    public SmallWidthDimensHelper setDefaultScale(float scale){
        this.mScale=scale;
        return this;
    }

    /**宽度适配方案**/
    public void createSmallWidthDimens() {
        // 获取项目res文件的路径
        getResPath(new File("").getAbsolutePath());
        List<Integer>list=initScreenDimen();
        for (Integer width:list) {
            createDimensByBase(width);
        }
        //默认分辨率设备的通用dimen文件
        createDefaultDimens(mScale);
    }


    /**
     * 生成相应分辨率的文件
     *
     * @param dimenWidth 要生成相应分辨率宽
     */
    private void createDimensByBase(int dimenWidth) {
        if(mBaseWidth<=0){
            throw new SecurityException("请先设置屏幕ui基准分辨率宽度");
        }else if(mBaseHeight<=0){
            throw new SecurityException("请先设置屏幕ui基准分辨率高度");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        // 计算倍数
        int temp = dimenWidth;
        int baseTemp = mBaseWidth;
        float scale=temp * 1.0f / baseTemp;
        for (int i = 0; i < baseTemp; i++) {
            builder.append(PX_TEMPLATE.replace("{0}", i + "").replace("{1}", leftTwoDecimal(scale * i) + ""));
        }
        builder.append(PX_TEMPLATE.replace("{0}", baseTemp + "").replace("{1}", temp + ""));
        builder.append("</resources>");
        System.out.println("指定最小宽度分辨率:small_width=" + dimenWidth);
        // 生成文件
        String filePath = FILE_PATH + File.separator + "values-sw"+ dimenWidth+"dp";
        setContentInFile(builder.toString(),filePath);
    }

    /***
     * 生成适配未找到对应分辨率设备的通用dimen文件(直接放置在values中)
     *
     * @param scale: 默认文件夹下dp缩放倍数
     */
    private void createDefaultDimens(float scale){
        if(scale==0){
            scale=DEFAULT_SCALE;
            System.out.println("=====未设置通用文件夹dp缩放倍数,scale采用默认比例====scale="+scale);
        }else if(scale>0){
            System.out.println("=====设置了通用文件夹dp缩放倍数,其值为====scale="+scale);
        }else{
            throw new SecurityException("设置默认value包中dp缩放比例时该值需要大于0");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        // 计算倍数
        int baseDimen=mBaseWidth;
        for (int i = 0; i < baseDimen + 1; i++) {
            // 根据倍率（最终保留两位小数）生成
            builder.append(PX_TEMPLATE.replace("{0}", i + "").replace("{1}", leftTwoDecimal(scale * i) + "").replace("px", "dp"));
        }
        builder.append("</resources>");
        System.out.println("未指定的通用分辨率(values中)");
        //生成文件
        String filePath=FILE_PATH + File.separator + "values";
        setContentInFile(builder.toString(),filePath);
    }

    /***
     * 写文件
     *
     * @param content 要写入文件的数据
     * @param dimenFilePath 文件路径
     *
     */
    private void setContentInFile(String content,String dimenFilePath){
        if(content==null||content.isEmpty()){
            throw new NullPointerException("=====写入文件的数据不能为空=====");
        }
        if(dimenFilePath==null||dimenFilePath.isEmpty()){
            throw new NullPointerException("=====文件夹路径不能为空=====");
        }
        File dimenFile = new File(dimenFilePath);
        dimenFile.mkdirs();
        File valueFile = new File(dimenFile.getAbsolutePath(), "dimens.xml");
        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(valueFile));
            printWriter.print(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保留两位小数
     *
     * @param a 要保留的Float数值
     * @return 保留后的数值
     */
    private float leftTwoDecimal(float a) {
        return (int) (a * 100) / 100f;
    }

    /**
     * 利用递归查询res文件夹的绝对路径并赋值
     *
     * @param filePath 文件路径
     */
    private void getResPath(String filePath) {
        for (File file : new File(filePath).listFiles()) {
            if (file.isDirectory()) {
                // 递归
                //这里将列出所有的文件夹
                if (file.getAbsolutePath().contains("src") && file.getAbsolutePath().contains("res")) {
                    System.out.println("res路径：" + file.getAbsolutePath());
                    FILE_PATH = file.getAbsolutePath();
                    return;
                }
                getResPath(file.getAbsolutePath());
            }
        }
    }

    private List initScreenDimen(){
        //生成分辨率文件夹(宽,高)
        List list=new ArrayList<Integer>();
        list.add(240);
        list.add(320);
        list.add(360);
        list.add(384);
        list.add(392);
        list.add(400);
        list.add(410);
        list.add(411);
        list.add(480);
        list.add(533);
        list.add(540);
        list.add(592);
        list.add(600);
        list.add(640);
        list.add(662);
        list.add(720);
        list.add(750);
        list.add(768);
        list.add(800);
        list.add(811);
        list.add(820);
        list.add(900);
        list.add(960);
        list.add(961);
        list.add(1024);
        list.add(1080);
        list.add(1200);
        list.add(1280);
        list.add(1365);
        list.add(1440);
        list.add(1600);
        return list;
    }


}
