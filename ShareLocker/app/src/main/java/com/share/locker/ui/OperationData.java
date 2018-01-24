package com.share.locker.ui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页使用的数据对象
 * Created by Jordan on 19/01/2018.
 */

public class OperationData {
    private List<BannerData> bannerDataList;

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

    private List<OperationData.HotItemData> hotItemDataList;

    /**
     * 根据运营配置数据，创建对象
     * @param operationDataJson
     */
    public OperationData(String operationDataJson) {
        try {
            JSONObject obj = new JSONObject(operationDataJson);

            //banner
            bannerDataList = new ArrayList<>();
            JSONArray bannerArr = new JSONArray(obj.getString("banner"));
            for(int j=0;j<bannerArr.length();j++){
                BannerData bannerData = new BannerData();
                JSONObject bannerJsonObj = bannerArr.getJSONObject(j);
                bannerData.setImgUrl(bannerJsonObj.getString("img_url")); //banner图片url
                bannerData.setItemId(bannerJsonObj.getInt("link_item_id"));  //链接的宝贝id
                bannerDataList.add(bannerData);
            }

            //运营位
            JSONObject centerJsonObj = new JSONObject(obj.getString("center"));
            //left
            JSONObject leftJsonObj = new JSONObject(centerJsonObj.getString("left"));
            leftImgUrl = leftJsonObj.getString("img_url");
            leftTxt = leftJsonObj.getString("text");
            leftItemId = leftJsonObj.getLong("link_item_id");
            //right1
            JSONObject rightJsonObj1 = new JSONObject(centerJsonObj.getString("right1"));
            rightImgUrl1 = rightJsonObj1.getString("img_url");
            rightTitle1 = rightJsonObj1.getString("title");
            rightTxt1 = rightJsonObj1.getString("text");
            rightItemId1 = rightJsonObj1.getLong("link_item_id");
            //right2
            JSONObject rightJsonObj2 = new JSONObject(centerJsonObj.getString("right2"));
            rightImgUrl2 = rightJsonObj2.getString("img_url");
            rightTitle2 = rightJsonObj2.getString("title");
            rightTxt2 = rightJsonObj2.getString("text");
            rightItemId2 = rightJsonObj2.getLong("link_item_id");

            //Hot items
            hotItemDataList = new ArrayList<>();
            JSONArray hotItemArr = new JSONArray(obj.getString("hot_items"));
            for(int i=0;i<hotItemArr.length();i++){
                HotItemData hotItemData = new HotItemData();
                JSONObject jsonObject = hotItemArr.getJSONObject(i);
                hotItemData.setItemId(jsonObject.getInt("itemId"));
                hotItemData.setPriceTxt(jsonObject.getString("priceStr"));
                hotItemData.setDeposit(jsonObject.getInt("deposit"));
                hotItemData.setCommentCount(jsonObject.getInt("comment"));
                hotItemData.setTitle(jsonObject.getString("title"));
                hotItemData.setImgUrl(jsonObject.getString("itemImgUrl"));
                hotItemDataList.add(hotItemData);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<BannerData> getBannerDataList() {
        return bannerDataList;
    }

    public void setBannerDataList(List<BannerData> bannerDataList) {
        this.bannerDataList = bannerDataList;
    }

    public List<HotItemData> getHotItemDataList() {
        return hotItemDataList;
    }

    public void setHotItemDataList(List<HotItemData> hotItemDataList) {
        this.hotItemDataList = hotItemDataList;
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

    public Long getRightItemId1() {
        return rightItemId1;
    }

    public Long getRightItemId2() {
        return rightItemId2;
    }

    public void setLeftItemId(Long leftItemId) {
        this.leftItemId = leftItemId;
    }

    public void setRightItemId1(Long rightItemId1) {
        this.rightItemId1 = rightItemId1;
    }

    public void setRightItemId2(Long rightItemId2) {
        this.rightItemId2 = rightItemId2;
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


class BannerData {
    private String imgUrl;
    private int itemId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}

class HotItemData {
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

}
