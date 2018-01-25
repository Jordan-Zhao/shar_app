package com.share.locker.common;

/**
 * Created by Jordan on 26/01/2018.
 */

public class StringUtil {
    public static boolean isInteger(String str){
        boolean result = true;
        try {
            Integer.parseInt(str);
        }catch(Exception ex){
            result = false;
        }
        return  result;
    }
    public static boolean isFloat(String str){
        boolean result = true;
        try {
            Float.parseFloat(str);
        }catch(Exception ex){
            result = false;
        }
        return  result;
    }
}
