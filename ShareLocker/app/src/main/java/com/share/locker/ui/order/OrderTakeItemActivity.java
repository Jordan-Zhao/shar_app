package com.share.locker.ui.order;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.ImageUtil;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.OrderTakeItemDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_order_take_item)
public class OrderTakeItemActivity extends BaseActivity {
    private final String URL_GET_TAKE_ITEM_DATA = Constants.URL_BASE + "order/getTakeItemData.json";
    private static final String URL_OPEN_LOCKER_FOR_TAKE = Constants.URL_BASE + "order/openLockerForTake.json";
    private static final String URL_CLOSE_LOCKER_AFTER_TAKE = Constants.URL_BASE + "order/closeLockerAfterTake.json";

    private View view;
    @ViewInject(R.id.order_take_item_title_txt)
    private TextView titleTxt;

    @ViewInject(R.id.order_take_item_machine_name_txt)
    private TextView machineNameTxt;


    @ViewInject(R.id.order_take_item_qrcode_expire_time_txt)
    private TextView qrExpireTxt;

    @ViewInject(R.id.order_take_item_qr_img)
    private ImageView qrImg;

    @ViewInject(R.id.order_take_item_qr_txt)
    private TextView qrTxt;

    private OrderTakeItemDTO orderTakeItemDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil 渲染fragment
        x.view().inject(this);

        Intent intent = getIntent();
        Long orderId = intent.getLongExtra("orderId",-1);

        loadData(orderId);
    }

    private void loadData(Long orderId){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId", String.valueOf(orderId));

        LockerHttpUtil.postJson(URL_GET_TAKE_ITEM_DATA, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                orderTakeItemDTO = (OrderTakeItemDTO) JsonUtil.json2Object(successData, OrderTakeItemDTO.class);

                                showRentSuccessOnUi();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog(failData);
                    }
                });
    }

    private void showRentSuccessOnUi() {
        if (orderTakeItemDTO.getRemainTime() < 1) {
            titleTxt.setText(orderTakeItemDTO.getItemTitle());
            titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            machineNameTxt.setText(orderTakeItemDTO.getMachineName());
            qrExpireTxt.setText("取件验证码已过期");    //TODO 取件验证码是否需要过期时间？
        } else {
            titleTxt.setText(orderTakeItemDTO.getItemTitle());
            titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

            machineNameTxt.setText(orderTakeItemDTO.getMachineName());

            qrExpireTxt.setText("【"+orderTakeItemDTO.getRemainTime() + "分钟后过期】");

            //生成二维码并显示
            qrImg.setImageDrawable(new BitmapDrawable(ImageUtil.encodeAsBitmap(orderTakeItemDTO.getQrcode(), 250, 250)));
            qrTxt.setText(orderTakeItemDTO.getQrcode());
        }
    }

    @Event(value = R.id.mock_scan_take_qrcode_btn, type = View.OnClickListener.class)
    private void onClickMockScanBtn(View view) {
        //请求服务端打开柜门
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(orderTakeItemDTO.getLockerId()));
        paramMap.put("qrcode", orderTakeItemDTO.getQrcode());
        LockerHttpUtil.postJson(URL_OPEN_LOCKER_FOR_TAKE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(successData)) {
                                    GlobalManager.dialogManager.showTipDialog("Mock：柜门已打开，请取走宝贝，并关上柜门");
                                } else {
                                    GlobalManager.dialogManager.showTipDialog("Mock：信息不匹配，我不能开门");
                                }
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showTipDialog(failData);
                    }
                });
    }

    @Event(value = R.id.mock_take_item_btn, type = View.OnClickListener.class)
    private void onClickMockTakeBtn(View view) {
        //取走宝贝，并关闭柜门后，请求服务端,告知已经取走宝贝
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(orderTakeItemDTO.getLockerId()));
        LockerHttpUtil.postJson(URL_CLOSE_LOCKER_AFTER_TAKE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(successData)) {
                                    GlobalManager.dialogManager.showTipDialog("Mock：取件成功");
                                    //TODO : App页面如何显示？通过service感知？
                                }
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showTipDialog(failData);
                    }
                });
    }
}

