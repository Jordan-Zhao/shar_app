package com.share.locker.ui.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.item.ItemDetailActivity;
import com.share.locker.ui.order.OrderPayDepositActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员操作页面
 */
@ContentView(R.layout.activity_admin_main)
public class AdminMainActivity extends AppCompatActivity {
    private static final String URL_GENERATE_OPT_DATA = Constants.URL_BASE + "admin/generateOptData.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xutil 渲染fragment
        x.view().inject(this);

    }

    //生成运营数据
    @Event(value = R.id.admin_generate_opt_data_layout, type = View.OnClickListener.class)
    private void onClickRentBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        //服务端请求，租用
        LockerHttpUtil.postJson(URL_GENERATE_OPT_DATA, paramMap,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        GlobalManager.dialogManager.showTipDialog(successData);
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialogInUiThread(failData);
                    }
                });
    }
}
