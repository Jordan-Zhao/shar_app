package com.share.locker.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.locker.R;
import com.share.locker.dto.BannerDTO;
import com.share.locker.dto.HotItemDTO;
import com.share.locker.dto.OperationSettingDTO;
import com.share.locker.ui.item.ItemDetailActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页 banner + 运营位 + hot items一起放在recycleView中
 * Created by Jordan on 18/01/2018.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    public static final int VIEW_TYPE_HEADER = 0;  //说明是带有Header的
    public static final int VIEW_TYPE_NORMAL = 1;  //说明是不带有header和footer的

    private OperationSettingDTO operationSettingDTO;
    private Activity mainActivity;
    private View headView;

    public HomeRecyclerViewAdapter(Activity mainActivity, OperationSettingDTO operationSettingDTO) {
        this.mainActivity = mainActivity;
        this.operationSettingDTO = operationSettingDTO;
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (headView == null) {
            return VIEW_TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_NORMAL;
    }

    /**
     * 返回各viewType的viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            HomeViewHolder viewHolder = new HomeViewHolder(VIEW_TYPE_HEADER, headView);
            //点击后，跳转到详情页
            viewHolder.getBanner().setOnBannerListener(new OnBannerListener() {
                        //轮播图的监听方法
                        @Override
                        public void OnBannerClick(int position) {
                            Long itemId = operationSettingDTO.getBannerDTOList().get(position).getItemId();
                            jumpToItemDetailView(itemId);
                        }
                    });
            setItemClickListener(viewHolder.getOptLeftLayout(),operationSettingDTO.getLeftItemId());
            setItemClickListener(viewHolder.getOptRightLayout1(),operationSettingDTO.getRightItemId1());
            setItemClickListener(viewHolder.getOptRightLayout2(),operationSettingDTO.getRightItemId2());
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
            HomeViewHolder viewHolder = new HomeViewHolder(VIEW_TYPE_NORMAL, view);
            viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer)view.getTag();
                    Long itemId = operationSettingDTO.getHotItemDTOList().get(position - 1).getItemId();
                    jumpToItemDetailView(itemId);
                }
            });
            return viewHolder;
        }
    }

    private void  setItemClickListener(View view, final Long itemId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jumpToItemDetailView(itemId);
                    }
                });
            }
        });
    }

    /**
     * 渲染各Item
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            showBannerData(holder);
            showOperationData(holder);
        } else if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            showListItemData(holder, position);
        }
        holder.getView().setTag(position);  //使用position作为Tag
    }

    @Override
    public int getItemCount() {
        if (headView == null) {
            return operationSettingDTO.getHotItemDTOList().size();
        } else {
            return operationSettingDTO.getHotItemDTOList().size() + 1;
        }
    }

    private void showBannerData(HomeViewHolder headViewHolder) {
        Banner banner = headViewHolder.getBanner();
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(//自定义的图片加载器
                new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load((String) path).centerCrop().into(imageView);
                    }
                });
        //设置图片网址或地址的集合`
        List<String> imgUrlList = new ArrayList<>();
        for (BannerDTO bannerDTO : operationSettingDTO.getBannerDTOList()) {
            imgUrlList.add(bannerDTO.getImgUrl());
        }
        banner.setImages(imgUrlList);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(null);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    private void showOperationData(HomeViewHolder headViewHolder) {
        //left
        Glide.with(mainActivity).load(operationSettingDTO.getLeftImgUrl()).into(headViewHolder.getOptLeftImg());
        headViewHolder.getOptLeftTxt().setText(operationSettingDTO.getLeftTxt());

        //right1
        Glide.with(mainActivity).load(operationSettingDTO.getRightImgUrl1()).into(headViewHolder.getOptRightImg1());
        headViewHolder.getOptRightTitle1().setText(operationSettingDTO.getRightTitle1());
        headViewHolder.getOptRightTxt1().setText(operationSettingDTO.getRightTxt1());

        //right2
        Glide.with(mainActivity).load(operationSettingDTO.getRightImgUrl2()).into(headViewHolder.getOptRightImg2());
        headViewHolder.getOptRightTitle2().setText(operationSettingDTO.getRightTitle2());
        headViewHolder.getOptRightTxt2().setText(operationSettingDTO.getRightTxt2());
    }

    private void showListItemData(HomeViewHolder listItemViewHolder, int position) {
        HotItemDTO hotItemDTO = operationSettingDTO.getHotItemDTOList().get(position - 1);
        Glide.with(mainActivity).load(hotItemDTO.getImgUrl()).centerCrop().into(listItemViewHolder.getImgView());
        listItemViewHolder.getTitleTxt().setText(hotItemDTO.getTitle());
        listItemViewHolder.getTitleTxt().setText(hotItemDTO.getTitle());
        listItemViewHolder.getDepositTxt().setText(String.valueOf(hotItemDTO.getDeposit())+"元");
        listItemViewHolder.getPriceTxt().setText(hotItemDTO.getPriceTxt());
        listItemViewHolder.getCommentTxt().setText(String.valueOf(hotItemDTO.getCommentCount()));
    }

    public View getHeadView() {
        return headView;
    }


    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
    }

    private void jumpToItemDetailView(Long itemId){
        Intent intent = new Intent(mainActivity,ItemDetailActivity.class);
        intent.putExtra("itemId",itemId);
        mainActivity.startActivity(intent);
    }
    /**
     * view Holder，viewType不同，属性值也不同
     */
    static class HomeViewHolder extends RecyclerView.ViewHolder {
        View view;

        //banner
        private Banner banner;
        //center 运营位
        private LinearLayout optLeftLayout;
        private ImageView optLeftImg;
        private TextView optLeftTxt;

        private LinearLayout optRightLayout1;
        private ImageView optRightImg1;
        private TextView optRightTitle1;
        private TextView optRightTxt1;

        private LinearLayout optRightLayout2;
        private ImageView optRightImg2;
        private TextView optRightTitle2;
        private TextView optRightTxt2;
        //hot items
        private ImageView imgView;
        private TextView titleTxt;
        private TextView depositTxt;
        private TextView priceTxt;
        private TextView commentTxt;

        public HomeViewHolder(int viewType, View view) {
            super(view);
            this.view = view;
            if (viewType == VIEW_TYPE_HEADER) {
                banner = view.findViewById(R.id.banner_home);

                optLeftLayout = view.findViewById(R.id.opt_left);
                optLeftImg = view.findViewById(R.id.opt_left_img);
                optLeftTxt = view.findViewById(R.id.opt_left_txt);

                optRightLayout1 = view.findViewById(R.id.opt_right1);
                optRightImg1 = view.findViewById(R.id.opt_right_img1);
                optRightTitle1 = view.findViewById(R.id.opt_right_title1);
                optRightTxt1 = view.findViewById(R.id.opt_right_txt1);

                optRightLayout2 = view.findViewById(R.id.opt_right2);
                optRightImg2 = view.findViewById(R.id.opt_right_img2);
                optRightTitle2 = view.findViewById(R.id.opt_right_title2);
                optRightTxt2 = view.findViewById(R.id.opt_right_txt2);
            } else if (viewType == VIEW_TYPE_NORMAL) {
                imgView = view.findViewById(R.id.list_item_img_view);
                titleTxt = view.findViewById(R.id.list_item_title);
                depositTxt = view.findViewById(R.id.list_item_deposit);
                priceTxt = view.findViewById(R.id.list_item_price);
                commentTxt = view.findViewById(R.id.list_item_comment);
            }
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public Banner getBanner() {
            return banner;
        }

        public void setBanner(Banner banner) {
            this.banner = banner;
        }

        public LinearLayout getOptLeftLayout() {
            return optLeftLayout;
        }

        public void setOptLeftLayout(LinearLayout optLeftLayout) {
            this.optLeftLayout = optLeftLayout;
        }

        public ImageView getOptLeftImg() {
            return optLeftImg;
        }

        public void setOptLeftImg(ImageView optLeftImg) {
            this.optLeftImg = optLeftImg;
        }

        public TextView getOptLeftTxt() {
            return optLeftTxt;
        }

        public void setOptLeftTxt(TextView optLeftTxt) {
            this.optLeftTxt = optLeftTxt;
        }



        public ImageView getOptRightImg1() {
            return optRightImg1;
        }

        public void setOptRightImg1(ImageView optRightImg1) {
            this.optRightImg1 = optRightImg1;
        }

        public TextView getOptRightTitle1() {
            return optRightTitle1;
        }

        public void setOptRightTitle1(TextView optRightTitle1) {
            this.optRightTitle1 = optRightTitle1;
        }

        public TextView getOptRightTxt1() {
            return optRightTxt1;
        }

        public void setOptRightTxt1(TextView optRightTxt1) {
            this.optRightTxt1 = optRightTxt1;
        }

        public ImageView getOptRightImg2() {
            return optRightImg2;
        }

        public void setOptRightImg2(ImageView optRightImg2) {
            this.optRightImg2 = optRightImg2;
        }

        public LinearLayout getOptRightLayout1() {
            return optRightLayout1;
        }

        public void setOptRightLayout1(LinearLayout optRightLayout1) {
            this.optRightLayout1 = optRightLayout1;
        }

        public LinearLayout getOptRightLayout2() {
            return optRightLayout2;
        }

        public void setOptRightLayout2(LinearLayout optRightLayout2) {
            this.optRightLayout2 = optRightLayout2;
        }

        public TextView getOptRightTitle2() {
            return optRightTitle2;
        }

        public void setOptRightTitle2(TextView optRightTitle2) {
            this.optRightTitle2 = optRightTitle2;
        }

        public TextView getOptRightTxt2() {
            return optRightTxt2;
        }

        public void setOptRightTxt2(TextView optRightTxt2) {
            this.optRightTxt2 = optRightTxt2;
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
