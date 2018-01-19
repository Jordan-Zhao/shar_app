package com.share.locker.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.LoginActivity;
import com.share.locker.MainActivity;
import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ruolan on 2015/11/29.
 */
@ContentView(R.layout.fragment_mine)
public class TabMineFragment extends Fragment{
    private final String TAG_LOG = "TabMineFragment";
    private final String URL_GET_USER_INFO = Constants.URL_BASE+"getMineData.json";

    private View view;

    @ViewInject(R.id.mine_icon)
    private ImageView mineIconImg;
    @ViewInject(R.id.mine_nick_txt)
    private TextView mineNickTxt;
    @ViewInject(R.id.mine_register_id_txt)
    private TextView mineRegisterIdTxt;
    @ViewInject(R.id.mine_login_btn)
    private Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        loadMineData();

        //登录按钮
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    /*@Event(value = R.id.mine_login_btn,
            type = View.OnClickListener.class*//*可选参数, 默认是View.OnClickListener.class*//*)
    private void onClickLoginBtn(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }*/

    public void loadMineData(){
        //从sharedRef取用户账号和密码信息，如果存在，则请求用户数据，不存在，则显示登录按钮
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_REF_NAME, Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString(Constants.SHARED_REF_KEY_LOGIN_info,null);
        if(loginInfo == null){
            //显示登录按钮
            showUnLogin();
        }else{
            String[] loginInfoArr = loginInfo.split(";");
            showLogin(loginInfoArr[0],loginInfoArr[1]);
        }
    }

    private void showUnLogin(){
        mineIconImg.setImageResource(R.drawable.un_login_icon);
        mineNickTxt.setVisibility(View.GONE);
        mineRegisterIdTxt.setVisibility(View.GONE);
        loginBtn.setVisibility(View.VISIBLE);
    }
    private void showLogin(String userName,String password){
        //从服务端加载数据
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("userName",userName);
        paramMap.put("password",password);
        LockerHttpUtil.postJson(URL_GET_USER_INFO, paramMap, new HttpCallback() {
            @Override
            public void processSuccess(final String successData) {
                //登录成功，服务端回返回用户基本信息，UI去显示用户信息
                getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showUserData(successData);
                            }
                        }
                );
            }
            @Override
            public void processFail(String failData) {
                //TODO 提示
            }
        });
    }

    private void showUserData(String userDataJson){
        try {
            JSONObject userJsonObj = new JSONObject(userDataJson);
            String userIconUrl = userJsonObj.getString("user_icon_url");
            String nick = userJsonObj.getString("nick");
            String email = userJsonObj.getString("email");
            String phoneNuber = userJsonObj.getString("phone_number");

            Glide.with(this).load(userIconUrl).into(mineIconImg);
            mineNickTxt.setVisibility(View.VISIBLE);
            mineNickTxt.setText(nick);
            mineRegisterIdTxt.setVisibility(View.VISIBLE);
            mineRegisterIdTxt.setText(email + ";" + phoneNuber);

            loginBtn.setVisibility(View.GONE);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Nullable
    @Override
    public void onResume(){
        super.onResume();
    }
}
