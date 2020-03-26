package com.thestoryofus;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatetimeHelper {

    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static String getNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DatetimeHelper.DATE_TIME_FORMAT);
        Calendar cal = Calendar.getInstance();
        dateFormat.setTimeZone(cal.getTimeZone());
        return dateFormat.format(cal.getTime());
    }

}
