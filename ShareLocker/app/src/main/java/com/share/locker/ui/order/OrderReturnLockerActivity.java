package com.share.locker.ui.order;

import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.ImageUtil;
import com.share.locker.common.JsonUtil;
import com.share.locker.common.MockUtil;
import com.share.locker.dto.OrderReturnLockerDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_order_return_locker)
public class OrderReturnLockerActivity extends BaseActivity {
    private final String URL_GET_RETURN_LOCKER_DATA = Constants.URL_BASE + "order/getReturnLockerData.json";
    private final String URL_OPEN_LOCKER_FOR_RETURN = Constants.URL_BASE + "order/openLockerForReturn.json";
    private final String URL_CLOSE_LOCKER_AFTER_RETURN = Constants.URL_BASE + "order/closeLockerAfterReturn.json";
    private final String URL_CHECK_QUALITY = Constants.URL_BASE + "order/checkQuality.json";

    @ViewInject(R.id.item_return_locker_title_txt)
    private TextView titleTxt;
    @ViewInject(R.id.item_return_locker_machine_name_txt)
    private TextView machineNameTxt;
    @ViewInject(R.id.item_return_locker_qrcode_expire_time_txt)
    private  TextView qrExpireTimeTxt;
    @ViewInject(R.id.item_return_locker_qr_img)
    private ImageView qrImg;
    @ViewInject(R.id.item_return_locker_qr_txt)
    private TextView qrTxt;

    private Long orderId;
    private OrderReturnLockerDTO returnLockerDTO;
    private Long returnLockerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xutil 渲染fragment
        x.view().inject(this);

        orderId = getIntent().getLongExtra("orderId", -1);

        loadReturnLockerData(orderId);

    }

    /**
     * 加载页面显示数据
     *
     * @param orderId
     */
    private void loadReturnLockerData(Long orderId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId", String.valueOf(orderId));

        LockerHttpUtil.postJson(URL_GET_RETURN_LOCKER_DATA, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                returnLockerDTO = (OrderReturnLockerDTO) JsonUtil.json2Object(successData, OrderReturnLockerDTO.class);
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

    private void showDataOnUi() {
        if (returnLockerDTO.getRemainTime() < 1) {
            titleTxt.setText(returnLockerDTO.getItemTitle());
            titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            machineNameTxt.setText(returnLockerDTO.getMachineName());
            qrExpireTimeTxt.setText("换件验证码已过期，请支付逾期租金");
        } else {
            titleTxt.setText(returnLockerDTO.getItemTitle());
            titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

            machineNameTxt.setText(returnLockerDTO.getMachineName());

            qrExpireTimeTxt.setText("【"+returnLockerDTO.getRemainTime() + "分钟后过期】");

            //生成二维码并显示
            qrImg.setImageDrawable(new BitmapDrawable(ImageUtil.encodeAsBitmap(returnLockerDTO.getQrcode(), 300, 300)));
            qrTxt.setText(returnLockerDTO.getQrcode());
        }
    }

    /**
     * 模拟：机柜扫描换件二维码时，会请求服务端，服务端判断是否可以打开柜门
     *
     * @param view
     */
    @Event(value = R.id.mock_scan_return_qrcode_btn, type = View.OnClickListener.class)
    private void onClickMockScanReturnBtn(View view) {
        //请求服务端打开柜门
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("machineId", "1");//TODO 每个机柜有固定id
        paramMap.put("qrcode", returnLockerDTO.getQrcode());
        LockerHttpUtil.postJson(URL_OPEN_LOCKER_FOR_RETURN, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                returnLockerId = Long.parseLong(successData);    //服务端返回应该打开哪个lokcerId
                                if (MockUtil.isContainLocker(returnLockerId)) {
                                    MockUtil.openLocker(returnLockerId);
                                    GlobalManager.dialogManager.showTipDialog("Mock：柜门已打开，请归还宝贝");
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

    /**
     * 模拟：放入宝贝后，关闭柜门。机柜告知服务器，宝贝已还。
     *
     * @param view
     */
    @Event(value = R.id.mock_return_item_btn, type = View.OnClickListener.class)
    private void onClickMockReturnBtn(View view) {
        //放入宝贝，并关闭柜门后，请求服务端,告知已经放入宝贝
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(returnLockerId));
        LockerHttpUtil.postJson(URL_CLOSE_LOCKER_AFTER_RETURN, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(successData)) {
                                    GlobalManager.dialogManager.showTipDialog("Mock：归还成功，检查无误后将自动退还押金");
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

    /**
     * 模拟：管理员检查宝贝，无质量问题后放入柜门，重新上架。
     *
     * @param view
     */
    @Event(value = R.id.mock_check_item_btn, type = View.OnClickListener.class)
    private void onClickMockCheckBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(returnLockerId));
        LockerHttpUtil.postJson(URL_CHECK_QUALITY, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(successData)) {
                                    GlobalManager.dialogManager.showTipDialog("Mock：检查完成，没有损坏，宝贝已重新上架");
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
