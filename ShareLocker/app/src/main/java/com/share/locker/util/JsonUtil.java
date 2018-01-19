package com.share.locker.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jordan on 16/01/2018.
 */

public class JsonUtil {
    public static HashMap json2Map(String jsonStr) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        HashMap hashMap = gson.fromJson(jsonStr, HashMap.class);
        return hashMap;
    }

    public static ArrayList json2List(String jsonStr) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ArrayList list = gson.fromJson(jsonStr, ArrayList.class);
        return list;
    }

    public static void main(String[] ar) throws  Exception{
        System.out.println("zzzzzzzzz");

        String json = "[{\"configCode\":\"OPERATION_BANNER\",\"content\":\"" +
                "[{\\\"img_url\\\":\\\"http://192.168.1.6:8080/locker/banner1.png\\\",\\\"link_item_id\\\":1016}," +
                "{\\\"img_url\\\":\\\"http://192.168.1.6:8080/locker/banner1.png\\\",\\\"link_item_id\\\":2099}]\"}," +
                "{\"configCode\":\"OPERATION_CENTER\"," +
                "\"content\":\"{\\\"left\\\":{\\\"link_item_id\\\":1016," +
                "\\\"img_url\\\":\\\"http://192.168.1.6:8080/locker/banner1.png\\\",\\\"text\\\":\\\"???????\\\"}," +
                "\\\"right1\\\":{\\\"link_item_id\\\":3088,\\\"img_url\\\":\\\"http://192.168.1.6:8080/locker/banner1.png\\\"," +
                "\\\"title\\\":\\\"??????\\\",\\\"text\\\":\\\"????????????????\\\"}," +
                "\\\"right2\\\":{\\\"link_item_id\\\":5999,\\\"img_url\\\":" +
                "\\\"http://192.168.1.6:8080/locker/banner1.png\\\",\\\"title\\\":\\\"??????????\\\"," +
                "\\\"text\\\":\\\"??????????\\\"}}\"}]";
//        List list = json2List(json);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JsonElement jsonElement = gson.toJsonTree(json);

        JSONObject jsonObject = new JSONObject(json);



    }

}
