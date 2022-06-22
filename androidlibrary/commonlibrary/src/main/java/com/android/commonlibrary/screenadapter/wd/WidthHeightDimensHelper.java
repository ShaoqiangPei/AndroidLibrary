package com.android.commonlibrary.screenadapter.wd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: 生成Android布局分辨率(宽高限定符)
 *
 * description: 使用说明：
 *              Android项目中建一个Javamodule,将此类拷贝进去
 *              然后在 main(String[] args) 中执行以下代码生成 values文件夹:
 *
 *              WidthHeightDimensHelper helper=new WidthHeightDimensHelper();
 *              helper.setBaseWidth(1080,1920) //设计图基准宽高
 *                    .setDefaultScale(1.0f) //默认缩放比
 *                    .createWidthDimens(); //宽度基准适配
 *                 // .createHeightDimens(); //高度基准适配
 *
 *
 * autor:pei
 * created on 2022/6/6
 */
public class WidthHeightDimensHelper {

    //基准宽度和高度(通常设置成UI图的分辨率的高度和宽度)
    private static final float DEFAULT_SCALE=1f;

    private int mBaseWidth;
    private int mBaseHeight;
    private float mScale;

    //生成Dimens文件的路径(项目的res文件夹)
    private String FILE_PATH;
    private static String PX_TEMPLATE = "<dimen name=\"dp_{0}\">{1}px</dimen>\n";

    /**设置ui设计图基准分辨率宽高**/
    public WidthHeightDimensHelper setBaseWidth(int baseWidth, int baseHeight){
        this.mBaseWidth=baseWidth;
        this.mBaseHeight=baseHeight;
        return this;
    }

    /**设置默认value包中dp缩放比例**/
    public WidthHeightDimensHelper setDefaultScale(float scale){
        this.mScale=scale;
        return this;
    }

    /**宽度适配方案**/
    public void createWidthDimens() {
        // 获取项目res文件的路径
        getResPath(new File("").getAbsolutePath());
        List<Dimens>list=initScreenDimen();
        for (Dimens dimens:list) {
            createDimensByBase(dimens.width,dimens.height,false);
        }
        //默认分辨率设备的通用dimen文件
        createDefaultDimens(mScale,false);
    }

    /** 高度适配方案 **/
    public void createHeightDimens() {
        // 获取项目res文件的路径
        getResPath(new File("").getAbsolutePath());
        List<Dimens>list=initScreenDimen();
        for (Dimens dimens:list) {
            createDimensByBase(dimens.width,dimens.height,true);
        }
        //默认分辨率设备的通用dimen文件
        createDefaultDimens(mScale,true);
    }

    /**
     * 生成相应分辨率的文件
     *
     * @param dimenWidth 要生成相应分辨率宽
     * @param dimenHeight  要生成相应分辨率高
     * @param isHeight 是否采用最小高度适配： true: 采用以高度为基准适配方案
     *                                      false: 采用以宽度为基准适配方案
     */
    private void createDimensByBase(int dimenWidth, int dimenHeight,boolean isHeight) {
        if(mBaseWidth<=0){
            throw new SecurityException("请先设置屏幕ui基准分辨率宽度");
        }else if(mBaseHeight<=0){
            throw new SecurityException("请先设置屏幕ui基准分辨率高度");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        // 计算倍数
        int temp = isHeight ? dimenHeight : dimenWidth;
        int baseTemp = isHeight ? mBaseHeight : mBaseWidth;
        float scale=temp * 1.0f / baseTemp;
        for (int i = 1; i < baseTemp; i++) {
            builder.append(PX_TEMPLATE.replace("{0}", i + "").replace("{1}", leftTwoDecimal(scale * i) + ""));
        }
        builder.append(PX_TEMPLATE.replace("{0}", baseTemp + "").replace("{1}", temp + ""));
        builder.append("</resources>");
        System.out.println("指定分辨率:" + dimenHeight + "x" + dimenWidth);
        // 生成文件
        String filePath = FILE_PATH + File.separator + "values-" + dimenHeight + "x" + dimenWidth;
        setContentInFile(builder.toString(),filePath);
    }

    /***
     * 生成适配未找到对应分辨率设备的通用dimen文件(直接放置在values中)
     *
     * @param scale: 默认文件夹下dp缩放倍数
     * @param isHeight: 是否采用最小高度适配： true: 采用以高度为基准适配方案
     *                                       false: 采用以宽度为基准适配方案
     */
    private void createDefaultDimens(float scale,boolean isHeight){
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
        int baseDimen=isHeight?mBaseHeight:mBaseWidth;
        for (int i = 1; i < baseDimen + 1; i++) {
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
        List list=new ArrayList<Dimens>();

        list.add(new Dimens(240,320));
        list.add(new Dimens(320,480));
        list.add(new Dimens(480,800));
        list.add(new Dimens(600,800));
        list.add(new Dimens(480,854));
        list.add(new Dimens(540,854));
        list.add(new Dimens(540,960));
        list.add(new Dimens(600,1024));
        list.add(new Dimens(640,960));
        list.add(new Dimens(768,1024));
        list.add(new Dimens(720,1184));
        list.add(new Dimens(720,1196));
        list.add(new Dimens(720,1208));
        list.add(new Dimens(720,1280));
        list.add(new Dimens(768,1280));
        list.add(new Dimens(750,1334));
        list.add(new Dimens(800,1280));
        list.add(new Dimens(900,1440));
        list.add(new Dimens(1080,1700));
        list.add(new Dimens(1080,1776));
        list.add(new Dimens(1080,1794));
        list.add(new Dimens(1080,1800));
        list.add(new Dimens(1080,1812));
        list.add(new Dimens(1080,1920));
        list.add(new Dimens(1080,2160));
        list.add(new Dimens(1200,1920));
        list.add(new Dimens(1440,2560));
        list.add(new Dimens(1600,2560));
        return list;
    }

    private class Dimens {
        private int width;
        private int height;

        public Dimens(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

}
