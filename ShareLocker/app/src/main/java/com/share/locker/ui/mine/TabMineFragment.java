package com.share.locker.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.common.GlobalManager;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.ui.component.BaseFragment;
import com.share.locker.vo.LoginUserVO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.vo.ResumeRefreshVO;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * “我的” 页面
 * Created by ruolan on 2015/11/29.
 */
@ContentView(R.layout.fragment_mine)
public class TabMineFragment extends BaseFragment {
    private final String TAG_LOG = "TabMineFragment";
    private final String URL_GET_USER_INFO = Constants.URL_BASE + "mine/getMineData.json";

    private View view;

    @ViewInject(R.id.mine_icon)
    private ImageView mineIconImg;
    @ViewInject(R.id.mine_nick_txt)
    private TextView mineNickTxt;
    @ViewInject(R.id.mine_email_txt)
    private TextView mineEmailTxt;
    @ViewInject(R.id.mine_phone_txt)
    private TextView minePhoneTxt;
    @ViewInject(R.id.mine_login_btn)
    private Button loginBtn;

    @ViewInject(R.id.mine_logined_layout)
    private LinearLayout loginedLayout;
    @ViewInject(R.id.mine_unlogin_layout)
    private LinearLayout unloginLayout;
    @ViewInject(R.id.mine_logout_layout)
    private LinearLayout logoutLayout;

    @ViewInject(R.id.mine_loginout_btn)
    private Button loginOutBtn;

    @ViewInject(R.id.mine_publish_item_list_layout)
    private LinearLayout publicItemListLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        loadMineData();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(GlobalManager.resumeManager.isNeedRefresh(Constants.FRAG_INDEX_MINE)){
            loadMineData();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        GlobalManager.resumeManager.clearResumeData(Constants.FRAG_INDEX_MINE);
    }

    //登录按钮
    @Event(value = R.id.mine_login_btn, type = View.OnClickListener.class)
    private void onClickLoginBtn(View view) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(Constants.KEY_LOGINED_JUMP,Constants.LOGINED_JUMP_TO_MINE);
        startActivity(intent);
    }

    //退出登录按钮
    @Event(value = R.id.mine_loginout_btn, type = View.OnClickListener.class)
    private void onClickLoginoutBtn(View view) {
        //清理sharedRef数据
        BizUtil.removeValueFromPref(Constants.SHARED_REF_KEY_LOGIN_info, getActivity());

        showUnLoginUi();
    }

    //加载用户数据，public可调用
    private void loadMineData() {
        //从sharedRef取用户账号和密码信息，如果存在，则请求用户数据，不存在，则显示登录按钮
        LoginUserVO loginUserVO = BizUtil.getLoginUser(getActivity());
        if (loginUserVO == null) {
            //显示登录按钮
            showUnLoginUi();
        } else {
            showLogin(loginUserVO);
        }
    }

    //用户未登录过，显示未登录时的页面
    private void showUnLoginUi() {
        loginedLayout.setVisibility(View.GONE);
        unloginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }

    //用户登录过，根据用户名和密码加载数据，然后显示
    private void showLogin(LoginUserVO loginUserVO) {
        //从服务端加载数据
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userName", loginUserVO.getUserName());
        paramMap.put("password", loginUserVO.getPassword());
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

    //加载到user数据后，显示到UI
    private void showUserData(String userDataJson) {
        try {
            JSONObject userJsonObj = new JSONObject(userDataJson);
            String userIconUrl = userJsonObj.getString("user_icon_url");
            String nick = userJsonObj.getString("nick");
            String email = userJsonObj.getString("email");
            String phoneNuber = userJsonObj.getString("phone_number");

            Glide.with(this).load(userIconUrl).into(mineIconImg);
            mineNickTxt.setText(nick);
            mineEmailTxt.setText("邮箱：" + email);
            minePhoneTxt.setText("电话：" + phoneNuber);

            showLoginedUi();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //显示登录后的UI
    private void showLoginedUi() {
        loginedLayout.setVisibility(View.VISIBLE);
        unloginLayout.setVisibility(View.GONE);
        logoutLayout.setVisibility(View.VISIBLE);
    }

    //我发布的宝贝 点击
    @Event(value = R.id.mine_publish_item_list_layout, type = View.OnClickListener.class)
    private void onClickPublishedItemLayout(View view) {
        //打开published item activity
        Intent intent = new Intent(getContext(), MinePublishItemListActivity.class);
        startActivity(intent);
    }

}
