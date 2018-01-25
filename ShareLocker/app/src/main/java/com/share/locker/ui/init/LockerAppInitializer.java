package com.share.locker.ui.init;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.share.locker.common.GlobalManager;
import com.share.locker.ui.component.DialogManager;
import com.share.locker.ui.component.ResumeRefreshManager;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.vo.AppConfigVO;

import java.util.HashMap;

/**
 * 初始化app
 * Created by Jordan on 16/01/2018.
 */

public class LockerAppInitializer {
    private MainActivity mainActivity;
    private final String URL_GET_APP_CONFIG = Constants.URL_BASE + "app/getAppConfig.json";

    public LockerAppInitializer(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void init() {
        //初始化全局管理类对象
        GlobalManager.resumeManager = new ResumeRefreshManager();
        GlobalManager.dialogManager = new DialogManager();

        //申请权限
        //写存储卡
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //读存储卡
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        //检查系统配置信息是否存在
        String config = BizUtil.getValueFromPref(Constants.SHARED_REF_KEY_APP_CONFIG, mainActivity);
        if (config == null) {
            //没有配置信息，从接口获取
            LockerHttpUtil.postJson(URL_GET_APP_CONFIG,new HashMap<String, String>(),
                    new HttpCallback() {
                        @Override
                        public void processSuccess(String successData) {
                            //保存到sharedPref
                            BizUtil.saveValueInPref(Constants.SHARED_REF_KEY_APP_CONFIG, successData, mainActivity);

                            initAppByConfig(successData);
                        }

                        @Override
                        public void processFail(String failData) {
                            GlobalManager.dialogManager.showErrorDialogInUiThread("服务端处理失败："+failData);
                        }
                    });
        } else {
            initAppByConfig(config);
        }

    }

    private void initAppByConfig(String config){
        //初始化配置对象
        AppConfigVO.init(config);

        //系统初始化完成后，执行界面操作
        mainActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.excuseAfterInitApp();
                    }
                });
    }
}
