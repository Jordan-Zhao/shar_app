package com.share.locker.dto;

import java.io.Serializable;

public class BannerDTO  implements Serializable {
	 private String imgUrl;
     private Long itemId;

     public String getImgUrl() {
         return imgUrl;
     }

     public void setImgUrl(String imgUrl) {
         this.imgUrl = imgUrl;
     }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
