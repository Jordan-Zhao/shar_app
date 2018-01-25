package com.share.locker.ui.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.vo.LoginUserVO;
import com.share.locker.dto.ItemDetailDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.common.JsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_item_detail)
public class ItemDetailActivity extends AppCompatActivity {
    private static final String URL_GET_ITEM_DETAIL = Constants.URL_BASE + "getItemDetail.json";
    private static final String URL_RENT = Constants.URL_BASE + "rent.json";
    private static final String URL_ITEM_ON_LINE = Constants.URL_BASE + "onLineItem.json";
    private static final String URL_ITEM_OFF_LINE = Constants.URL_BASE + "offLineItem.json";
    private static final String URL_ITEM_DELETE = Constants.URL_BASE + "deleteItem.json";

    @ViewInject(R.id.detail_title_txt)
    private TextView titleTxt;
    @ViewInject(R.id.detail_price_txt)
    private TextView priceTxt;
    @ViewInject(R.id.detail_deposit_txt)
    private TextView depositTxt;
    @ViewInject(R.id.detail_machine_txt)
    private TextView machineTxt;
    @ViewInject(R.id.detail_comment_txt)
    private TextView commentTxt;

    @ViewInject(R.id.detail_description_txt)
    private TextView descriptionTxt;

    @ViewInject(R.id.detail_img_list)
    private ListView imgListView;

    @ViewInject(R.id.detail_renter_opt_layout)
    private LinearLayout renterOptLayout;

    @ViewInject(R.id.detail_owner_opt_layout)
    private LinearLayout ownerOptLayout;

    private Long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil注入
        x.view().inject(this);

        itemId = getIntent().getLongExtra("itemId",0L);
        loadItemDetail(itemId);
    }

    //“立即租用”按钮
    @Event(value = R.id.detail_rent_btn, type = View.OnClickListener.class)
    private void onClickRentBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_RENT, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO 提示
                                Toast.makeText(ItemDetailActivity.this,successData,1000*1000*10).show();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        //TODO 提示租用失败
                    }
                });
    }

    //“上架”按钮
    @Event(value = R.id.detail_on_line_btn, type = View.OnClickListener.class)
    private void onClickOnlineBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_ON_LINE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO 提示
                                Toast.makeText(ItemDetailActivity.this,"上架成功",1000*1000*10).show();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        //TODO 提示
                    }
                });
    }

    //“下架”按钮
    @Event(value = R.id.detail_off_line_btn, type = View.OnClickListener.class)
    private void onClickOfflineBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_OFF_LINE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO 提示
                                Toast.makeText(ItemDetailActivity.this,"下架成功",1000*1000*10).show();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        //TODO 提示
                    }
                });
    }

    //“删除”按钮
    @Event(value = R.id.detail_delete_btn, type = View.OnClickListener.class)
    private void onClickDeleteBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_DELETE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO 提示
                                Toast.makeText(ItemDetailActivity.this,"删除成功",1000*1000*10).show();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        //TODO 提示
                    }
                });
    }


    private void loadItemDetail(Long itemId){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        LockerHttpUtil.postJson(URL_GET_ITEM_DETAIL, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ItemDetailDTO detailDTO = (ItemDetailDTO)JsonUtil.json2Object(successData,ItemDetailDTO.class);
                                showItemDetailOnUi(detailDTO);
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {

                    }
                });
    }

    private void showItemDetailOnUi(ItemDetailDTO detailDTO){
        titleTxt.setText(detailDTO.getTitle());
        titleTxt.getPaint().setFakeBoldText(true);
        priceTxt.setText(detailDTO.getPriceStr());
        depositTxt.setText("押金"+detailDTO.getDeposit()+"元");
        machineTxt.setText(detailDTO.getMachineName());
        commentTxt.setText(String.valueOf(detailDTO.getComment()));
        descriptionTxt.setText(detailDTO.getDescription());
        //图片
        String[] urlArr = new String[detailDTO.getImgList().size()];
        for(int i=0;i<urlArr.length;i++){
            urlArr[i] = detailDTO.getImgList().get(i);
        }
        DetailImgListAdapter imgListAdapter = new DetailImgListAdapter(this, urlArr);
        imgListView.setAdapter(imgListAdapter);


        Long userId = detailDTO.getUserId();
        LoginUserVO loginUserVO = BizUtil.getLoginUser(this);
        //本人
        if(userId.equals(loginUserVO.getUserId())){
            renterOptLayout.setVisibility(View.GONE);
            ownerOptLayout.setVisibility(View.VISIBLE);
        }else{  //
            renterOptLayout.setVisibility(View.VISIBLE);
            ownerOptLayout.setVisibility(View.GONE);
        }
    }
}
