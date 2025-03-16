package de.prog3.projektarbeit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {


    public static Date parseStringToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(date);
    }


    public static String parseDateToString(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

}
