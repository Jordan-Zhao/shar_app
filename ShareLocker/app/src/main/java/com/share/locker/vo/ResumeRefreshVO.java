package com.share.locker.vo;

import android.os.Bundle;

import java.util.Map;

/**
 * Created by Jordan on 25/01/2018.
 */

public class ResumeRefreshVO {
    private boolean isNeedRefresh;
    private Map<String,Object> data;

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        isNeedRefresh = needRefresh;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
