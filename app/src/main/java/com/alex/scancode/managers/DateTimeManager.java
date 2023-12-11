package com.alex.scancode.managers;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class DateTimeManager {

    @SuppressLint("SimpleDateFormat")
    public static String extractHoursAndMinutes(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date date = inputFormat.parse(dateTimeString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

}
