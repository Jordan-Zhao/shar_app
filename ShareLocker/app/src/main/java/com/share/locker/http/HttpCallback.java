package com.share.locker.http;

/**
 * Created by Jordan on 16/01/2018.
 */

public interface HttpCallback {
    void processSuccess(String successData);
    void processFail(String failData);
}
