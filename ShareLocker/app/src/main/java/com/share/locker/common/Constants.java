package com.share.locker.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jordan on 16/01/2018.
 * 系统中用到的常量
 */

public class Constants {
    public final static String SHARED_REF_NAME = "share_locker";
    public final static String SHARED_REF_KEY_APP_CONFIG = "app_config";   //系统配置信息在ShareRefer中的key
    public final static String SHARED_REF_KEY_LOGIN_info = "login_info";    //登录信息

    public final static String PASSWORD_MD5_KEY = "passworkMD5Key";

    public final static String URL_BASE = "http://192.168.0.102:8080/locker/";

    public final static String OPERATION_BANNER = "OPERATION_BANNER";	//banner图片配置code
    public final static String OPERATION_CENTER = "OPERATION_CENTER";	//首页中间位置配置code


    public final static String HTTP_RESULT_JSON_TYPE_OBJECT = "HTTP_RESULT_JSON_TYPE_OBJECT";	//http 请求返回的json：Object
    public final static String HTTP_RESULT_JSON_TYPE_ARRAY = "HTTP_RESULT_JSON_TYPE_ARRAY";	//http 请求返回的json：ARRAY

    public final static String KEY_LOGINED_JUMP = "KEY_LOGINED_JUMP";   //发起登录Intent里的key
    public final static String LOGINED_JUMP_TO_MINE = "LOGINED_JUMP_TO_MINE";   //登录后跳转到Mine页面
    public final static String LOGINED_JUMP_TO_PUBLISH_ITEM = "LOGINED_JUMP_TO_PUBLISH_ITEM";   //登录后跳转到“发布宝贝”页面，显示发布完成

    //========start======Intent request code===========
    public final static int INTENT_REQUEST_CODE_LOGIN = 1;
    public final static int INTENT_REQUEST_CODE_PUBLISH_ITEM = 2;
    //========end======Intent request code===========

    /**
     * 最小租赁时间单元，给下拉框使用
     */
    public final static List<SpinnerItemData> PRICE_TIME_UNIT_ITEM_DATA_LIST = new ArrayList<SpinnerItemData>();
    {
        SpinnerItemData itemData = new SpinnerItemData();
        itemData.setKey("DAY");
        itemData.setValue("天");
        PRICE_TIME_UNIT_ITEM_DATA_LIST.add(itemData);
        itemData = new SpinnerItemData();
        itemData.setKey("HALF_DAY");
        itemData.setValue("半天");
        PRICE_TIME_UNIT_ITEM_DATA_LIST.add(itemData);
        itemData = new SpinnerItemData();
        itemData.setKey("HOUR");
        itemData.setValue("小时");
        PRICE_TIME_UNIT_ITEM_DATA_LIST.add(itemData);
    }

    /**
     * 柜门尺寸
     */
    public enum LockerSize{
        MIN("min"),MID("mid"),MAX("max");
        private String sizeCode;

        LockerSize(String sizeCode){
            this.sizeCode = sizeCode;
        }

        public String getSizeCode() {
            return sizeCode;
        }

        public void setSizeCode(String sizeCode) {
            this.sizeCode = sizeCode;
        }
    }
}
