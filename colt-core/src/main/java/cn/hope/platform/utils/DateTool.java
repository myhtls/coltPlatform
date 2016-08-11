package cn.hope.platform.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工程
 * Created by myhtls on 16/7/2.
 */
public class DateTool {
    /**
     *
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     *
     *
     * @param date
     * @param pattern Date pattern. Example: dd/MM/yyyy
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String dateToString(Calendar date, String pattern) {
        return dateToString(date.getTime(), pattern);
    }

}
