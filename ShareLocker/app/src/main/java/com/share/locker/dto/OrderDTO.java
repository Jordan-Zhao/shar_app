package com.share.locker.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * server 与app的传输对象。 订单DTO
 * 
 * @author Jordan
 *
 */
public class OrderDTO implements Serializable{
	private Long orderId;
	private Long itemId;
	private Float deposit;
	private String title;
	private Date createTime;
	private String status;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
