package com.share.locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    private final String TAG_LOG = "LoginActivity";
    private final String URL_LOGIN = Constants.URL_BASE + "login.json";
    private View view;

    @ViewInject(R.id.login_email_phone_txt)
    private EditText emailPhoneTxt;
    @ViewInject(R.id.login_password_txt)
    private EditText passwordTxt;
    @ViewInject(R.id.login_register_txt)
    private TextView loginRegisterTxt;
    @ViewInject(R.id.login_btn)
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xutil注入
        x.view().inject(this);

        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                processLoginClick();
            }
        });
    }

    private void processLoginClick() {
        final String userName = emailPhoneTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        //TODO 条件判断

        //post请求，服务端
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("password", password);
        LockerHttpUtil.postJson(URL_LOGIN, paramMap, new HttpCallback() {
            @Override
            public void processSuccess(String successData) {
                //登录成功，跳转到main activity
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //把userName和password存入SharedRef
                        BizUtil.saveValueInPref(Constants.SHARED_REF_KEY_LOGIN_info, userName + ";" + password, LoginActivity.this);

                        //登录成功后，返回到上一个页面
                        backToLastPage();
                    }
                });
            }

            @Override
            public void processFail(String failData) {
                //TODO 失败，给出失败原因提示
            }
        });
    }

    /**
     * 登录成功后，返回到上一个页面
     */
    private void backToLastPage(){
        Intent inIntent = getIntent();
        String jumpTo = inIntent.getStringExtra(Constants.KEY_LOGINED_JUMP);
        if(Constants.LOGINED_JUMP_TO_MINE.equals(jumpTo)) {
            //跳转到mine fragment
            Intent mineFragIntent = new Intent();
            setResult(RESULT_OK, mineFragIntent);
            finish();
        }else if(Constants.LOGINED_JUMP_TO_PUBLISH_ITEM.equals(jumpTo)){
            //跳转到发布宝贝的activity
            Intent publishItemIntent = new Intent(this,PublishItemActivity.class);
            startActivity(publishItemIntent);
        }
    }
}

