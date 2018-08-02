package com.zzzz.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        return today.getTime();
    }
}
