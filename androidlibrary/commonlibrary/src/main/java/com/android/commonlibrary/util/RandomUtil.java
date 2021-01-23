package com.android.commonlibrary.util;

import java.util.Random;

/**
 * Title:随机数工具类
 * description:
 * autor:pei
 * created on 2021/1/23
 */
public class RandomUtil {

    /***
     * 随机生成 [start,end] 之间的数字
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandomInt(int start,int end){
        Random random=new Random();
        return random.nextInt(end-start+1)+start;
    }

    /***
     * 根据概率执行
     *
     * @param rate 概率范围[0.0000,1.0000] 小数点后保留4位小数
     * @return true:执行   false:不执行
     */
    public static boolean getProbability(double rate){
        if(rate<0||rate>1){
            throw new SecurityException("=== rate取值范围为[0.0000,1.0000] ===");
        }
        int temp = getRandomInt(1, 10000);  //[1,10000]
        double tempRate=(1-rate)*10000;
        LogUtil.i("===temp="+temp+"  tempRate="+tempRate);
        if(temp>tempRate){
            return true;
        }
        return false;
    }

}
