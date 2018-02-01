package com.share.locker.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.PayFeeDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 还件前，支付租金页面
 */
@ContentView(R.layout.activity_order_pay_fee)
public class OrderPayFeeActivity extends BaseActivity {
    private final String URL_GET_PAY_FEE_DATA = Constants.URL_BASE+"order/getPayFeeData.json";
    private final String URL_PAY_FEE = Constants.URL_BASE+"trade/feePayFinished.json";

    @ViewInject(R.id.pay_fee_order_id_txt)
    private TextView orderIdTxt;
    @ViewInject(R.id.pay_fee_item_title_txt)
    private TextView itemTitleTxt;
    @ViewInject(R.id.pay_fee_price_txt)
    private TextView priceTxt;
    @ViewInject(R.id.pay_fee_time_txt)
    private TextView rentTimeTxt;
    @ViewInject(R.id.pay_fee_txt)
    private TextView rentFeeTxt;
    @ViewInject(R.id.pay_fee_way_group)
    private RadioGroup payWayGp;

    private PayFeeDTO payFeeDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil注入
        x.view().inject(this);

        loadPayFeeData();
    }

    /**
     * 去支付租金
     * @param view
     */
    @Event(value = R.id.pay_fee_btn,type = View.OnClickListener.class)
    private void onClickPayFeeBtn(View view){
        //TODO 应该调用支付宝 或者 微信 intent去支付，支付完成后，发送下面这个请求
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId",String.valueOf(payFeeDTO.getOrderId()));
        paramMap.put("fee",String.valueOf(payFeeDTO.getFee()));

        if(payWayGp.getCheckedRadioButtonId() == R.id.pay_fee_way_alipay){
            paramMap.put("payWay", Constants.PayWay.ALIPAY.getCode());
        }else if(payWayGp.getCheckedRadioButtonId() == R.id.pay_fee_way_wechat){
            paramMap.put("payWay",Constants.PayWay.WECHAT.getCode());
        }

        LockerHttpUtil.postJson(URL_PAY_FEE, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //租金支付成功，跳转到还件二维码页面
                                Intent intent = new Intent(OrderPayFeeActivity.this,OrderReturnLockerActivity.class);
                                intent.putExtra("orderId",payFeeDTO.getOrderId());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog(failData);
                    }
                });
    }

    private void loadPayFeeData(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId",String.valueOf(getIntent().getLongExtra("orderId",-1)));
        LockerHttpUtil.postJson(URL_GET_PAY_FEE_DATA, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                payFeeDTO = (PayFeeDTO)JsonUtil.json2Object(successData,PayFeeDTO.class);
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

    private void showDataOnUi(){
        orderIdTxt.setText(String.valueOf(payFeeDTO.getOrderId()));
        itemTitleTxt.setText(payFeeDTO.getTitle());
        priceTxt.setText(payFeeDTO.getPriceStr());
        rentTimeTxt.setText(payFeeDTO.getRentTime());
        rentFeeTxt.setText(BizUtil.getMoneyStr(payFeeDTO.getFee())+"元");
        payWayGp.check(R.id.pay_fee_way_alipay);
    }
}
