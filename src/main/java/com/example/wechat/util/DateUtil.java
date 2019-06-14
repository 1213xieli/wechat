package com.example.wechat.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/* *
 * @description 时间工具类
 * @author xieli
 * @date  9:51 2019/6/5
 **/
public class DateUtil
{
    public DateUtil()
    {

    }

    public static String Time_Formatter_Day = "yyyy/MM/dd";
    public static String Formate_Day = "yyyy-MM-dd";
    public static String Formate_Second = "yyyy-MM-dd HH:mm:ss";
    public static String Formate_HMS = "HH:mm:ss";


    public static String getCurrentYear()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 判断 firstDate 是否在 secondDate 之前或相等.
     * @param firstDate .
     * @param secondDate .
     * @return firstDate 在 secondDate 之前或相等时返回 true，否则返回 false.
     */
    public static boolean isBeforeOrEqual(Date firstDate, Date secondDate)
    {
        if (secondDate == null)
        {
            return true;
        }
        if (firstDate.compareTo(secondDate) <= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 比较日期
     * @param firstDate 第一个
     * @param secondDate 第二个
     * @return boolean
     */
    public static boolean isAfterOrEqual(Date firstDate, Date secondDate)
    {
        if (secondDate == null)
        {
            return true;
        }
        if (firstDate.compareTo(secondDate) >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 验证日期格式是否正确.
     * @param datestr .
     * @return .
     */
    public static boolean isISO8601Date(String datestr)
    {
        return Pattern
                .compile(
                        "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$")
                .matcher(datestr).matches();
    }

    /**
     * 获取两个指定日期相差的天数.
     * @param firstDate .
     * @param secondDate .
     * @return .
     */
    public static int getBetweenDay(Date firstDate, Date secondDate)
    {
        // 时间格式相同，获取两时间差的秒数
        long betweendDateBySeconds = firstDate.getTime() - secondDate.getTime();
        // 得到天数(保持正负号)
        return (int) (betweendDateBySeconds / (1000 * 3600 * 24));
    }

    /**
     * 获取系统当前时间.
     * @return .
     */
    public static Date getCurrentDate()
    {
        return new Date();
    }

    /**
     * 年份
     * @param datIn 日期
     * @return int
     */
    public static int getYear(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 月份
     * @param datIn 日期
     * @return int
     */
    public static int getMonth(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 日
     * @param datIn 日期
     * @return int
     */
    public static int getDay(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 小时
     * @param datIn 日期
     * @return int
     */
    public static int getHour(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 分钟
     * @param datIn 日期
     * @return int
     */
    public static int getMinute(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 秒数
     * @param datIn 日期
     * @return int
     */
    public static int getSecond(Date datIn)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datIn);

        return calendar.get(Calendar.SECOND);
    }

    /**
     * 格式化日期
     * @param patern 格式字符
     * @return String
     */
    public static String getFormatDate(String patern)
    {
        return new SimpleDateFormat(patern).format(new Date());
    }

    /**
     * 格式化日期
     * @param datIn 日期
     * @return String
     */
    public static String getFormatPaternDate(Date datIn)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datIn);
    }

    /**
     * 将指定字符串，通过指定格式转换为日期对象.
     * @param dateStr 字符串
     * @return Date
     */
    public static Date parseDate(String dateStr, String formatter)
    {

        try
        {
            if (Func.checkNullOrEmpty(dateStr))
            {
                return null;
            }
            return new SimpleDateFormat(formatter).parse(dateStr);
        } catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 将指定字符串，通过指定格式转换为日期对象.
     * @param dateStr 字符串
     * @return Date
     */
    public static Date parseDateOnly(String dateStr)
    {
        if (Func.checkNullOrEmpty(dateStr))
        {
            return null;
        }

        try
        {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e)
        {
            try
            {
                return new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
            } catch (Exception e2)
            {
                new Exception("时间格式错误：" + e2.getMessage()).printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将日期date转化成string
     * @param date
     * @return
     */
    public static String parseDateToStr(Date date, String formatter)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(date);
    }

    /**
     * 增加日期
     * @param date 日期
     * @param dateField 字段
     * @param dateAmount 数量
     * @return Date
     */
    public static Date addDate(Date date, int dateField, int dateAmount)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateField, dateAmount);

        return calendar.getTime();
    }

    /**
     * 格式化指定日期格式
     * @param datIn 日期内容
     * @param formatStr 格式化字符
     * @return 格式化之后的日期对象
     * @throws ParseException 转换异常
     */
    public static Date formateDate(Date datIn, String formatStr) throws ParseException
    {
        String dateStr = new SimpleDateFormat(formatStr).format(datIn);

        return new SimpleDateFormat(formatStr).parse(dateStr);
    }

    /** 
     *  获取两个日期相差的月数 
     * @param c1    较大的日期
     * @param c2    较小的日期
     * @return  如果d1>d2返回 月数差 否则返回0 
     */
    public static int getMonthDiff(Calendar c1, Calendar c2)
    {
        if (c1.getTimeInMillis() < c2.getTimeInMillis())
        {
            return 0;
        }
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30  
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数  
        if (month1 < month2 || month1 == month2 && day1 < day2)
        {
            yearInterval--;
        }
        // 获取月数差值  
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2)
        {
            monthInterval--;
        }
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 获得两个日期之间的所有月份
     * @param minDate minDate
     * @param maxDate maxDate
     * @return List<String>
     * @throws ParseException ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException
    {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max))
        {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 转换日期
     * @param str 对象
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    public static boolean isDateStr(String str, boolean hasDay)
    {

        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        if (!hasDay)
        {
            format = new SimpleDateFormat("yyyy/MM");
        }
        try
        {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e)
        {
            try
            {
                SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
                if (!hasDay)
                {
                    formatt = new SimpleDateFormat("yyyy-MM");
                }
                formatt.setLenient(false);
                Date tempDate = formatt.parse(str);
                // 年份大于9999年 则视为非法;
                if (tempDate.getYear() > 8099)
                {
                    convertSuccess = false;
                }
            } catch (Exception e2)
            {
                convertSuccess = false;
            }
        }
        return convertSuccess;
    }

    /**
     * 根据当前时间获取文件夹
     * @return String
     */
    public static String getCurrentDateFolder()
    {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        return year + File.separator + month + File.separator + day + File.separator;
    }

    /**
     * 对比时间(精确到天)
     * @param date1
     * @param date2
     */
    public static boolean compareDateTimeSpecial(String date1, String date2, String order)
    {
        Date d1 = parseDateOnly(date1);
        Date d2 = parseDateOnly(date2);
        if (d1.equals(d2))
        {
            return false;
        } else if (d1.before(d2))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /* *
     * @description 转化成开始时间
     * @author xieli
     * @date  9:53 2019/6/5
     * @param [starttime]
     * @return long
     **/
    public static long convertStartTime(long starttime)
    {
        String dateStr = parseDateToStr(new Date(starttime), Formate_Day);
        dateStr = dateStr + " 00:00:00";
        return parseDate(dateStr, Formate_Second).getTime();
    }

    /**
     * 时间戳转Unix时间戳
     * @param timestamp
     * @return
     */
    public static long toUnixTimeStamp(long timestamp){
        return timestamp/1000;
    }

    /**
     * Unix时间戳转时间戳
     * @param unixTimeStamp
     * @return
     */
    public static long toTimestamp(long unixTimeStamp){
        return unixTimeStamp*1000;
    }

    /* *
     * @description 转化成结束时间
     * @author xieli
     * @date  9:54 2019/6/5
     * @param [endtime]
     * @return long
     **/
    public static long convertEndTime(long endtime)
    {
        String dateStr = parseDateToStr(new Date(endtime), Formate_Day);
        dateStr = dateStr + " 23:59:59";
        return parseDate(dateStr, Formate_Second).getTime();
    }

    /**
     * 获取本月最后一天
     * @return String
     * **/
    public static String getMonthEnd(int... month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (month.length == 1 && month[0] != 0)
            cal.set(Calendar.MONTH, month[0] - 1);

        Date time = cal.getTime();
        return new SimpleDateFormat(Formate_Day).format(time);
    }
    /**
     * 获取本月开始日期
     * @return String
     * **/
    public static String getMonthStart(int... month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        if (month.length == 1 && month[0] != 0)
            cal.set(Calendar.MONTH, month[0] - 1);

        Date time = cal.getTime();
        return new SimpleDateFormat(Formate_Day).format(time);
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = new Date(currentTime); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
