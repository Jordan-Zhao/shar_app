package com.share.locker.ui.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;

import java.io.Serializable;

/**
 * Created by Jordan on 22/01/2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected Class toActivityClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalManager.currentActivity = this;

        //去向 activity
        Serializable obj = getIntent().getSerializableExtra(Constants.INTENT_KEY_JUMP_TO);
        if(obj != null){
            toActivityClass = (Class)obj;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalManager.currentActivity = this;
    }

}
