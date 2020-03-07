package com.thesandx.sandeep.tiktok.Utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    private DateUtils() {

    }

    public static String currentDate(String format) {
        //String format = "dd/MM/yy";
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(currentDate);
        return formattedDate;
    }

    public static String DateToString(Date date, String format) {
        //String format="dd/MM/yy";
        Format formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date StringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date myDate = Calendar.getInstance().getTime();

        try {
            myDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

}
