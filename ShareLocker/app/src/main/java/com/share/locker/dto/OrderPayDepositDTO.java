package com.share.locker.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * server 与app的传输对象。 订单支付押金页面
 * 
 * @author Jordan
 *
 */
public class OrderPayDepositDTO implements Serializable{
	private Long orderId;
	private Float deposit;
	private String title;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Float getDeposit() {
		return deposit;
	}

	public void setDeposit(Float deposit) {
		this.deposit = deposit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
