package com.share.locker.ui;

/**
 * Created by Jordan on 18/01/2018.
 */

public class HomeListItemData {
    private int itemId;
    private String imgUrl;
    private String title;
    private int deposit;
    private String priceTxt;
    private int commentCount;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getPriceTxt() {
        return priceTxt;
    }

    public void setPriceTxt(String priceTxt) {
        this.priceTxt = priceTxt;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
