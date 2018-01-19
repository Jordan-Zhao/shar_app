package com.share.locker.http;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

/**
 * Created by Jordan on 16/01/2018.
 */
@HttpRequest(
        host = "https://localhost:8080",
        path = "locker",
        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/)
public class LockerHttpRequestParams extends RequestParams {

}
