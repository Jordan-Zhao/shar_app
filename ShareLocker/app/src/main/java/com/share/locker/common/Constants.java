package com.share.locker.common;

/**
 * Created by Jordan on 16/01/2018.
 * 系统中用到的常量
 */

public class Constants {
    public final static String SHARED_REF_NAME = "share_locker";
    public final static String SHARED_REF_KEY_APP_CONFIG = "app_config";   //系统配置信息在ShareRefer中的key
    public final static String SHARED_REF_KEY_LOGIN_info = "login_info";    //登录信息

    public final static String PASSWORD_MD5_KEY = "passworkMD5Key";

    public final static String URL_BASE = "http://192.168.0.104:8080/locker/";

    public final static String OPERATION_BANNER = "OPERATION_BANNER";	//banner图片配置code
    public final static String OPERATION_CENTER = "OPERATION_CENTER";	//首页中间位置配置code


    public final static String HTTP_RESULT_JSON_TYPE_OBJECT = "HTTP_RESULT_JSON_TYPE_OBJECT";	//http 请求返回的json：Object
    public final static String HTTP_RESULT_JSON_TYPE_ARRAY = "HTTP_RESULT_JSON_TYPE_ARRAY";	//http 请求返回的json：ARRAY

    public final static String KEY_LOGINED_JUMP = "KEY_LOGINED_JUMP";   //发起登录Intent里的key
    public final static String LOGINED_JUMP_TO_MINE = "LOGINED_JUMP_TO_MINE";   //登录后跳转到Mine页面
    public final static String LOGINED_JUMP_TO_PUBLISH_ITEM = "LOGINED_JUMP_TO_PUBLISH_ITEM";   //登录后跳转到“发布宝贝”页面，显示发布完成

}
