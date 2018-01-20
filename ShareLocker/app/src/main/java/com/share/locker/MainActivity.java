package com.share.locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.share.locker.common.LockerAppInitializer;
import com.share.locker.ui.Tab;
import com.share.locker.ui.fragment.TabCartFragment;
import com.share.locker.ui.fragment.TabHomeFragment;
import com.share.locker.ui.fragment.TabItemPublishFragment;
import com.share.locker.ui.fragment.TabMessageFragment;
import com.share.locker.ui.fragment.TabMineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Tab> bottomTabList = new ArrayList<>();
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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String tab = intent.getStringExtra("tab");
                    if(tab != null){
                        if("mine".equals(tab)){
                            fragTabhost.setCurrentTab(4);
                            String mineFragTag = fragTabhost.getCurrentTabTag();
                            TabMineFragment mineFragment = (TabMineFragment)getSupportFragmentManager().findFragmentByTag(mineFragTag);
                            mineFragment.loadMineData();
                        }
                    }
                }
        }
    }*/

    /**
     * 初始化应用程序
     */
    private void initApplication() {
        LockerAppInitializer lockerAppInitializer = new LockerAppInitializer(this);
        lockerAppInitializer.init();
    }

    /**
     * 系统初始化完成之后调用
     */
    public void excuseAfterInitApp() {
//        Toast.makeText(this, AppConfig.passwordMD5Key,Toast.LENGTH_LONG).show();

        initTab();
    }

    private void initTab() {
        Tab homeTab = new Tab(R.string.tab_home, R.drawable.selector_tab_home, TabHomeFragment.class);
        Tab cartTab = new Tab(R.string.tab_cart, R.drawable.selector_tab_cart, TabCartFragment.class);
        Tab itemPublishTab = new Tab(R.string.tab_item_publish, R.drawable.selector_tab_item_publish, TabItemPublishFragment.class);
        Tab msgTab = new Tab(R.string.tab_message, R.drawable.selector_tab_message, TabMessageFragment.class);
        Tab mineTab = new Tab(R.string.tab_mine, R.drawable.selector_tab_mine, TabMineFragment.class);

        bottomTabList.add(homeTab);
        bottomTabList.add(cartTab);
        bottomTabList.add(itemPublishTab);
        bottomTabList.add(msgTab);
        bottomTabList.add(mineTab);

        mainInflater = LayoutInflater.from(this);
        fragTabhost = (FragmentTabHost) findViewById(R.id.frag_tabhost);

        fragTabhost.setup(this, getSupportFragmentManager(), R.id.frame_main);

        for (Tab tab : bottomTabList) {
            TabHost.TabSpec tabSpec = fragTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(builderIndiator(tab));
            fragTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        //去掉分割线
        fragTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragTabhost.setCurrentTab(0);
    }

    private View builderIndiator(Tab tab) {
        View view = mainInflater.inflate(R.layout.tab_menu_item, null);
        ImageView imgView = view.findViewById(R.id.tab_item_icon);
        TextView textView = view.findViewById(R.id.tab_item_txt);
        imgView.setBackgroundResource(tab.getImage());
        textView.setText(tab.getTitle());
        return view;
    }
}
