package com.share.locker.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.MinePublishItemListActivity;
import com.share.locker.R;
import com.share.locker.common.dto.ItemDTO;

import java.util.List;

/**
 * 我发布的宝贝 list view adapter
 * Created by Jordan on 18/01/2018.
 */

public class MinePublishItemRecyclerAdapter extends RecyclerView.Adapter<MinePublishItemRecyclerAdapter.ItemViewHolder>{
    public static final String CLICK_CODE_ITEM_VIEW = "CLICK_CODE_ITEM_VIEW";   //点击整个itemView
    private List<ItemDTO> itemList;
    private MinePublishItemListActivity activity;
    private AdapterView.OnItemClickListener itemClickListener;

    public MinePublishItemRecyclerAdapter(MinePublishItemListActivity activity, List<ItemDTO> itemList) {
        this.itemList = itemList;
        this.activity = activity;
    }

    /**
     * 返回各viewType的viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_publish_list_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转给activity里的listenr
                Integer position = (Integer)view.getTag();
                activity.onClickRecyclerItemView(CLICK_CODE_ITEM_VIEW,itemList.get(position));
            }
        });
        return holder;
    }

    /**
     * 渲染各Item
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemDTO itemDTO = itemList.get(position);
        Glide.with(activity).load(itemDTO.getSmallImgUrl()).into(holder.getImgView());
        holder.getTitleTxt().setText(itemDTO.getTitle());
        holder.getTitleTxt().getPaint().setFakeBoldText(true);
        holder.getDepositTxt().setText(String.valueOf(itemDTO.getDeposit()));
        holder.getPriceTxt().setText(itemDTO.getPriceStr());
        holder.getCommentTxt().setText(String.valueOf(itemDTO.getComment()));
        holder.getView().setTag(position);  //使用position作为tag，点击时，可以取到position
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * view Holder，viewType不同，属性值也不同
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        private ImageView imgView;
        private TextView titleTxt;
        private TextView depositTxt;
        private TextView priceTxt;
        private TextView commentTxt;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            imgView = view.findViewById(R.id.mine_publish_item_img_view);
            titleTxt = view.findViewById(R.id.mine_publish_item_title);
            depositTxt = view.findViewById(R.id.mine_publish_item_deposit);
            priceTxt = view.findViewById(R.id.mine_publish_item_price);
            commentTxt = view.findViewById(R.id.mine_publish_item_comment);
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public ImageView getImgView() {
            return imgView;
        }

        public void setImgView(ImageView imgView) {
            this.imgView = imgView;
        }

        public TextView getTitleTxt() {
            return titleTxt;
        }

        public void setTitleTxt(TextView titleTxt) {
            this.titleTxt = titleTxt;
        }

        public TextView getDepositTxt() {
            return depositTxt;
        }

        public void setDepositTxt(TextView depositTxt) {
            this.depositTxt = depositTxt;
        }

        public TextView getPriceTxt() {
            return priceTxt;
        }

        public void setPriceTxt(TextView priceTxt) {
            this.priceTxt = priceTxt;
        }

        public TextView getCommentTxt() {
            return commentTxt;
        }

        public void setCommentTxt(TextView commentTxt) {
            this.commentTxt = commentTxt;
        }
    }
}
