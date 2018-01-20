package com.share.locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.LockerAppInitializer;
import com.share.locker.ui.MainTabData;
import com.share.locker.ui.fragment.TabCartFragment;
import com.share.locker.ui.fragment.TabHomeFragment;
import com.share.locker.ui.fragment.TabItemPublishFragment;
import com.share.locker.ui.fragment.TabMessageFragment;
import com.share.locker.ui.fragment.TabMineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MainTabData> bottomTabList = new ArrayList<>();
    private LayoutInflater mainInflater;
    public FragmentTabHost fragTabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApplication();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    /**
     * 初始化应用程序
     */
    private void initApplication() {
        LockerAppInitializer lockerAppInitializer = new LockerAppInitializer(this);
        lockerAppInitializer.init();
    }

    /**
     * lockerAppInitializer加载初始化数据后，调用
     */
    public void excuseAfterInitApp() {
        initTab();

        addAllEventListener();
    }

    /**
     * 构建页面主架构，使用FragmentTabHost + 5个Fragment
     */
    private void initTab() {
        MainTabData homeTab = new MainTabData(R.string.tab_home, R.drawable.selector_tab_home, TabHomeFragment.class);
        MainTabData cartTab = new MainTabData(R.string.tab_cart, R.drawable.selector_tab_cart, TabCartFragment.class);
        MainTabData itemPublishTab = new MainTabData(R.string.tab_item_publish, R.drawable.selector_tab_item_publish, TabItemPublishFragment.class);
        MainTabData msgTab = new MainTabData(R.string.tab_message, R.drawable.selector_tab_message, TabMessageFragment.class);
        MainTabData mineTab = new MainTabData(R.string.tab_mine, R.drawable.selector_tab_mine, TabMineFragment.class);

        bottomTabList.add(homeTab);
        bottomTabList.add(cartTab);
        bottomTabList.add(itemPublishTab);
        bottomTabList.add(msgTab);
        bottomTabList.add(mineTab);

        mainInflater = LayoutInflater.from(this);
        fragTabhost = (FragmentTabHost) findViewById(R.id.frag_tabhost);

        fragTabhost.setup(this, getSupportFragmentManager(), R.id.frame_main);

        for (MainTabData tab : bottomTabList) {
            TabHost.TabSpec tabSpec = fragTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(builderIndiator(tab));
            fragTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        //去掉分割线
        fragTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragTabhost.setCurrentTab(0);
    }

    //创建底部tab的item view
    private View builderIndiator(MainTabData tab) {
        View view = mainInflater.inflate(R.layout.tab_menu_item, null);
        ImageView imgView = view.findViewById(R.id.tab_item_icon);
        TextView textView = view.findViewById(R.id.tab_item_txt);
        imgView.setBackgroundResource(tab.getImage());
        textView.setText(tab.getTitle());
        return view;
    }

    /**
     * main页面添加Listener
     */
    private void addAllEventListener(){
        //“发布宝贝”的tab ，点击时，跳转到activity，而不是切换fragment
        fragTabhost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BizUtil.getLoginUser(MainActivity.this) == null){
                    //未登录，跳转到登录页面
                    Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                    loginIntent.putExtra(Constants.KEY_LOGINED_JUMP,Constants.LOGINED_JUMP_TO_PUBLISH_ITEM);
                    startActivity(loginIntent);
                }
                else{
                    //如果已登录，跳转到发布宝贝页面
                    Intent intent = new Intent(MainActivity.this,PublishItemActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
