package com.share.locker.ui.item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.common.MockUtil;
import com.share.locker.dto.OrderDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_pay)
public class PayDepositActivity extends BaseActivity {
    private final String URL_DEPOSIT_PAY_FINISHED = Constants.URL_BASE+"trade/depositPayFinished.json";

    private OrderDTO orderDTO;

    @ViewInject(R.id.pay_order_id_txt)
    private TextView orderIdTxt;
    @ViewInject(R.id.pay_item_title_txt)
    private TextView itemTitleTxt;
    @ViewInject(R.id.pay_deposit_txt)
    private TextView depositTxt;
    @ViewInject(R.id.pay_way_group)
    private RadioGroup payWayRadioGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        orderDTO = (OrderDTO)getIntent().getSerializableExtra("orderDTO");
        orderIdTxt.setText(String.valueOf(orderDTO.getOrderId()));
        itemTitleTxt.setText(orderDTO.getTitle());
        depositTxt.setText(String.valueOf(orderDTO.getDeposit()));
        payWayRadioGp.check(R.id.pay_way_alipay);
    }

    @Event(value = R.id.pay_btn,type = View.OnClickListener.class)
    private void onClickPayBtn(View view){
        //TODO 调用支付宝或者微信intent，发起支付请求，这里直接告诉后台服务器支付成功
        if(payWayRadioGp.getCheckedRadioButtonId() != R.id.pay_way_alipay
                && payWayRadioGp.getCheckedRadioButtonId() != R.id.pay_way_wechat){
            GlobalManager.dialogManager.showErrorDialog("请选择支付方式");
            return;
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId",String.valueOf(orderDTO.getOrderId()));
        paramMap.put("fee",String.valueOf(orderDTO.getDeposit()));

        if(payWayRadioGp.getCheckedRadioButtonId() == R.id.pay_way_alipay){
            paramMap.put("payWay", Constants.PayWay.ALIPAY.getCode());
        }else if(payWayRadioGp.getCheckedRadioButtonId() == R.id.pay_way_wechat){
            paramMap.put("payWay",Constants.PayWay.WECHAT.getCode());
        }

        List<Uri> imgList = new ArrayList<>();
        LockerHttpUtil.postJson(URL_DEPOSIT_PAY_FINISHED, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent successIntent = new Intent(PayDepositActivity.this, PayDepositSuccessActivity.class);
                                OrderDTO orderDTO = (OrderDTO)JsonUtil.json2Object(successData,OrderDTO.class);
                                successIntent.putExtra("orderDTO",orderDTO);
                                startActivity(successIntent);
                                finish(); //销毁activity
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog(failData);
                    }
                });
    }
}
