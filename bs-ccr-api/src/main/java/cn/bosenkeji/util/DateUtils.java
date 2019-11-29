/**
 * FileName: DateUtils
 * Author: xivin
 * Date: 2019-09-19 18:02
 * Description:
 */
package cn.bosenkeji.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private final static String FORMAT="yyyy-MM-dd HH:mm:ss";

    private static final int ZERO=0;
    private static final int HOUR_END=23;
    private static final int MINUTE_END=59;
    private static final int SECOND_END=59;

    private static final int ONE_DAY_HAS_MILLIS=86400000;
    private static final int ONE=1;


    public static long getCurrentDayBegin() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY,ZERO);
        calendar.set(Calendar.MINUTE,ZERO);
        calendar.set(Calendar.SECOND,ZERO);
        calendar.set(Calendar.MILLISECOND,ZERO);
        return calendar.getTimeInMillis();
    }

    public static long getCurrentDayEnd() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY,HOUR_END);
        calendar.set(Calendar.MINUTE,MINUTE_END);
        calendar.set(Calendar.SECOND,SECOND_END);
        calendar.set(Calendar.MILLISECOND,999);//毫秒
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayBegin() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(getCurrentDayBegin());
        calendar.add(Calendar.HOUR_OF_DAY,-1);
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayEnd() {
        return getYesterdayBegin()+ONE_DAY_HAS_MILLIS-ONE;
    }

    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null)
            return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }

        calendar.add(Calendar.DATE,2-dayOfWeek);

        return getDayStartTime(calendar.getTime());
    }

    /**
     * 传入一个 date 返回 时间戳
     * @param date
     * @return
     */
    public static Timestamp getDayStartTime(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (null != date) calendar.setTime(date);

        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        calendar.set(Calendar.MILLISECOND,ZERO);
        return new Timestamp(calendar.getTimeInMillis());
    }


    /**
     * string 日期 转成 Date
     * @param dataStr 日期
     * @param format  日期格式
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dataStr, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.parse(dataStr);
    }

    /**
     * date 转string
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String dateToString(long date,String format) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date stringToDate(String dataStr) throws ParseException {
        return stringToDate(dataStr,FORMAT);
    }

    public static String dateToString(Date date) {
        return dateToString(date,FORMAT);
    }

    /**
     *  获取明天这个时候的系统时间
     * @return
     * @throws ParseException
     */
    public static Date getTomorrow() {

        return getAfterHoursDate(24);
    }

    /**
     * 获取当前时间加上 n小时的时间
     * @param hours
     * @return
     * @throws ParseException
     */
    public static Date getAfterHoursDate(int hours) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, hours);
            String date = dateToString(calendar.getTimeInMillis(), FORMAT);
            return stringToDate(date);
        }catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /*public static void main(String[] args) {
        *//*long currentDayBegin = getCurrentDayBegin();
        System.out.println("currentDayBegin = " + currentDayBegin);
        long currentDayEnd = getCurrentDayEnd();
        System.out.println("currentDayEnd = " + currentDayEnd);*//*

        String s1 = "4.2";
        String s2 = "1.6";

        double d1 = Double.valueOf(s1);
        double d2 = Double.valueOf(s2);
        double dd = d1+d2;
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        double v = b1.add(b2).doubleValue();

        System.out.println(v);
    }*/


    public static void main(String[] args) {
        System.out.println("hello java!!!");
    }

}
