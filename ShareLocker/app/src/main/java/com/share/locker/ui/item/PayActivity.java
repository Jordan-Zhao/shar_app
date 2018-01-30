package com.share.locker.ui.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.share.locker.R;
import com.share.locker.dto.OrderDTO;
import com.share.locker.ui.component.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_pay)
public class PayActivity extends BaseActivity {
    private OrderDTO orderDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        orderDTO = (OrderDTO)getIntent().getSerializableExtra("orderDTO");

    }
}
