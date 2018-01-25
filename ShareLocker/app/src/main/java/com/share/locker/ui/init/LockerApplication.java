package com.share.locker.ui.init;

import android.app.Application;

import com.share.locker.common.Constants;

import org.xutils.x;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 自定义Application
 * Created by Jordan
 */
public class LockerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initXutil();

    }

    private void initXutil(){
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //校验域名
                if(Constants.URL_BASE.contains(hostname)) {
                    return true;
                }else{
                    return false;
                }
            }
        });
    }



}
