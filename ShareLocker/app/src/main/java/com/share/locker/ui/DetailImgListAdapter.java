package com.share.locker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.share.locker.R;

/**
 * 宝贝详情，图片列表adapter
 * Created by Jordan on 21/01/2018.
 */

public class DetailImgListAdapter extends ArrayAdapter<String> {
    private Context context;

    public DetailImgListAdapter(Context context, String[] imgUrlList) {
        super(context, R.layout.detail_img_list_item, imgUrlList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        DetailImgItemViewHolder itemViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.detail_img_list_item,parent,false);
            itemViewHolder = new DetailImgItemViewHolder();
            itemViewHolder.detailImgView = (ImageView) view.findViewById(R.id.detail_item_img_view);
            view.setTag(itemViewHolder);
        } else {
            view = convertView;
            itemViewHolder = (DetailImgItemViewHolder) view.getTag();
        }

        //渲染缩略图
        Glide.with(context).load(getItem(position)).into(itemViewHolder.detailImgView);
        return view;
    }

    /** View holder for the views we need access to */
    private static class DetailImgItemViewHolder {
        public ImageView detailImgView;
    }
}
