package com.share.locker.ui.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.StringUtil;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.item.PublishItemActivity;
import com.share.locker.ui.main.MainActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    private final String URL_GET_VERIFY_CODE = Constants.URL_BASE + "mine/getRegisterVerifyCode.json";
    private final String URL_REGISTER = Constants.URL_BASE + "mine/register.json";

    @ViewInject(R.id.register_phone_txt)
    private EditText phoneText;
    @ViewInject(R.id.register_verify_code_txt)
    private EditText verifyCodeTxt;
    @ViewInject(R.id.register_password_txt1)
    private EditText pwdTxt1;
    @ViewInject(R.id.register_password_txt2)
    private EditText pwdTxt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

    }

    @Event(value = R.id.register_get_verify_code_btn,type= View.OnClickListener.class)
    private void onClickGetVerifyCodeBtn(View view){
        String phoneNumberStr = phoneText.getText().toString();
        if(!StringUtil.isPhoneNumber(phoneNumberStr)){
            GlobalManager.dialogManager.showTipDialog("请输入正确的手机号");
            return;
        }
        //post请求，服务端
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumberStr);
        LockerHttpUtil.postJson(URL_GET_VERIFY_CODE, paramMap, new HttpCallback() {
            @Override
            public void processSuccess(final String verifyCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    verifyCodeTxt.setText(verifyCode);
                    }
                });
            }

            @Override
            public void processFail(String failData) {
                GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
            }
        });
    }
    @Event(value = R.id.register_btn,type= View.OnClickListener.class)
    private void onClickRegisterBtn(View view){
        final String phoneNumberStr = phoneText.getText().toString();
        if(!StringUtil.isPhoneNumber(phoneNumberStr)){
            GlobalManager.dialogManager.showTipDialog("请输入正确的手机号");
            return;
        }
        final String password1 = pwdTxt1.getText().toString();
        String password2 = pwdTxt2.getText().toString();
        if(StringUtil.isEmpty(password1) || !password1.equals(password2)){
            GlobalManager.dialogManager.showTipDialog("请输入正确的密码");
            return;
        }
        String verifyCode = verifyCodeTxt.getText().toString();
        if(StringUtil.isEmpty(verifyCode)){
            GlobalManager.dialogManager.showTipDialog("请输入验证码");
        }

        //post请求，服务端
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumberStr);
        paramMap.put("password",password1);
        paramMap.put("verifyCode",verifyCode);
        LockerHttpUtil.postJson(URL_REGISTER, paramMap, new HttpCallback() {
            @Override
            public void processSuccess(final String userId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //把userName，password，userId存入SharedRef
                        BizUtil.saveValueInPref(Constants.SHARED_REF_KEY_LOGIN_info,
                                phoneNumberStr.trim() + ";" + password1.trim() +";"+userId, RegisterActivity.this);

                        //注册成功后，使mine页面下次显示时更新数据
                        GlobalManager.resumeManager.updateResumeData(Constants.FRAG_INDEX_MINE,null);

                        //注册成功后,销毁注册activity
                        finish();
                    }
                });
            }

            @Override
            public void processFail(String failData) {
                GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
            }
        });
    }

}
