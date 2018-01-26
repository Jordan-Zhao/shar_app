package com.share.locker.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jordan on 16/01/2018.
 */

public class JsonUtil {
    /**
     * json 转 map
     *
     * @param jsonStr
     * @return
     */
    public static HashMap json2Map(String jsonStr) {
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(jsonStr, HashMap.class);
        return hashMap;
    }

    /**
     * json 转 Object
     *
     * @param jsonStr
     * @return
     */
    public static Object json2Object(String jsonStr,Class c) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, c);
    }

    /**
     * json 转 list
     *
     * @param jsonStr
     * @param typeToken  list中的类构建的TypeToken
     * @return
     */
    public static List json2List(String jsonStr, TypeToken typeToken) {
        if(StringUtil.isEmpty(jsonStr)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, typeToken.getType());
    }


    public static void main(String[] ar) throws Exception {
        /*System.out.println("zzzzzzzzz");

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

        JSONObject jsonObject = new JSONObject(json);*/

        /////////////////////////////////////////////////////////
        /*List<ItemDTO> list = new ArrayList<>();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(111L);
        itemDTO.setTitle("HHHHHHHHH");
        list.add(itemDTO);


        Gson gson = new Gson();

        String s = gson.toJson(list);

System.out.println(s);

         List<ItemDTO> retList = gson.fromJson(s,new TypeToken<List<ItemDTO>>() {}.getType());

        System.out.println(retList.size());*/


        /////////////////////////////////////////////////////////
       /* net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
        String s = jsonArray.toString();
        System.out.println(s);

        Object obj2 = JSONArray.toCollection(JSONArray.fromObject(s),ItemDTO.class);
System.out.println(obj2);*/


        /////////////////////////////////////////////////////////
        /*ItemDetailDTO detailDTO = new ItemDetailDTO();
        detailDTO.setOwnnerNick("aaaaaaaaa");
        List<String> urlList = new ArrayList<>();
        urlList.add("http1111");
        detailDTO.setImgList(urlList);

        Gson gson = new Gson();
        String s = gson.toJson(detailDTO);

        ItemDetailDTO detailDTO1 = gson.fromJson(s,ItemDetailDTO.class);
        System.out.println(detailDTO1.getImgList().size());*/

        ///////////////////////////////////////////////////
       /* Gson gson = new Gson();
        OperationData operationData = new OperationData();
        operationData.setLeftImgUrl("httpdkksdksk111111111");
        HotItemDTO hotItemDTO = new HotItemDTO();
        hotItemDTO.setImgUrl("httttttttttt2222222");
        List<HotItemDTO> hotItemDTOList = new ArrayList<>();
        hotItemDTOList.add(hotItemDTO);
        operationData.setHotItemDTOList(hotItemDTOList);

        String json = gson.toJson(operationData);
        OperationData obj = gson.fromJson(json, new TypeToken<OperationData>() {}.getType());
        obj.getClass();*/
    }

}
