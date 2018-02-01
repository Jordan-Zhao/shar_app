package com.share.locker.ui.mine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.common.DateUtil;
import com.share.locker.dto.ItemDTO;
import com.share.locker.dto.OrderDTO;

import java.util.List;

/**
 * 我租用的宝贝 list view adapter
 * Created by Jordan on 18/01/2018.
 */

public class MineRentItemRecyclerAdapter extends RecyclerView.Adapter<MineRentItemRecyclerAdapter.ItemViewHolder>{
    public static final String CLICK_CODE_ITEM_VIEW = "CLICK_CODE_ITEM_VIEW";   //点击整个itemView
    public static final String CLICK_CODE_RETURN_BTN = "CLICK_CODE_RETURN_BTN";   //点击还件按钮

    private List<OrderDTO> itemList;
    private MineRentItemListActivity activity;
    private AdapterView.OnItemClickListener itemClickListener;

    public MineRentItemRecyclerAdapter(MineRentItemListActivity activity, List<OrderDTO> itemList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_rent_list_item, parent, false);
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
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.getView().setTag(position);  //使用position作为tag，点击时，可以取到position

        OrderDTO orderDTO = itemList.get(position);

        Glide.with(activity).load(orderDTO.getItemSmallImgUrl()).into(holder.getImgView());
        holder.getTitleTxt().setText(orderDTO.getTitle());
        holder.getTitleTxt().getPaint().setFakeBoldText(true);
        holder.getDepositTxt().setText("押金:"+BizUtil.getMoneyStr(orderDTO.getDeposit())+"元");
        holder.getPriceTxt().setText("价格:"+orderDTO.getPriceStr());
        holder.getStatusTxt().setText(BizUtil.getOrderStatusByCode(orderDTO.getStatus()));
        holder.getCreateTimeTxt().setText("租用开始时间:"+ DateUtil.formatDate(orderDTO.getCreateTime()));

        holder.getReturnBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onClickRecyclerItemView(CLICK_CODE_RETURN_BTN,itemList.get(position));
            }
        });

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
        private TextView statusTxt;
        private TextView createTimeTxt;
        private Button returnBtn;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            imgView = view.findViewById(R.id.mine_rent_item_img_view);
            titleTxt = view.findViewById(R.id.mine_rent_item_title);
            depositTxt = view.findViewById(R.id.mine_rent_item_deposit);
            priceTxt = view.findViewById(R.id.mine_rent_item_price);
            statusTxt = view.findViewById(R.id.mine_rent_item_status);
            createTimeTxt = view.findViewById(R.id.mine_rent_item_create_time);
            returnBtn = view.findViewById(R.id.mine_rent_item_return_btn);
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


        public TextView getStatusTxt() {
            return statusTxt;
        }

        public void setStatusTxt(TextView statusTxt) {
            this.statusTxt = statusTxt;
        }

        public TextView getCreateTimeTxt() {
            return createTimeTxt;
        }

        public void setCreateTimeTxt(TextView createTimeTxt) {
            this.createTimeTxt = createTimeTxt;
        }

        public Button getReturnBtn() {
            return returnBtn;
        }

        public void setReturnBtn(Button returnBtn) {
            this.returnBtn = returnBtn;
        }
    }
}
