package com.share.locker.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.share.locker.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ruolan on 2015/11/29.
 */
@ContentView(R.layout.fragment_item_publish)
public class TabItemPublishFragment extends BaseFragment{
    private View view;

    @ViewInject(R.id.frag_publish_jump_layout)
    private LinearLayout jumpLayout;
    @ViewInject(R.id.frag_publish_success)
    private LinearLayout publishSuccessLayout;
    @ViewInject(R.id.frag_publish_title_txt)
    private TextView titleTxt;

    private Long itemId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        showBeforePublish();
        return view;
    }

    /**
     * main中点击发布宝贝后，到这个页面，调这个方法
     */
    private void showBeforePublish(){
        publishSuccessLayout.setVisibility(View.GONE);
        jumpLayout.setVisibility(View.VISIBLE);
    }
    /**
     * 宝贝发布成功后调用
     * @param itemId
     * @param itemTitle
     */
    public void showPublishSuccess(Long itemId,String itemTitle){
        this.itemId = itemId;
        titleTxt.setText(itemTitle);
        titleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        jumpLayout.setVisibility(View.GONE);
        publishSuccessLayout.setVisibility(View.VISIBLE);
    }
}
