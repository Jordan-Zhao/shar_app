package com.share.locker.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author shenghai
 */
public class DateUtil {

    public static String formatDate(Date date){
        if(date == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(date);
    }
    /**
     * 以指定的日期格式来格式化日期
     *
     * @param date
     * @param format 不允许为null
     * @return 如果date == null，返回""
     */
    public static String formatDate(final Date date, final String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date parseDate(final String dateStr, final String format) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        return new SimpleDateFormat(format).parse(dateStr);
    }
//	/**
//	 * 计算两个日期间隔的天数
//	 *
//	 * @param firstDate 比对的开始时间
//	 * @param lastDate 比对的结束时间
//	 * @return 相隔天数
//	 */
//	public static int getIntervalDays(java.util.Date firstDate, java.util.Date lastDate) {
//		final Date first = CalendarUtil.zerolizedTime(firstDate);
//		final Date last = CalendarUtil.zerolizedTime(lastDate);
//		return CalendarUtil.getIntervalDays(first, last);
//	}

//	/**
//	 * 获取和系统时间的时间差（毫秒，系统时间-给定时间）
//	 * @param time
//	 * @return
//	 */
//	public static long getIntervalMilliseconds(java.util.Date time) {
//		return System.currentTimeMillis()-time.getTime();
//	}
//
//	public static String format(Date date) {
//		return CalendarUtil.toString(date, CalendarUtil.DATE_FMT_3);
//	}
//
//	public static String formatTime(Date date) {
//		return CalendarUtil.toString(date, CalendarUtil.TIME_PATTERN);
//	}

public static void main(String[] arg){
        System.out.println(formatDate(new Date()));
}

}