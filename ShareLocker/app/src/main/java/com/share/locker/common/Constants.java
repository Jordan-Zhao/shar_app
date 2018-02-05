package com.share.locker.common;

import com.share.locker.ui.component.SpinnerItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 16/01/2018.
 * 系统中用到的常量
 */

public class Constants {
    public final static String SHARED_REF_NAME = "share_locker";
    public final static String SHARED_REF_KEY_APP_CONFIG = "app_config";   //系统配置信息在ShareRefer中的key
    public final static String SHARED_REF_KEY_LOGIN_info = "login_info";    //登录信息

    public final static String PASSWORD_MD5_KEY = "passworkMD5Key";

    public final static String URL_IP = "47.96.141.170";    //线上

    //public final static String URL_IP = "192.168.0.101:8080";  //本地

    public final static String URL_BASE = "http://"+URL_IP+"/locker/";
//public final static String URL_BASE = "http://192.168.2.195:8080/locker/";

    public final static String[] serverIpArr = {"47.96.141.170","192.168.0.101:8080","192.168.0.103:8080"};   //服务端可能部署的ip地址

    public final static String OPERATION_BANNER = "OPERATION_BANNER";	//banner图片配置code
    public final static String OPERATION_CENTER = "OPERATION_CENTER";	//首页中间位置配置code


    public final static String HTTP_RESULT_JSON_TYPE_OBJECT = "HTTP_RESULT_JSON_TYPE_OBJECT";	//http 请求返回的json：Object
    public final static String HTTP_RESULT_JSON_TYPE_ARRAY = "HTTP_RESULT_JSON_TYPE_ARRAY";	//http 请求返回的json：ARRAY

    public final static String INTENT_KEY_JUMP_TO = "INTENT_KEY_JUMP_TO";   //处理完成Intent后，应该跳转到的页面
/*
    public final static String KEY_BACK_MAIN_PAGE = "KEY_MAIN_JUMP_TO";   //发起登录Intent里的key

    public final static String KEY_LOGINED_JUMP = "KEY_LOGINED_JUMP";   //发起登录Intent里的key
    public final static String LOGINED_JUMP_TO_MINE = "LOGINED_JUMP_TO_MINE";   //登录后跳转到Mine页面
    public final static String LOGINED_JUMP_TO_PUBLISH_ITEM = "LOGINED_JUMP_TO_PUBLISH_ITEM";   //登录后跳转到“发布宝贝”页面，显示发布完成

    public final static String KEY_REGISTER_JUMP = "KEY_REGISTER_JUMP";   //发起注册Intent里的key
    public final static String REGISTERED_TO_MINE = "REGISTERED_TO_MINE";   //注册后跳转到Mine页面*/

//public final static String INTENT_KEY_MAIN_TO_WHERE = "INTENT_KEY_MAIN_TO_WHERE";

    //========start======Intent request code===========
    public final static int INTENT_REQUEST_CODE_LOGIN = 1;
    public final static int INTENT_REQUEST_CODE_PUBLISH_ITEM = 2;
    //========end======Intent request code===========

    //========从外部activity回到MainActivity时，通过这个编码告知mainActivity显示那个Fragment
    public enum MainIntentToWhereCode{
        TO_MINE_FRAG(1,"登录完成后，返回到 我的 Fragment");

        private int code;
        private String description;

        MainIntentToWhereCode(int code,String description){
            this.code = code;
            this.description = description;
        }
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

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


    public static final int FRAG_INDEX_HOME = 0;
    public static final int FRAG_INDEX_CART = 1;
    public static final int FRAG_INDEX_PUBLISH = 2;
    public static final int FRAG_INDEX_MESSAGE = 3;
    public static final int FRAG_INDEX_MINE = 4;

    public static final String TMP_FILE_PATH = "/data/data/com.share.locker.sharelocker/files/";    //临时文件路径
    public static final int UPLOAD_IMAGE_WIDTH = 1080;  //上传的图片宽度，高度按照这个宽度等比例缩放

    /**
     * 支付方式
     */
    public enum PayWay{
        ALIPAY("ALIPAY","支付宝"),WECHAT("WECHAT","微信支付");
        private String code;
        private String name;

        PayWay(String code,String name){
            this.code=code;this.name=name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 宝贝数据状态
     *
     * @author Jordan
     *
     */
    public static enum ItemStatus {
        CREATED("CREATED", "创建成功"), GENERATED_PUT_QRCODE("GENERATED_PUT_QRCODE", "已生成存件二维码"), ONLINE("ONLINE",
                "已存入柜门，上架"), LOCKED("LOCKED", "正在下单，锁定中"), USING("USING", "租用中"), EXCEPTION("EXCEPTION",
                "异常情况，人工介入中"), OFFLINE("OFFLINE",
                "下架"), DELETED("DELETED", "删除"), INVALID("INVALID", "无效数据，数据订正删除等造成");

        private String code;
        private String name;

        ItemStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * 订单状态
     *
     * @author Jordan
     *
     */
    public static enum OrderStatus {
        CREATED("CREATED", "创建成功，待支付"), PAID_DEPOSIT("PAID_DEPOSIT", "已支付押金"), GENERATED_TAKE_QRCODE(
                "GENERATED_TAKE_QRCODE", "生成取件二维码，待取件"), USING("USING", "租用中"), GENERATED_RETURN_QRCODE(
                "GENERATED_RETURN_QRCODE", "生成还件二维码，还件中"), PAID_FEE("PAID_FEE", "租金支付成功"), RETURNED("RETURNED",
                "已还件"), CHECKED_QUALITY("RETURNED", "管理员已检查"), REFUNDED_DEPOSIT("REFUNDED_DEPOSIT",
                "押金已退还"), APPLY_FOR_REFUND_DEPOSIT("APPLY_FOR_REFUND_DEPOSIT",
                "租用前申请退押金"), CANCELLED("CANCELLED", "已取消"), EXCEPTION("EXCEPTION",
                "异常情况，人工介入"), INVALID("INVALID", "无效数据，人工订正等原因造成");

        private String code;
        private String name;

        OrderStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
