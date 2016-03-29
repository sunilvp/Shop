package com.example.suvp.shop.General;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by suvp on 3/28/2016.
 */
public class Utility
{
    public static boolean isNullOrEmptyString(String aInString)
    {
        if(aInString == null || aInString.isEmpty() || aInString.length() <0
                || aInString.equals("n/a") || aInString.equals("N/A"))
        {
            return true;
        }
        return false;
    }

    public static String getDateStringFromDate(Date aInDate)
    {
        Calendar lCalender = Calendar.getInstance();
        lCalender.setTime(aInDate);
        StringBuffer lStringDate = new StringBuffer();
        lStringDate.append(lCalender.get(Calendar.DAY_OF_MONTH));
        lStringDate.append(":");
        lStringDate.append(lCalender.get(Calendar.MONTH) +1);
        lStringDate.append(":");
        lStringDate.append(lCalender.get(Calendar.YEAR));
        return lStringDate.toString();
    }
}
