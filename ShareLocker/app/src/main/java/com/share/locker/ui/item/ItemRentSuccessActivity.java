package com.share.locker.ui.item;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.ImageUtil;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.vo.ResumeRefreshVO;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_item_rent_success)
public class ItemRentSuccessActivity extends BaseActivity {
    private static final String URL_OPEN_LOCKER_FOR_TAKE = Constants.URL_BASE + "order/openLockerForTake.json";
    private static final String URL_CLOSE_LOCKER_AFTER_TAKE = Constants.URL_BASE + "order/closeLockerAfterTake.json";

    private View view;
    @ViewInject(R.id.order_success_title_txt)
    private TextView titleTxt;

    @ViewInject(R.id.order_success_qr_img)
    private ImageView qrImg;

    @ViewInject(R.id.order_success_qr_txt)
    private TextView qrTxt;

    @ViewInject(R.id.order_success_tip_txt)
    private TextView tipTxt;

    private Long itemId;
    private String qrcode;
    private Long lockerId;
    private String itemTitle;
    private String machineName;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil 渲染fragment
        x.view().inject(this);

        Intent intent = getIntent();
        itemId = intent.getLongExtra("itemId", -1);
        qrcode = intent.getStringExtra("qrcode");
        lockerId = intent.getLongExtra("lockerId", -1);
        itemTitle = intent.getStringExtra("itemTitle");
        machineName = intent.getStringExtra("machineName");
        Long orderId = intent.getLongExtra("orderId",-1);

        showRentSuccessOnUi();
    }

    private void showRentSuccessOnUi() {
        titleTxt.setText("您已成功租用["+itemTitle+"]，我们将为您锁定宝贝30分钟，请尽快去共享柜["+machineName+"]取件。取件二维码：");

        //生成二维码并显示
        qrImg.setImageDrawable(new BitmapDrawable(ImageUtil.encodeAsBitmap(qrcode, 300, 300)));

        qrTxt.setText(qrcode);
    }

    @Event(value = R.id.mock_scan_take_qrcode_btn, type = View.OnClickListener.class)
    private void onClickMockScanBtn(View view) {
        //请求服务端打开柜门
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(lockerId));
        paramMap.put("qrcode", qrcode);
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
        //放入宝贝，并关闭柜门后，请求服务端,告知已经取走宝贝
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lockerId", String.valueOf(lockerId));
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

