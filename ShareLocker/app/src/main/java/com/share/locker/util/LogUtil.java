package com.share.locker.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by Jordan on 16/01/2018.
 */

public class LogUtil {

    /**
     * 连接字符串
     * @param objects
     * @return
     */
    public static String getMsg(Object... objects){
        StringBuilder sb = new StringBuilder();
        if(objects != null && objects.length > 0){
            GsonBuilder builder=new GsonBuilder();
            Gson gson=builder.create();
            for(Object obj : objects){
                sb.append(gson.toJson(obj)).append("=====");
            }
        }
        return sb.toString();
    }
}
