package com.share.locker.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.ui.PublishPhotoListAdapter;
import com.share.locker.ui.PublishPhotoListItemData;

import java.util.List;
import java.util.Map;

/**
 * 下拉框adapter
 * Created by Jordan on 21/01/2018.
 */

public class SelectorAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> itemDataList;
    private LayoutInflater layoutInflater;

    public SelectorAdapter(Context pContext, List<Map<String,String>> itemDataList) {
        this.context = pContext;
        this.itemDataList = itemDataList;
    }

    @Override
    public int getCount() {
        return itemDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null){
            layoutInflater=LayoutInflater.from(context);
        }
        if(convertView == null){
            convertView=layoutInflater.inflate(android.R.layout.simple_spinner_item, null);
        }
        TextView textView = (TextView) convertView;
        textView.setText(itemDataList.get(position).get("value"));
        return convertView;
    }
}
