package com.share.locker.ui.item;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.vo.ResumeRefreshVO;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(R.layout.activity_publish_item_success)
public class PublishItemSuccessActivity extends AppCompatActivity {
    @ViewInject(R.id.frag_publish_jump_layout)
    private LinearLayout jumpLayout;
    @ViewInject(R.id.frag_publish_success)
    private LinearLayout publishSuccessLayout;
    @ViewInject(R.id.frag_publish_title_txt)
    private TextView titleTxt;

    private Long itemId;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xutil 渲染fragment
        x.view().inject(this);

        Intent intent = getIntent();
        Long itemId = intent.getLongExtra("itemId",-1);
        String itemTitle = intent.getStringExtra("itemTitle");
        showPublishSuccessOnUi(itemId,itemTitle);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    /**
     * @param itemId
     * @param itemTitle
     */
    private void showPublishSuccessOnUi(Long itemId,String itemTitle){
        this.itemId = itemId;
        titleTxt.setText(itemTitle);
        titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        jumpLayout.setVisibility(View.GONE);
        publishSuccessLayout.setVisibility(View.VISIBLE);
    }
}
