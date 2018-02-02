package com.share.locker.ui.component;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.share.locker.R;
/**
 * Created by jordan on 2018/2/2.
 */

public class ActivityTitleView extends LinearLayout {
    private String title;

    public ActivityTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ActivityTitleView, 0, 0);
        try {
            title = ta.getString(R.styleable.ActivityTitleView_title);
            initView();
        } finally {
            ta.recycle();
        }
    }

    private void initView(){
        ((TextView)findViewById(R.id.comp_activity_title_txt)).setText(title);
    }
}
