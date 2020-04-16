package com.gh0stcr4ck3r.news.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DatePref {
    public static String ConvertToNewFormate(String dateStr) throws ParseException{
        TimeZone utc=TimeZone.getTimeZone("Asia/Dhaka");
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.US);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mmaa", Locale.US);
        dateFormat.setTimeZone(utc);
        Date convertedDate=dateFormat.parse(dateStr);
        assert convertedDate!=null;
        return simpleDateFormat.format(convertedDate);

    }
}
