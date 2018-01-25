package com.share.locker.vo;

import android.util.Log;

import com.share.locker.common.Constants;
import com.share.locker.common.JsonUtil;
import com.share.locker.common.LogUtil;

import java.util.Map;

/**
 * 系统配置类
 * Created by Jordan on 16/01/2018.
 */

public class AppConfigVO {
    private final static String TAG_LOG = "AppConfigVO";

    public static String passwordMD5Key;

    public static void init(String appConfigJson) {
        Log.i(TAG_LOG, LogUtil.getMsg("init app config.", appConfigJson));
        Map map = JsonUtil.json2Map(appConfigJson);
        passwordMD5Key = (String) map.get(Constants.PASSWORD_MD5_KEY);
    }
}
