package com.share.locker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.ui.cart.TabCartFragment;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.home.TabHomeFragment;
import com.share.locker.ui.init.LockerAppInitializer;
import com.share.locker.ui.item.ItemPublishActivity;
import com.share.locker.ui.item.TabItemPublishFragment;
import com.share.locker.ui.message.TabMessageFragment;
import com.share.locker.ui.mine.LoginActivity;
import com.share.locker.ui.mine.TabMineFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * UI主体框架。
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private LayoutInflater mainInflater;

    @ViewInject(R.id.frag_tabhost)
    public FragmentTabHost fragTabhost;
    private List<MainTabData> bottomTabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainInflater = LayoutInflater.from(this);

        //xutil注入
        x.view().inject(this);

        initApplication();
    }

    /**
     * 由于MainActivity是singleTask模式，从外面activity发intent过来时，需要更新intent
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //当从外面activity回调回来时，判断intent，显示不同的fragment
//        Intent intent = getIntent();
//        int toWhereCode = intent.getIntExtra(Constants.INTENT_KEY_MAIN_TO_WHERE,-1);
//        if(toWhereCode == Constants.MainIntentToWhereCode.TO_MINE_FRAG.getCode()){
//            if(fragTabhost.getCurrentTab() != Constants.FRAG_INDEX_MINE) {
//                //切换到发布宝贝完成的fragment
//                fragTabhost.setCurrentTab(Constants.FRAG_INDEX_MINE);
//            }
//        }
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

        fragTabhost.setup(this, getSupportFragmentManager(), R.id.frame_main);

        for (MainTabData tab : bottomTabList) {
            TabHost.TabSpec tabSpec = fragTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(createBottomIndiator(tab));
            fragTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        //去掉分割线
        fragTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragTabhost.setCurrentTab(0);
    }

    //创建底部tab的item view
    private View createBottomIndiator(MainTabData tab) {
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
        //“发布宝贝”的tab图标 ，点击时，需要先判断是否登录。
        fragTabhost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BizUtil.getLoginUser(MainActivity.this) == null){
                    //未登录，跳转到登录页面
                    Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                    loginIntent.putExtra(Constants.INTENT_KEY_JUMP_TO,MainActivity.class);
                    startActivity(loginIntent);
                }
                else{
                    //如果已登录，跳转到发布宝贝页面
                    Intent intent = new Intent(MainActivity.this,ItemPublishActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
