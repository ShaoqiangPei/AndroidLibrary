## RandomUtil使用说明

### 概述
`RandomUtil`主要用于处理事件概率执行场景。当我们知道某事件可能发生的概率时，又需要判断该事件在此概率下会不会执行时,就可以使用此类做处理。

### 使用方法
#### 一. RandomUtil 中方法简介
`RandomUtil`作为一个概率执行的工具类，具有以下几个方法：
```
    /***
     * 随机生成 [start,end] 之间的数字
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandomInt(int start,int end)

    /***
     * 根据概率执行
     *
     * @param rate 概率范围[0.0000,1.0000] 小数点后保留4位小数
     * @return true:执行   false:不执行
     */
    public static boolean getProbability(double rate)

    /****
     * 根据概率执行
     *
     * @param rate  概率范围[0.0000,maxRate] 小数点后保留4位小数
     * @param maxRate 最大概率范围
     * @return true:执行   false:不执行
     */
    public static boolean getProbability(double rate,double maxRate)

    /***
     * 给一个集合(map)设置item,并给每个item设置执行概率
     *
     * @param map 集合
     * @param key item
     * @param probability 概率
     */
    public static void putProbability(Map<String,Double>map,String key,double probability)

    /***
     * map中单个key根据概率是否执行
     *
     * @param key
     * @return true:执行   false:不执行
     */
    public static boolean getProbabilityByKey(Map<String,Double> map, String key)
```
#### 二. RandomUtil 在 Activity 中的使用
下面贴出`RandomUtil`在`Activity`中使用时计算单个事件和单个事件在集合中执行可能性的主要代码：
```
               //计算单个概率,总概率为1
               boolean flag = RandomUtil.getProbability(0.6);
               if (flag) {
                   LogUtil.i("=====总概率为1,该执行概率为0.6,是否会执行：是");
               } else {
                   LogUtil.i("=====总概率为1,该执行概率为0.6,是否会执行：否");
               }

               //计算一组数据中单个item执行的概率
               Map<String,Double>map=new HashMap<>();
               //设置集合
               RandomUtil.putProbability(map,"逛街",0.6);
               RandomUtil.putProbability(map,"看电影",0.2);
               RandomUtil.putProbability(map,"吃饭",0.3);
               //是否吃饭
               boolean eatFlag=RandomUtil.getProbabilityByKey(map,"吃饭");
               if(eatFlag){
                   LogUtil.i("===去吃饭===");
               }else{
                   LogUtil.i("===做其他的事===");
               }
```
