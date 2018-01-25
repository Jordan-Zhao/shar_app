package com.share.locker.dto;

public class HotItemDTO {
	 private Long itemId;
     private String imgUrl;
     private String title;
     private Float deposit;
     private String priceTxt;
     private Integer commentCount;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
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
	public Float getDeposit() {
		return deposit;
	}
	public void setDeposit(Float deposit) {
		this.deposit = deposit;
	}
	public String getPriceTxt() {
		return priceTxt;
	}
	public void setPriceTxt(String priceTxt) {
		this.priceTxt = priceTxt;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

}
