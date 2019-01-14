package com.android.commonlibrary.util;


import android.annotation.SuppressLint;
import android.text.TextUtils;

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

    /**
     * 获得当前日期 ： 年-月-日
     */
    public static String getDate() {
        Date d1 = new Date();
        long longtime1 = d1.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(longtime1);
        return date;// 2012年10月03日 23:41:31  
    }

    /**
     * 获得当前时间：时：分：秒（24小时制，若想用12小时制，将"HH:mm:ss"改为"hh:mm:ss"）
     *
     * @return
     */
    public static String getTime() {
        Date d2 = new Date();
        long longtime2 = d2.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String date = format.format(longtime2);
        return date;// 2012年10月03日 23:41:31  
    }

    /**
     * 获得当前日期及时间：年-月-日  时：分：秒（24小时制，若想用12小时制，将"HH:mm:ss"改为"hh:mm:ss"）
     *
     * @return
     */
    public static String getDateTime() {
        Date d3 = new Date();
        long longtime3 = d3.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(longtime3);
        return date1;// 2012-10-03 23:41:31  
    }

    /**
     * 获得当前日期及时间：年-月-日  时：分（24小时制，若想用12小时制，将"HH:mm"改为"hh:mm"）
     *
     * @return
     */
    public static String getChinaDate2min() {
        Date d3 = new Date();
        long longtime3 = d3.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String date1 = format1.format(longtime3);
        return date1;// 2012年10月03日 23:41
    }

    /**
     * 获得当前日期及时间：年-月-日  时：分：秒（24小时制，若想用12小时制，将"HH:mm:ss"改为"hh:mm:ss"）
     *
     * @return
     */
    public static String getDateTimeBySymbol(String synbol) {
        Date d3 = new Date();
        long longtime3 = d3.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy" + synbol + "MM" + synbol + "dd HH:mm:ss");
        String date1 = format1.format(longtime3);
        return date1;// 2012-10-03 23:41:31
    }

    /**
     * 当前年份
     * @return
     */
    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 当前月份
     * @return
     */
    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        return String.valueOf(month);
    }

    /**
     * 当前日
     * @return
     */
    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }


    /**
     * 获取当前小时 HH:mm
     *
     * @return
     */
    public static String getCurrentHour() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
        String timestamp = df.format(new Date());
        return timestamp;
    }

    /**
     * 获取当前时间毫秒数
     */
    public static long getLongTime() {
        Date d = new Date();
        long longtime = d.getTime();

        return longtime;
    }

    /**获取当前日期时间戳**/
    public static long getStrToLong(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒数转成当前日期
     */
    public static String getLongToDate(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(time);

        return nowDate;
    }

    /**
     * 将毫秒数转成当前时间（24小时制，若想用12小时制，将"HH:mm:ss"改为"hh:mm:ss"）
     */
    public static String getTimeToString(long time, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        String nowTime = sf.format(time);

        return nowTime;
    }

    /**
     * 将毫秒数转成当前时间（24小时制，若想用12小时制，将"HH:mm:ss"改为"hh:mm:ss"）
     */
    public static String getLongToTime(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sf.format(time);

        return nowTime;
    }

    /**
     * 得到时间差 自2013年1月1日起到现在的时间差(秒数)
     *
     *  eg:将2013年1月一日转换成秒数
     *     String sDt = "01/01/2013 00:00:00"
     */
    public static long getDifferenceFromTime(String sDt) {
        // 将2013年1月一日转换成秒数
//        String sDt = "01/01/2013 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt2;
        long lTime = 0;
        long changeTime = 0;
        try {
            dt2 = sdf.parse(sDt);
            // 继续转换得到秒数的long型
            lTime = dt2.getTime() / 1000;
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
     * <p>
     * 例：preDate=-7 表示今天的前的第 7 天
     * preDate=7 表示今天之后的第 7 天
     *
     * @param preDate 天数
     * @return
     */
    public static String getStateDate(int preDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, preDate);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 比较日期大小
     *
     * @return 1:表示 date1是date2的未来
     * 0:表示 date1是date2是同一天
     * -1:表示 date1是date2的过去
     * -2：表示发生错误
     * 注：date1和date2样式为   "2015-12-09"
     */
    @SuppressLint("SimpleDateFormat")
    public static int compareDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int state = -2;
        try {

            if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
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
     * @param birthdy
     * @return
     */
    public static boolean isBeforeToday(String birthdy) {
        long longtime1 = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(longtime1);
        int resultDate = currentDate.compareTo(birthdy);
        return resultDate > 0;
    }

    /***
     * 获取某天的前i天或后i天
     * @param date 日期格式："yyyy-MM-dd"
     * @param i i>0表示获取date之后的第i天日期，i<0表示获取date之前的第i天日期
     * @return
     */
    public static String getBeforeOrAfterDate(String date, long i) {
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
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
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
     * 当地时间 ---> UTC时间
     * @return
     */
    public static String Local2UTC(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    /**
     * UTC时间 ---> 当地时间
     * @param utcTime   UTC时间
     * @return
     */
    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * 获取当地时间在utc标准下的中国时间
     * @return
     */
    public static String getUtcInChina(){
        String utcInChina=null;
        String utcTime=Local2UTC();
        if(StringUtil.isNotEmpty(utcTime)){
            utcInChina=utc2Local(utcTime);
        }
        return utcInChina;
    }

    /**根据给定date截取显示date的时分字符串**/
    public static String getHourMinuteByDate(Date date){
        if(date!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String hourMinute=simpleDateFormat.format(date.getTime());
            return hourMinute;
        }
        return null;
    }

}
