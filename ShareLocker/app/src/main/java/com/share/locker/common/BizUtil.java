package com.share.locker.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jordan on 20/01/2018.
 */

public class BizUtil {

    /**
     * 获取已登录的用户信息，userName；password。
     * @param context
     * @return 如果没有登录，则返回null.
     */
    public static LoginUserVO getLoginUser(Context context){
        LoginUserVO loginUserVO = null;
        String loginInfo = getValueFromPref(Constants.SHARED_REF_KEY_LOGIN_info, context);
        if(loginInfo != null) {
            loginUserVO = new LoginUserVO();
            String[] loginInfoArr = loginInfo.split(";");
            loginUserVO.setUserName(loginInfoArr[0]);
            loginUserVO.setPassword(loginInfoArr[1]);
        }
        return loginUserVO;
    }

    /**
     * 从SharedPref中获取数据
     * @param key
     * @param context
     * @return
     */
    public static String getValueFromPref(String key, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    /**
     * 从SharedPref中获取数据
     * @param key
     * @param context
     * @return
     */
    public static void saveValueInPref(String key, String value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    /**
     * 从SharedPref中删除数据
     * @param key
     * @param context
     * @return
     */
    public static void removeValueFromPref(String key, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
