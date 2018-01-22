package com.share.locker.common;

/**
 * Created by Jordan on 21/01/2018.
 */

public class SpinnerItemData {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 在页面显示
     * @return
     */
    @Override
    public String toString(){
        return value;
    }
}
