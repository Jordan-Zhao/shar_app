package com.share.locker.ui.item;

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
import com.share.locker.dto.ItemPutLockerDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_item_put_locker)
public class ItemPutLockerActivity extends BaseActivity {
    private static final String URL_GET_PUT_LOCKER_DATA = Constants.URL_BASE+"item/getPutLockerData.json";
    private static final String URL_OPEN_LOCKER_FOR_PUT = Constants.URL_BASE+"item/openLockerForPut.json";
    private static final String URL_CLOSE_LOCKER_AFTER_PUT = Constants.URL_BASE+"item/closeLockerAfterPut.json";

    private View view;
    @ViewInject(R.id.publish_success_title_txt)
    private TextView titleTxt;

    @ViewInject(R.id.publish_success_qr_img)
    private ImageView qrImg;

    @ViewInject(R.id.publish_success_qr_txt)
    private TextView qrTxt;

    @ViewInject(R.id.publish_success_tip_txt)
    private TextView tipTxt;

    private Long itemId;
    private ItemPutLockerDTO putLockerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil 渲染fragment
        x.view().inject(this);

        Intent intent = getIntent();
        itemId = intent.getLongExtra("itemId",-1);

        loadData(itemId);
    }

    private void loadData(Long itemId){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("itemId",String.valueOf(itemId));
        LockerHttpUtil.postJson(URL_GET_PUT_LOCKER_DATA, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                putLockerDTO = (ItemPutLockerDTO)JsonUtil.json2Object(successData,ItemPutLockerDTO.class);
                                showPublishSuccessOnUi();
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showTipDialog(failData);
                    }
                });
    }

    private void showPublishSuccessOnUi(){
        if(putLockerDTO.getRemainTime() < 1){
            tipTxt.setText("验证码已过期，请重新发布宝贝");
        }else {
            titleTxt.setText(putLockerDTO.getItemTitle());
            titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

            tipTxt.setText("请尽快去共享柜[" + putLockerDTO.getMachineName()
                    + "]，使用以下二维码存件(" +
                    "还剩余" + putLockerDTO.getRemainTime() + "分钟)：");

            //生成二维码并显示
            qrImg.setImageDrawable(new BitmapDrawable(ImageUtil.encodeAsBitmap(putLockerDTO.getQrcode(), 300, 300)));
            qrTxt.setText(putLockerDTO.getQrcode());
        }
    }

    @Event(value = R.id.mock_scan_qrcode_btn,type = View.OnClickListener.class)
    private void onClickMockScanBtn(View view){
        //请求服务端打开柜门
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("lockerId",String.valueOf(putLockerDTO.getLockerId()));
        paramMap.put("qrcode",putLockerDTO.getQrcode());
        LockerHttpUtil.postJson(URL_OPEN_LOCKER_FOR_PUT, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if("true".equals(successData)){
                                    GlobalManager.dialogManager.showTipDialog("Mock：柜门已打开，请放入宝贝");
                                }else{
                                    GlobalManager.dialogManager.showTipDialog("Mock：信息不匹配，我不能开门");
                                }
                                //TODO app页面如何感知？
                            }
                        });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showTipDialog(failData);
                    }
                });
    }

    @Event(value = R.id.mock_put_item_btn,type = View.OnClickListener.class)
    private void onClickMockPutBtn(View view){
        //放入宝贝，并关闭柜门后，请求服务端,告知已经放入宝贝
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("lockerId",String.valueOf(putLockerDTO.getLockerId()));
        LockerHttpUtil.postJson(URL_CLOSE_LOCKER_AFTER_PUT, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if("true".equals(successData)){
                                    GlobalManager.dialogManager.showTipDialog("Mock：入柜成功，宝贝已上架");
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
