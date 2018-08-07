package com.zzzz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getToday() {
        Calendar today = Calendar.getInstance();
        return toStartOfDay(today);
    }

    public static Date toStartOfDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return date.getTime();
    }

    public static Calendar toCalendar(long unixEpoch) {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(unixEpoch);
        return result;
    }

    public static String toDateString(Date date) {
        return dateFormat.format(date);
    }
}
