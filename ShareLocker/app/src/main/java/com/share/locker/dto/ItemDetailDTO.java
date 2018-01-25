package com.share.locker.dto;

import java.util.List;

/**
 * 商品详情
 * 
 * @author Jordan
 *
 */
public class ItemDetailDTO{
	private Long itemId;
	private Long userId;
	private String ownnerNick;
	private String title;
	private String priceStr;
	private Float deposit;
	private String machineName;
	private Integer comment;
	private String description;
	private List<String> imgList;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOwnnerNick() {
		return ownnerNick;
	}

	public void setOwnnerNick(String ownnerNick) {
		this.ownnerNick = ownnerNick;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public Float getDeposit() {
		return deposit;
	}

	public void setDeposit(Float deposit) {
		this.deposit = deposit;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}
}
