package com.share.locker.http;

import android.net.Uri;
import android.util.Log;

import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.ImageUtil;
import com.share.locker.common.LogUtil;
import com.share.locker.vo.LoginUserVO;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jordan on 16/01/2018.
 */

public class LockerHttpUtil {
    private final static String TAG_LOG = "LockerHttpUtil";

    /**
     * post请求，服务端返回json,转交给httpCallBack处理
     *
     * @param url
     * @param httpCallback
     */
    public static void postJson(String url, Map<String, String> paramMap, final HttpCallback httpCallback) {
        GlobalManager.dialogManager.showLoopDialog();
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
                GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
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
                GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
                GlobalManager.dialogManager.showErrorDialogInUiThread("网络请求出错"+ex.toString());
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                Log.e("http cancelled", cex.toString());
                GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
            }

            @Override
            public void onFinished() {
                Log.e("http finished", "finished");
                GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
            }
        });
    }

    //上传文件
    public static void postFileJson(final String url, final Map<String, String> paramMap, final List<Uri> fileUriList,
                                     final HttpCallback httpCallback) {
        GlobalManager.dialogManager.showLoopDialog();
        //开启线程上传，因为图片处理不能在主线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(url);
                if(paramMap != null && paramMap.keySet() != null){
                    for(String key : paramMap.keySet()){
                        params.addBodyParameter(key,paramMap.get(key));
                    }
                }
                final List<File> uploadFileList = new ArrayList<>();
                if(fileUriList != null && fileUriList.size() > 0){
                    params.setMultipart(true);
                    int i=0;
                    for(Uri fileUri : fileUriList){
                        File uploadFile = ImageUtil.zoomImgAndSave(GlobalManager.currentActivity,fileUri, Constants.UPLOAD_IMAGE_WIDTH);
                        params.addBodyParameter("file"+i,uploadFile,null);
                        i++;
                        uploadFileList.add(uploadFile);
                    }
                }
                addLoginUserToRequestParams(params);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String resultJson) {
                        ImageUtil.deleteTmpImages(uploadFileList);
                        GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
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
                        ImageUtil.deleteTmpImages(uploadFileList);
                        GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
                        GlobalManager.dialogManager.showErrorDialogInUiThread("网络请求出错"+ ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.e("http cancelled", cex.toString());
                        ImageUtil.deleteTmpImages(uploadFileList);
                        GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
                    }

                    @Override
                    public void onFinished() {
                        Log.e("http finished", "finished");
                        ImageUtil.deleteTmpImages(uploadFileList);
                        GlobalManager.dialogManager.removeCurrentLoopDialogInUiThread();
                    }
                });
            }
        }).start();
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
        LoginUserVO loginUserVO = BizUtil.getLoginUser(GlobalManager.currentActivity);
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


    /**
     * 发送http GET请求，服务端返回json,转交给httpCallBack处理
     *
     * @param url
     */
    /*public static void getJson(String url, final HttpCallback httpCallback) {
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
    }*/


}
