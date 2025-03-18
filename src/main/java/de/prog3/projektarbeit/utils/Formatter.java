package de.prog3.projektarbeit.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {


    public static String formatCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        return formatter.format(amount);
    }

    public static Date parseStringToDate(String date) throws ParseException {
        date = date.replace(".","-");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(date);
    }


    public static String parseDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

}
