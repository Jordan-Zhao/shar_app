package com.share.locker.ui.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.ui.item.PublishPhotoListItemData;

/**
 * 发布宝贝，选中图片后，显示图片缩略图list的adapter
 * Created by Jordan on 21/01/2018.
 */

public class PublishPhotoListAdapter extends ArrayAdapter<PublishPhotoListItemData> {
    private Context context;
    private LayoutInflater layoutInflater;

    public PublishPhotoListAdapter(Context context, PublishPhotoListItemData[] itemDataArr) {
        super(context, R.layout.publish_photo_list_item, itemDataArr);
        this.context = context;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PublishPhotoItemViewHolder itemViewHolder;
        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = layoutInflater.inflate(R.layout.publish_photo_list_item, parent, false);

            // Create and save off the holder in the tag, so we get quick access to inner fields
            // This must be done for performance reasons
            itemViewHolder = new PublishPhotoItemViewHolder();
            itemViewHolder.smallPhotoImg = (ImageView) convertView.findViewById(R.id.publish_photo_small);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (PublishPhotoItemViewHolder) convertView.getTag();
        }

        //渲染缩略图
        Glide.with(context).load(getItem(position).getImgUri()).fitCenter()
                .into(itemViewHolder.smallPhotoImg);


        return convertView;
    }

    /** View holder for the views we need access to */
    private static class PublishPhotoItemViewHolder {
        public ImageView smallPhotoImg;
    }
}
