package com.android.commonlibrary.util;

import java.util.Map;
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
        return getProbability(rate,1d);
    }

    /****
     * 根据概率执行
     *
     * @param rate  概率范围[0.0000,maxRate] 小数点后保留4位小数
     * @param maxRate 最大概率范围
     * @return true:执行   false:不执行
     */
    public static boolean getProbability(double rate,double maxRate){
        if(rate<0||rate>maxRate){
            throw new SecurityException("=== rate取值范围为[0.0000,"+maxRate+"]  ===");
        }
        int temp = getRandomInt(1, NumberUtil.mul(maxRate,10000d).intValue());  //[1,maxRate*10000]
        double diff=NumberUtil.sub(maxRate,rate);
        double tempRate=NumberUtil.mul(diff,10000d);
        LogUtil.i("===temp="+temp+"  tempRate="+tempRate);
        if(temp>tempRate){
            return true;
        }
        return false;
    }

    /***
     * 给一个集合(map)设置item,并给每个item设置执行概率
     *
     * @param map 集合
     * @param key item
     * @param probability 概率
     */
    public static void putProbability(Map<String,Double>map,String key,double probability){
        if(map==null){
            throw new NullPointerException("====map不能为空=====");
        }
        if(StringUtil.isEmpty(key)){
            throw new NullPointerException("====name不能为空=====");
        }
        if(probability<0){
            throw new SecurityException("====probability不能小于零=====");
        }
        map.put(key,probability);
    }

    /***
     * map中单个key根据概率是否执行
     *
     * @param key
     * @return true:执行   false:不执行
     */
    public static boolean getProbabilityByKey(Map<String,Double> map, String key){
        if(map!=null&&StringUtil.isNotEmpty(key)&&!map.isEmpty()&&map.containsKey(key)){
            //计算总值
            double maxRate = 0;
            for (String keyInMap : map.keySet()) {
                maxRate = NumberUtil.add(maxRate,map.get(keyInMap));
            }
            LogUtil.i("========maxRate="+maxRate);
            //key对应的概率
            double rate=map.get(key);
            return getProbability(rate,maxRate);
        }
        return false;
    }

}
