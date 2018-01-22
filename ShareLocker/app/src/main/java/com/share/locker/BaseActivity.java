package com.share.locker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.share.locker.common.GlobalManager;

/**
 * Created by Jordan on 22/01/2018.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalManager.currentContext = this;
    }
}
