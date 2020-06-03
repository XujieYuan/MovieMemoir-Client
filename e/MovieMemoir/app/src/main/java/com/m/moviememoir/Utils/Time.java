package com.m.moviememoir.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    public static Date toDate(String stringDate, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentTextDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DAY_OF_MONTH);
        return date + "/" + month + "/" + year;
    }

    public static String getDayBeforeYesterdayFormatedDate() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -2);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String strMonth = "";
        String strDay = "";
        if (month < 10)
            strMonth = "0" + Integer.toString(month);
        else
            strMonth = Integer.toString(month);
        if (day < 10)
            strDay = "0" + Integer.toString(day);
        else
            strDay = Integer.toString(day);
        return year + "-" + strMonth + "-" + strDay;
    }

    public static String getYesterdayFormatedDate() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String strMonth = "";
        String strDay = "";
        if (month < 10)
            strMonth = "0" + Integer.toString(month);
        else
            strMonth = Integer.toString(month);
        if (day < 10)
            strDay = "0" + Integer.toString(day);
        else
            strDay = Integer.toString(day);
        return year + "-" + strMonth + "-" + strDay;
    }

    public static String getCurrentFormatedDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String strMonth = "";
        String strDay = "";
        if (month < 10)
            strMonth = "0" + Integer.toString(month);
        else
            strMonth = Integer.toString(month);
        if (day < 10)
            strDay = "0" + Integer.toString(day);
        else
            strDay = Integer.toString(day);
        return year + "-" + strMonth + "-" + strDay;
    }

    public static Date getCurrentDate() {
        return toDate(getCurrentTextDate(), "dd/MM/yyyy");
    }


}
