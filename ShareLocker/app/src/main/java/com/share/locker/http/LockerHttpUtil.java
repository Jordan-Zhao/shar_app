package com.share.locker.http;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.LoginUserVO;
import com.share.locker.util.LogUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by Jordan on 16/01/2018.
 */

public class LockerHttpUtil {
    private final static String TAG_LOG = "LockerHttpUtil";

    /**
     * 发送http GET请求，服务端返回json,转交给httpCallBack处理
     *
     * @param url
     */
    public static void getJson(String url, final HttpCallback httpCallback) {
//        LockerHttpRequestParams params = new LockerHttpRequestParams();
        RequestParams params = new RequestParams(url);
//        params.wd = "xUtils";
        addLoginUserToRequestParams(params);
        Callback.Cancelable cancelable = x.http().get(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String resultJson) {
                        LockerHttpResponseData responseData = convertResponseJson(resultJson);
                        if (responseData.isSuccess()) {
                            httpCallback.processSuccess(responseData.getData());
                        } else {
                            httpCallback.processFail(responseData.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("http error", ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.e("http cancelled", cex.toString());
                    }

                    @Override
                    public void onFinished() {
                        Log.e("http finished", "finished");
                    }
                });
    }

    /**
     * post请求，服务端返回json,转交给httpCallBack处理
     *
     * @param url
     * @param httpCallback
     */
    public static void postJson(String url, Map<String, String> paramMap, final HttpCallback httpCallback) {
        RequestParams params = new RequestParams(url);
        if(paramMap != null && paramMap.keySet() != null){
            for(String key : paramMap.keySet()){
                params.addBodyParameter(key,paramMap.get(key));
            }
        }
        addLoginUserToRequestParams(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultJson) {
                LockerHttpResponseData responseData = convertResponseJson(resultJson);
                if (responseData.isSuccess()) {
                    httpCallback.processSuccess(responseData.getData());
                } else {
                    httpCallback.processFail(responseData.getData());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("http error", ex.toString());
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                Log.e("http cancelled", cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("http finished", "finished");
            }
        });
    }

    //上传文件
    public static void postFileJson(String url, Map<String, String> paramMap, List<File> fileList,
                                     final HttpCallback httpCallback) {
        RequestParams params = new RequestParams(url);
        if(paramMap != null && paramMap.keySet() != null){
            for(String key : paramMap.keySet()){
                params.addBodyParameter(key,paramMap.get(key));
            }
        }
        if(fileList != null && fileList.size() > 0){
            params.setMultipart(true);
            int i=0;
            for(File file : fileList){
                params.addBodyParameter("file"+i,file,null);
                i++;
            }
        }
        addLoginUserToRequestParams(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultJson) {
                LockerHttpResponseData responseData = convertResponseJson(resultJson);
                if (responseData.isSuccess()) {
                    httpCallback.processSuccess(responseData.getData());
                } else {
                    httpCallback.processFail(responseData.getData());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("http error", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("http cancelled", cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("http finished", "finished");
            }
        });
    }

    private static LockerHttpResponseData convertResponseJson(String resultJson){
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            Boolean isSuccess = jsonObject.getBoolean("isSuccess");
            Object dataObj = jsonObject.get("data");
            LockerHttpResponseData responseData = new LockerHttpResponseData();
            responseData.setSuccess(isSuccess);
            responseData.setData(dataObj.toString());
            return responseData;
        } catch (Exception ex) {
            Log.e(TAG_LOG, LogUtil.getMsg("http response successfully, but process resultJson error."), ex);
            throw new RuntimeException(ex);
        }
    }

    private static void addLoginUserToRequestParams(RequestParams params){
        LoginUserVO loginUserVO = BizUtil.getLoginUser(GlobalManager.currentContext);
        if(loginUserVO != null){
            List<KeyValue> paramList = params.getStringParams();
            if(paramList != null) {
                boolean isContainUserName = false;
                boolean isContainPassword = false;
                for (KeyValue kv : paramList) {
                    if("userName".equals(kv.key)){
                        isContainUserName = true;
                    }else if("password".equals(kv.key)){
                        isContainPassword = true;
                    }
                }
                if(!isContainUserName && !isContainPassword){
                    //!!!只能addQueryStringParameter，不能addBodyParameter到body中，因为如果是文件表单，服务端无法通过getParam取到这个值
                    params.addQueryStringParameter("userName",loginUserVO.getUserName());
                    params.addQueryStringParameter("password",loginUserVO.getPassword());
                }
            }
        }
    }

    static class LockerHttpResponseData{
        private boolean isSuccess;
        private String data;

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
