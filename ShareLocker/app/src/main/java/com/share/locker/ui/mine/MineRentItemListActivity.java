package com.share.locker.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.OrderDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.component.RecyclerItemViewClickListener;
import com.share.locker.ui.order.OrderPayDepositActivity;
import com.share.locker.ui.order.OrderPayFeeActivity;
import com.share.locker.ui.order.OrderReturnLockerActivity;
import com.share.locker.ui.order.OrderTakeItemActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_mine_rent_item_list)
public class MineRentItemListActivity extends BaseActivity implements RecyclerItemViewClickListener {
    private static final String URL_GET_RENT_ORDER_LIST = Constants.URL_BASE + "mine/getMyRentOrderList.json";

    private View view;

    @ViewInject(R.id.mine_rent_list_view)
    private RecyclerView itemListView;

    //服务端返回的
    private List<OrderDTO> itemDTOList;

    private MineRentItemRecyclerAdapter itemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil注入
        x.view().inject(this);

        loadRentItemList();
    }

    private void loadRentItemList() {
        Map<String, String> paramMap = new HashMap<>();
        LockerHttpUtil.postJson(URL_GET_RENT_ORDER_LIST, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itemDTOList = JsonUtil.json2List(successData, new TypeToken<List<OrderDTO>>() {});
                                showDataOnUi();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog(failData);
                    }
                });
    }

    /**
     * itemView的点击事件
     */
    @Override
    public void onClickRecyclerItemView(String eventCode,Object data){
        OrderDTO orderDTO = (OrderDTO)data;
        if(eventCode.equals(MineRentItemRecyclerAdapter.CLICK_CODE_ITEM_VIEW)){
            //点击整个itemView,跳转到order detail页
            //TODO
        }else if(eventCode.equals(MineRentItemRecyclerAdapter.CLICK_CODE_PAY_DEPOSIT_BTN)){
            //点击支付押金
            Intent intent = new Intent(MineRentItemListActivity.this, OrderPayDepositActivity.class);
            intent.putExtra("orderId", orderDTO.getOrderId());
            startActivity(intent);
        }else if(eventCode.equals(MineRentItemRecyclerAdapter.CLICK_CODE_TAKE_BTN)){
            //点击取件btn
            Intent intent = new Intent(MineRentItemListActivity.this, OrderTakeItemActivity.class);
            intent.putExtra("orderId", orderDTO.getOrderId());
            startActivity(intent);
        }else if(eventCode.equals(MineRentItemRecyclerAdapter.CLICK_CODE_RETURN_BTN)){
            //点击还件btn
            if(Constants.OrderStatus.USING.getCode().equals(orderDTO.getStatus())){
                //租用中，跳转到支付租金页面
                Intent intent = new Intent(MineRentItemListActivity.this, OrderPayFeeActivity.class);
                intent.putExtra("orderId", orderDTO.getOrderId());
                startActivity(intent);
            }else if(Constants.OrderStatus.GENERATED_RETURN_QRCODE.getCode().equals(orderDTO.getStatus())){
                //已经支付租金，并且生成了换件二维码
                Intent intent = new Intent(MineRentItemListActivity.this, OrderReturnLockerActivity.class);
                intent.putExtra("orderId", orderDTO.getOrderId());
                startActivity(intent);
            }
        }

    }

    private void showDataOnUi() {
        if(itemDTOList != null) {
            //layout
            LinearLayoutManager layoutManager = new LinearLayoutManager(MineRentItemListActivity.this);
            itemListView.setLayoutManager(layoutManager);
            //list 数据 和 adapter
            itemRecyclerAdapter = new MineRentItemRecyclerAdapter(this, itemDTOList);
            itemListView.setAdapter(itemRecyclerAdapter);
        }
    }
}
