package com.example.suvp.shop.General;

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
}
