package com.share.locker.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * server 与app的传输对象。 支付租金页面使用的DTO
 * 
 * @author Jordan
 *
 */
public class PayFeeDTO implements Serializable{
	private Long orderId;
	private String title;
	private String priceStr;
	private String rentTime;
	private Float fee;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public String getRentTime() {
		return rentTime;
	}

	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

}
