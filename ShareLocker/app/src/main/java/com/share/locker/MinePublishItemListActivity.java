package com.share.locker;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.share.locker.common.Constants;
import com.share.locker.common.dto.ItemDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.MinePublishItemRecyclerAdapter;
import com.share.locker.util.JsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_mine_publish_item_list)
public class MinePublishItemListActivity extends BaseActivity {
    private static final String URL_GET_PUBLISHED_ITEM_LIST = Constants.URL_BASE + "getMyPublishItems.json";

    private View view;

    @ViewInject(R.id.mine_publish_item_list_view)
    private RecyclerView itemListView;

    //服务端返回的
    private List<ItemDTO> itemDTOList;

    private MinePublishItemRecyclerAdapter itemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil注入
        x.view().inject(this);

        loadPublishedItemList();
    }

    private void loadPublishedItemList() {
        Map<String, String> paramMap = new HashMap<>();
        LockerHttpUtil.postJson(URL_GET_PUBLISHED_ITEM_LIST, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itemDTOList = JsonUtil.json2List(successData, ItemDTO.class);
                                showDataOnUi();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {

                    }
                });
    }

    private void showDataOnUi() {
        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(MinePublishItemListActivity.this);
        itemListView.setLayoutManager(layoutManager);
        //list 数据 和 adapter
        itemRecyclerAdapter = new MinePublishItemRecyclerAdapter(this, itemDTOList);
        itemListView.setAdapter(itemRecyclerAdapter);
    }
}
