package com.share.locker.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.share.locker.vo.LoginUserVO;

import java.io.File;

/**
 * Created by Jordan on 20/01/2018.
 */

public class BizUtil {

    /**
     * 获取已登录的用户信息，userName；password;userId
     *
     * @param context
     * @return 如果没有登录，则返回null.
     */
    public static LoginUserVO getLoginUser(Context context) {
        LoginUserVO loginUserVO = null;
        String loginInfo = getValueFromPref(Constants.SHARED_REF_KEY_LOGIN_info, context);
        if (loginInfo != null) {
            loginUserVO = new LoginUserVO();
            String[] loginInfoArr = loginInfo.split(";");
            loginUserVO.setUserName(loginInfoArr[0]);
            loginUserVO.setPassword(loginInfoArr[1]);
            loginUserVO.setUserId(Long.parseLong(loginInfoArr[2]));
        }
        return loginUserVO;
    }

    /**
     * 从SharedPref中获取数据
     *
     * @param key
     * @param context
     * @return
     */
    public static String getValueFromPref(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    /**
     * 从SharedPref中获取数据
     *
     * @param key
     * @param context
     * @return
     */
    public static void saveValueInPref(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 从SharedPref中删除数据
     *
     * @param key
     * @param context
     * @return
     */
    public static void removeValueFromPref(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 把android uri（content://media/external/images/media/69）转成file对象
     *
     * @param uri
     * @return
     */
    public static File convertUriToFile(Uri uri, Context context) {
        String filePath = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(column_index);
        }
        cursor.close();
        return new File(filePath);
    }
}
