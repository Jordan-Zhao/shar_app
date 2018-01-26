package com.share.locker.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 首页使用的数据对象 Created by Jordan on 19/01/2018.
 */

public class OperationSettingDTO implements Serializable {
	private List<BannerDTO> bannerDTOList;

	private String leftImgUrl;
	private String leftTxt;
	private Long leftItemId;

	private String rightImgUrl1;
	private String rightTitle1;
	private String rightTxt1;
	private Long rightItemId1;

	private String rightImgUrl2;
	private String rightTitle2;
	private String rightTxt2;
	private Long rightItemId2;

	private List<HotItemDTO> hotItemDTOList;

	public List<BannerDTO> getBannerDTOList() {
		return bannerDTOList;
	}

	public void setBannerDTOList(List<BannerDTO> bannerDTOList) {
		this.bannerDTOList = bannerDTOList;
	}

	public String getLeftImgUrl() {
		return leftImgUrl;
	}

	public void setLeftImgUrl(String leftImgUrl) {
		this.leftImgUrl = leftImgUrl;
	}

	public String getLeftTxt() {
		return leftTxt;
	}

	public void setLeftTxt(String leftTxt) {
		this.leftTxt = leftTxt;
	}

	public Long getLeftItemId() {
		return leftItemId;
	}

	public void setLeftItemId(Long leftItemId) {
		this.leftItemId = leftItemId;
	}

	public String getRightImgUrl1() {
		return rightImgUrl1;
	}

	public void setRightImgUrl1(String rightImgUrl1) {
		this.rightImgUrl1 = rightImgUrl1;
	}

	public String getRightTitle1() {
		return rightTitle1;
	}

	public void setRightTitle1(String rightTitle1) {
		this.rightTitle1 = rightTitle1;
	}

	public String getRightTxt1() {
		return rightTxt1;
	}

	public void setRightTxt1(String rightTxt1) {
		this.rightTxt1 = rightTxt1;
	}

	public Long getRightItemId1() {
		return rightItemId1;
	}

	public void setRightItemId1(Long rightItemId1) {
		this.rightItemId1 = rightItemId1;
	}

	public String getRightImgUrl2() {
		return rightImgUrl2;
	}

	public void setRightImgUrl2(String rightImgUrl2) {
		this.rightImgUrl2 = rightImgUrl2;
	}

	public String getRightTitle2() {
		return rightTitle2;
	}

	public void setRightTitle2(String rightTitle2) {
		this.rightTitle2 = rightTitle2;
	}

	public String getRightTxt2() {
		return rightTxt2;
	}

	public void setRightTxt2(String rightTxt2) {
		this.rightTxt2 = rightTxt2;
	}

	public Long getRightItemId2() {
		return rightItemId2;
	}

	public void setRightItemId2(Long rightItemId2) {
		this.rightItemId2 = rightItemId2;
	}

	public List<HotItemDTO> getHotItemDTOList() {
		return hotItemDTOList;
	}

	public void setHotItemDTOList(List<HotItemDTO> hotItemDTOList) {
		this.hotItemDTOList = hotItemDTOList;
	}

}
