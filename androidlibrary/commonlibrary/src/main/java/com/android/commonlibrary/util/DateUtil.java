package com.android.commonlibrary.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author pei
 * @create 2016-6-13
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    private static final String SYNBOL_ONE="-";
    private static final String MILLI="SSS";//毫秒
    private static final String SECOND_MILLI="s.SSS";//秒和毫秒
    private static final String HOUR_MINUTE="HH:mm";//时:分
    private static final String HOUR_MINUTE_SECOND="HH:mm:ss";//时:分:秒
    private static final String YEAR_MONTH_DAY="yyyy-MM-dd";//年-月-日
    private static final String CHINA_DATE_MIN="yyyy年MM月dd日 HH:mm";//xxxx年xx月xx日 时:分
    private static final String CHINA_DATE="yyyy年MM月dd日";//xxxx年xx月xx日
    /**
     * 获取当前时间毫秒单位上的数值(只获取毫秒单位上的值,时分秒等其他单位上的值不获取)
     * @return eg:849
     */
    public static String getMillis(){
        return getDateTimeTemplate(DateUtil.MILLI);
    }

    /**
     * 获取当前时间秒和毫秒单位上的数值(只获取秒和毫秒单位上的值,时分等其他单位上的值不获取)
     * @return eg:8.852
     */
    public static String getSecondsAndMillis(){
        return getDateTimeTemplate(DateUtil.SECOND_MILLI);
    }

    /**
     * 获取当前时间的小时和分钟
     * @return eg:  17:29
     */
    public static String getHoursAndMinutes(){
        return getDateTimeTemplate(DateUtil.HOUR_MINUTE);
    }

    /***
     * 获得当前时间：时：分：秒(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @return eg: 17:29:45
     */
    public static String getTime(){
        return getDateTimeTemplate(DateUtil.HOUR_MINUTE_SECOND);
    }

    /**
     * 当前年份
     * @return eg: 2020
     */
    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 当前月份
     * @return eg: 2
     */
    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.valueOf(month);
    }


    /**
     * 当前日
     * @return eg: 7
     */
    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }

    /**
     * 获得当前日期: 年-月-日
     * @return eg: 2020-02-07
     */
    public static String getDate(){
        return getDateTimeTemplate(DateUtil.YEAR_MONTH_DAY);
    }

    /**
     * 获得当前日期及时间：年-月-日  时：分：秒(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @return eg: 2020-02-07 18:14:50
     */
    public static String getDateTime(){
        String template=getSynbolTemplate(DateUtil.SYNBOL_ONE);
        return getDateTimeTemplate(template);
    }

    /**
     * 获得当前中文日期：xxxx年xx月xx日
     * @return eg: 2020年02月07日
     */
    public static String getChinaDate(){
        return getDateTimeTemplate(DateUtil.CHINA_DATE);
    }

    /**
     * 获得当前中文日期及时分：xxxx年xx月xx日  时：分(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @return eg: 2020年02月07日 18:06
     */
    public static String getChinaDate2min(){
        return getDateTimeTemplate(DateUtil.CHINA_DATE_MIN);
    }

    /**
     * 将 2019-03-04 转成 2019年03月04日
     * @return
     */
    public static String formatChinaDate(String dateString){
        String dateArray[]=getDateArray(dateString);
        if(dateArray!=null){
            return dateArray[0]+"年"+dateArray[1]+"月"+dateArray[2]+"日";
        }
        return null;
    }

    /**
     * 将 2019年03月04日 转成 2019-03-04
     * @return
     */
    public static String formatDate(String dateString){
        //过滤汉字
        String str=StringUtil.filterChinese(dateString);
        //判断是否为8位数数字
        String regex = "^\\d{8}$";
        if(RegularUtil.isRegex(str,regex)){
            String year=str.substring(0,4);
            String month=str.substring(4,6);
            String day=str.substring(6,str.length());
            return year + DateUtil.SYNBOL_ONE + month + DateUtil.SYNBOL_ONE + day;
        }
        return null;
    }

    /**获取日期时间模板**/
    public static String getDateTimeTemplate(String template) {
        Date d = new Date();
        long longtime = d.getTime();
        SimpleDateFormat format = new SimpleDateFormat(template);
        String date = format.format(longtime);
        return date;
    }

    /**时间格式**/
    public static String getSynbolTemplate(String synbol){
        return "yyyy" + synbol + "MM" + synbol + "dd HH:mm:ss";
    }

    private static String[] getDateArray(String dateString){
        if(StringUtil.isNotEmpty(dateString)&& dateString.contains(DateUtil.SYNBOL_ONE)){
            String dateArray[] = dateString.split(DateUtil.SYNBOL_ONE);
            if(dateArray!=null&&dateArray.length==3){
                return dateArray;
            }
        }
        return null;
    }

    /**
     * 获取当前时间毫秒数
     * @return eg: 1581073508386
     */
    public static long getLongTime(){
        Date d = new Date();
        long longtime = d.getTime();
        return longtime;
    }

    /**
     * 获取给定日期时间戳
     * @param dateStr: 格式为: 2020-02-07
     * @return eg: 1581004800000
     */
    public static long getDateStrToLong(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.YEAR_MONTH_DAY);
        try {
            return formatter.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒数转成日期
     * @param time eg:1581004800000L
     * @return eg:2020-02-07
     */
    public static String getLongToDate(long time){
        return getTimeToString(time,DateUtil.YEAR_MONTH_DAY);
    }

    /**
     * 显示给定毫秒的时分单位:  时:分(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @param time eg: 1581075842256L
     * @return eg: 19:44
     */
    public static String getLongToHoursAndMinutes(long time){
        return getTimeToString(time,DateUtil.HOUR_MINUTE);
    }

    /**
     * 将毫秒数转成时间(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @param time  eg: 1581075842256L
     * @return  eg: 2020-02-07 19:44:02
     */
    public static String getLongToTime(long time){
        String template=getSynbolTemplate(DateUtil.SYNBOL_ONE);
        return getTimeToString(time,template);
    }

    /**
     * 将毫秒数转成时间模板(24小时制,若想用12小时制,将"HH:mm:ss"改为"hh:mm:ss")
     * @param time  eg:1581004800000L
     * @param pattern
     * @return
     */
    public static String getTimeToString(long time, String pattern){
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        String nowTime = sf.format(time);
        return nowTime;
    }

    /**
     * 得到时间差 自2013年1月1日起到现在的时间差(秒数)
     * @param dateTimeStr eg: 2013-01-01 00:00:00
     * @return eg: 224107790L
     */
    public static long getDifferenceFromTime(String dateTimeStr){
        // 将2013年1月1日到现在转换成秒数
        // String dateTimeStr = "2013-01-01 00:00:00";

        String template=getSynbolTemplate(DateUtil.SYNBOL_ONE);
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        Date date;
        long lTime = 0L;
        long changeTime = 0L;
        try {
            date = sdf.parse(dateTimeStr);
            // 继续转换得到秒数的long型
            lTime = date.getTime() / 1000;
            // 获取现在的时间 秒数
            Date day = new Date();
            long longtime = day.getTime() / 1000;
            // 时间差
            changeTime = longtime - lTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changeTime;
    }

    /**
     * 取当前日期的前xx天
     *
     * 例：preDate=-7 表示今天的前的第 7 天
     * preDate=7 表示今天之后的第 7 天
     *
     * @param preDate 天数
     * @return： 格式为：2020-02-04
     */
    public static String getStateDate(int preDate){
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YEAR_MONTH_DAY);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, preDate);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /***
     * 获取某天的前i天或后i天
     * @param date 日期格式："yyyy-MM-dd"
     * @param i i>0表示获取date之后的第i天日期，i<0表示获取date之前的第i天日期
     * @return
     */
    public static String getBeforeOrAfterDate(String date, long i){
        String dateString = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(date);
                 /*System.out.println("今天的日期："+df.format(d));
                 System.out.println("两天前的日期：" + df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000)));
                 System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000)));*/
            dateString = df.format(new Date(d.getTime() + i * 24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 比较日期大小
     *
     * @return 1:表示 date1是date2的未来
     *         0:表示 date1是date2是同一天
     *        -1:表示 date1是date2的过去
     *        -2：表示发生错误
     *
     * 注：date1和date2样式为   "2015-12-09"
     */
    @SuppressLint("SimpleDateFormat")
    public static int compareDate(String date1, String date2){
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YEAR_MONTH_DAY);
        int state = -2;
        try {
            if (StringUtil.isEmpty(date1) || StringUtil.isEmpty(date2)) {
                state = -2;
            } else {
                Date date = sdf.parse(date1);
                Date now = sdf.parse(date2);
                long difference = now.getTime() - date.getTime();
                if (difference > 0) {
                    state = -1;
                } else if (difference < 0) {
                    state = 1;
                } else {
                    state = 0;
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            state = -2;
        }
        return state;
    }

    /**
     * 是否为当前日期的过去时
     * @param dateStr: 日期格式为：2020-02-07
     * @return true: dateStr为过去的日期，即为昨天或更早以前的日期
     *         false： dateStr为今天或未来的日期
     */
    public static boolean isBeforeToday(String dateStr){
        long longtime1 = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.YEAR_MONTH_DAY);
        String currentDate = format.format(longtime1);
        int resultDate = currentDate.compareTo(dateStr);
        return resultDate > 0;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    /**
     * 当地时间 转为 UTC时间
     * @return 返回数据格式：2020-02-07 12:46:25
     *
     * 注：UTC时间为世界标准时间,又称世界统一时间
     */
    public static String Local2UTC() {
        String template=getSynbolTemplate(DateUtil.SYNBOL_ONE);
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }


    /**
     * UTC时间 转为 当地时间
     *
     * @param utcTime UTC时间, 格式为: 2020-02-07 12:46:25
     * @return 当地时间, 格式为: 2020-02-07 20:46:25
     *
     * 注：UTC时间为世界标准时间,又称世界统一时间
     */
    public static String utc2Local(String utcTime) {
        String template=getSynbolTemplate(DateUtil.SYNBOL_ONE);
        SimpleDateFormat utcFormater = new SimpleDateFormat(template);//UTC时间格式
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(template);//当地时间格式
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }


    /**
     * 获取当地时间在utc标准下的中国时间
     * @return  格式为：2020-02-07 21:04:49
     *
     * 注：UTC时间为世界标准时间,又称世界统一时间
     */
    public static String getUtcInChina() {
        String utcInChina = null;
        String utcTime = Local2UTC();
        if (StringUtil.isNotEmpty(utcTime)) {
            utcInChina = utc2Local(utcTime);
        }
        return utcInChina;
    }

}
