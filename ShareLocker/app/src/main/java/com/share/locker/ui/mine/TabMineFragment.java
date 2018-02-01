package com.share.locker.ui.mine;

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
import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.UserDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseFragment;
import com.share.locker.ui.component.ClickManager;
import com.share.locker.vo.LoginUserVO;

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
    private final String URL_GET_RANDOM_USER = Constants.URL_BASE + "mine/getRandomUser.json";
    private final String KEY_USER_DTO = "KEY_USER_DTO";

    private View view;

    @ViewInject(R.id.mine_icon)
    private ImageView mineIconImg;
    @ViewInject(R.id.mine_nick_txt)
    private TextView mineNickTxt;
    //    @ViewInject(R.id.mine_email_txt)
//    private TextView mineEmailTxt;
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

    @ViewInject(R.id.mine_register_btn)
    private Button registerBtn;

    @ViewInject(R.id.mine_publish_item_list_layout)
    private LinearLayout publicItemListLayout;

    private UserDTO userDTO;//页面使用的数据

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadMineData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        if (userDTO == null && savedInstanceState != null && savedInstanceState.getSerializable(KEY_USER_DTO) != null) {
            //Fragment被回收
            userDTO = (UserDTO) savedInstanceState.getSerializable(KEY_USER_DTO);
        }
        showDataOnUi();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        showUiByLoginStatus();

        if (GlobalManager.resumeManager.isNeedRefresh(Constants.FRAG_INDEX_MINE)) {
            loadMineData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GlobalManager.resumeManager.clearResumeData(Constants.FRAG_INDEX_MINE);
    }

    //登录按钮
    @Event(value = R.id.mine_login_btn, type = View.OnClickListener.class)
    private void onClickLoginBtn(View view) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    //注册按钮
    @Event(value = R.id.mine_register_btn, type = View.OnClickListener.class)
    private void onClickRegisterBtn(View view) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }

    //退出登录按钮
    @Event(value = R.id.mine_loginout_btn, type = View.OnClickListener.class)
    private void onClickLoginoutBtn(View view) {
        //清理sharedRef数据
        BizUtil.removeValueFromPref(Constants.SHARED_REF_KEY_LOGIN_info, getActivity());

        showUiByLoginStatus();
    }

    //加载用户数据，public可调用
    private void loadMineData() {
        LoginUserVO loginUserVO = BizUtil.getLoginUser(getActivity());
        if (loginUserVO != null) {
            //用户登录过，根据用户名和密码加载数据，然后显示
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
                                                        userDTO = (UserDTO) JsonUtil.json2Object(successData, UserDTO.class);
                                                        showDataOnUi();
                                                    }
                                                }
                    );
                }

                @Override
                public void processFail(String failData) {
                    GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                }
            });
        }
    }

    /**
     * 销毁前保存数据，避免下次显示时去请求服务器
     *
     * @param savedBundle
     */
    @Override
    public void onSaveInstanceState(Bundle savedBundle) {
        super.onSaveInstanceState(savedBundle);

        savedBundle.putSerializable(KEY_USER_DTO, userDTO);
    }

    private void showDataOnUi() {
        if (userDTO != null) {
            Glide.with(TabMineFragment.this).load(userDTO.getIconUrl()).into(mineIconImg);
            mineNickTxt.setText(userDTO.getNick());
            minePhoneTxt.setText("注册手机号：" + userDTO.getPhoneStr());
        }
    }

    //我发布的宝贝 点击
    @Event(value = R.id.mine_publish_item_list_layout, type = View.OnClickListener.class)
    private void onClickPublishedItemLayout(View view) {
        //打开published item activity
        Intent intent = new Intent(getContext(), MinePublishItemListActivity.class);
        startActivity(intent);
    }

    //我 租用 的宝贝 点击
    @Event(value = R.id.mine_rent_order_layout, type = View.OnClickListener.class)
    private void onClickRentItemLayout(View view) {
        //打开租用的 item list activity
        Intent intent = new Intent(getContext(), MineRentItemListActivity.class);
        startActivity(intent);
    }

    /**
     * 根据登录状态显示UI
     */
    private void showUiByLoginStatus() {
        LoginUserVO loginUserVO = BizUtil.getLoginUser(getActivity());
        if (loginUserVO == null) {
            //未登录
            unloginLayout.setVisibility(View.VISIBLE);
            loginedLayout.setVisibility(View.GONE);
            logoutLayout.setVisibility(View.GONE);
        } else {
            //已登录
            loginedLayout.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(View.VISIBLE);
            unloginLayout.setVisibility(View.GONE);
        }
    }

    /**
     * TODO 这个方法只是测试用
     * @param view
     */
    @Event(value = R.id.mine_layout,type = View.OnClickListener.class)
    private void onClickLayout(View view){
        if(ClickManager.isTriggered("MINE_LAYOUT",2)){
            //触发2次连续点击，去服务端随机取另外一个用户
            Map<String, String> paramMap = new HashMap<>();
            LockerHttpUtil.postJson(URL_GET_RANDOM_USER, paramMap, new HttpCallback() {
                @Override
                public void processSuccess(final String successData) {
                    getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String[] userData =  successData.split("-");

                                                        //把userName和password存入SharedRef
                                                        BizUtil.saveValueInPref(Constants.SHARED_REF_KEY_LOGIN_info,
                                                                userData[0].trim() + ";" + userData[1].trim() +";"+userData[2],
                                                                getActivity());

                                                        //加载用户信息
                                                        loadMineData();
                                                    }
                                                }
                    );
                }

                @Override
                public void processFail(String failData) {
                    GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                }
            });
        }
    }
}
