package com.share.locker.http;

import android.util.Log;
import android.widget.Toast;

import com.share.locker.util.LogUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Jordan on 16/01/2018.
 */

public class LockerHttpUtil {
    private final static String TAG_LOG = "LockerHttpUtil";

    /**
     * 发送http GET请求
     *
     * @param url
     * @return json String
     */
    public static void getJson(String url, final HttpCallback httpCallback) {
//        LockerHttpRequestParams params = new LockerHttpRequestParams();
        RequestParams params = new RequestParams(url);
//        params.wd = "xUtils";
        Callback.Cancelable cancelable = x.http().get(params,
            new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String resultJson) {
                    try {
                        JSONObject jsonObject = new JSONObject(resultJson);
                        Boolean isSuccess = jsonObject.getBoolean("isSuccess");
                        Object dataObj = jsonObject.get("data");
                        if (isSuccess) {
                            httpCallback.processSuccess(dataObj.toString());
                        } else {
                            httpCallback.processFail(dataObj.toString());
                        }
                    } catch (Exception ex) {
                        Log.e(TAG_LOG, LogUtil.getMsg("http response successfully, but process resultJson error."), ex);
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("http error",ex.toString());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e("http cancelled",cex.toString());
                }

                @Override
                public void onFinished() {
                    Log.e("http finished","finished");
                }
            });
    }
}
