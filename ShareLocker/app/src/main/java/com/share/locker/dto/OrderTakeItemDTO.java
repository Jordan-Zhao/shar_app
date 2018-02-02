package com.share.locker.dto;

public class OrderTakeItemDTO {
	private Long orderId;
	private String itemTitle;
	private String qrcode;
	private Long lockerId;
	private String machineName;
	private int remainTime;	//验证码过期，剩余时间。单位分钟

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Long getLockerId() {
		return lockerId;
	}

	public void setLockerId(Long lockerId) {
		this.lockerId = lockerId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
}
