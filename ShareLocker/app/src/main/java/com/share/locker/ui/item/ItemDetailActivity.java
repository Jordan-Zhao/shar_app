package com.share.locker.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.ItemDetailDTO;
import com.share.locker.dto.OrderDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.mine.LoginActivity;
import com.share.locker.ui.order.OrderPayDepositActivity;
import com.share.locker.vo.LoginUserVO;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_item_detail)
public class ItemDetailActivity extends BaseActivity {
    private static final String URL_GET_ITEM_DETAIL = Constants.URL_BASE + "item/getItemDetail.json";
    private static final String URL_RENT = Constants.URL_BASE + "order/rent.json";
    private static final String URL_ITEM_ON_LINE = Constants.URL_BASE + "item/onLineItem.json";
    private static final String URL_ITEM_OFF_LINE = Constants.URL_BASE + "item/offLineItem.json";
    private static final String URL_ITEM_DELETE = Constants.URL_BASE + "item/deleteItem.json";

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

    @ViewInject(R.id.detail_renter_opt_layout)
    private LinearLayout renterOptLayout;

    @ViewInject(R.id.detail_owner_opt_layout)
    private LinearLayout ownerOptLayout;

    @ViewInject(R.id.detail_img_list_layout)
    private LinearLayout imgListLayout;

    private Long itemId;
    private ItemDetailDTO detailDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil注入
        x.view().inject(this);

        itemId = getIntent().getLongExtra("itemId", 0L);
        loadItemDetail(itemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUiByUserRole();
    }

    //根据用户身份，显示页面
    private void showUiByUserRole() {
        if (detailDTO != null) {
            LoginUserVO loginUserVO = BizUtil.getLoginUser(this);
            if (loginUserVO == null || !loginUserVO.getUserId().equals(detailDTO.getUserId())) {
                //未登录，或者不是本人
                renterOptLayout.setVisibility(View.VISIBLE);
                ownerOptLayout.setVisibility(View.GONE);
            } else {
                //已登录，并且是本人
                renterOptLayout.setVisibility(View.GONE);
                ownerOptLayout.setVisibility(View.VISIBLE);
            }
        }
    }


    //“立即租用”按钮
    @Event(value = R.id.detail_rent_btn, type = View.OnClickListener.class)
    private void onClickRentBtn(View view) {
        if (needLogin()) {
            return;
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId", String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_RENT, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        //跳转到支付页面
                        Long orderId = Long.parseLong(successData);
                        Intent successIntent = new Intent(ItemDetailActivity.this, OrderPayDepositActivity.class);
                        successIntent.putExtra("orderId",orderId);
                        startActivity(successIntent);
                        finish(); //销毁activity
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                    }
                });
    }

    //“上架”按钮
    @Event(value = R.id.detail_on_line_btn, type = View.OnClickListener.class)
    private void onClickOnlineBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId", String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_ON_LINE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        GlobalManager.dialogManager.showTipDialogInUiThread(successData);
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                    }
                });
    }

    //“下架”按钮
    @Event(value = R.id.detail_off_line_btn, type = View.OnClickListener.class)
    private void onClickOfflineBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId", String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_OFF_LINE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        GlobalManager.dialogManager.showTipDialogInUiThread(successData);
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                    }
                });
    }

    //“删除”按钮
    @Event(value = R.id.detail_delete_btn, type = View.OnClickListener.class)
    private void onClickDeleteBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId", String.valueOf(itemId));
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_ITEM_DELETE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        GlobalManager.dialogManager.showTipDialogInUiThread(successData);
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                    }
                });
    }

    private boolean needLogin() {
        LoginUserVO loginUserVO = BizUtil.getLoginUser(this);
        if (loginUserVO == null) {
            Intent intent = new Intent(ItemDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private void loadItemDetail(Long itemId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("itemId", String.valueOf(itemId));
        LockerHttpUtil.postJson(URL_GET_ITEM_DETAIL, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                detailDTO = (ItemDetailDTO) JsonUtil.json2Object(successData, ItemDetailDTO.class);
                                showItemDetailOnUi(detailDTO);
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {

                    }
                });
    }

    private void showItemDetailOnUi(ItemDetailDTO detailDTO) {
        titleTxt.setText(detailDTO.getTitle());
        titleTxt.getPaint().setFakeBoldText(true);
        priceTxt.setText(detailDTO.getPriceStr());
        depositTxt.setText("押金" + detailDTO.getDeposit() + "元");
        machineTxt.setText(detailDTO.getMachineName());
        commentTxt.setText(String.valueOf(detailDTO.getComment()));
        descriptionTxt.setText(detailDTO.getDescription());
        //渲染图片
        for (String imgUrl : detailDTO.getImgList()) {
            ImageView imageView = new ImageView(this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);
            imageView.setLayoutParams(layoutParams);
            Glide.with(this).load(imgUrl).into(imageView);
            imgListLayout.addView(imageView);
        }
        showUiByUserRole();
    }
}
