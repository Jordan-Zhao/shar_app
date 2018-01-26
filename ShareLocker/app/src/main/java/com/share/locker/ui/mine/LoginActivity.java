package com.share.locker.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.share.locker.common.GlobalManager;
import com.share.locker.common.StringUtil;
import com.share.locker.ui.item.PublishItemActivity;
import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.vo.ResumeRefreshVO;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private final String TAG_LOG = "LoginActivity";
    private final String URL_LOGIN = Constants.URL_BASE + "mine/login.json";
    private View view;

    @ViewInject(R.id.login_email_phone_txt)
    private EditText emailPhoneTxt;
    @ViewInject(R.id.login_password_txt)
    private EditText passwordTxt;
    @ViewInject(R.id.login_register_txt)
    private TextView loginRegisterTxt;
    @ViewInject(R.id.login_btn)
    private Button loginBtn;

    @ViewInject(R.id.login_register_txt)
    private TextView registerTxt;

    @ViewInject(R.id.login_forget_password_txt)
    private TextView forgetPwdTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xutil注入
        x.view().inject(this);

    }

    @Event(value=R.id.login_btn,type = View.OnClickListener.class)
    private void onClickLoginBtn(View view) {
        final String userName = emailPhoneTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        if(!StringUtil.isPhoneNumber(userName)){
            GlobalManager.dialogManager.showTipDialog("请输入正确的手机号");
            return;
        }
        if(StringUtil.isEmpty(password)){
            GlobalManager.dialogManager.showTipDialog("请输入密码");
            return;
        }

        //post请求，服务端
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("password", password);
        LockerHttpUtil.postJson(URL_LOGIN, paramMap, new HttpCallback() {
            @Override
            public void processSuccess(final String userId) {
                //登录成功，跳转到main activity
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //把userName和password存入SharedRef
                        BizUtil.saveValueInPref(Constants.SHARED_REF_KEY_LOGIN_info,
                                userName.trim() + ";" + password.trim() +";"+userId,
                                LoginActivity.this);

                        //登录成功后，使mine页面下次显示时更新数据
                        GlobalManager.resumeManager.updateResumeData(Constants.FRAG_INDEX_MINE,null);

                        //登录成功后，跳转到下个页面
                        toNextPage();
                    }
                });
            }

            @Override
            public void processFail(String failData) {
                GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
            }
        });
    }

    @Event(value=R.id.login_register_txt,type = View.OnClickListener.class)
    private void onClickRegisterTxt(View view) {
        Intent toIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        /*Intent fromIntent = getIntent();
        String jumpTo = fromIntent.getStringExtra(Constants.KEY_LOGINED_JUMP);
        if(!StringUtil.isEmpty(jumpTo)){
            toIntent.putExtra(Constants.KEY_REGISTER_JUMP,jumpTo);
        }*/
        startActivity(toIntent);
        finish();
    }

    /**
     * 登录成功后，跳转到下个页面
     */
    private void toNextPage(){
        Intent inIntent = getIntent();
        if(toActivityClass != null){
            Intent intent = new Intent(LoginActivity.this, toActivityClass);
            startActivity(intent);
        }
        finish(); //销毁activity
    }
}

