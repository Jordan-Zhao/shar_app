package com.share.locker.common;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    public static boolean isEmpty(String s){
        return s == null || s.trim().length() < 1;
    }

    public static boolean isPhoneNumber(String s){
        if(s == null || s.trim().length() < 1){
            return false;
        }
        try{
            String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(s);
            return m.matches();
        }catch (PatternSyntaxException ex){
            return false;
        }
    }


    public static void main(String[] arg){
        System.out.println(isPhoneNumber("18688888888"));
    }
}
