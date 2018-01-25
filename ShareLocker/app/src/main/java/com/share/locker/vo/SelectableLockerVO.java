package com.share.locker.vo;

import android.content.Intent;

/**
 * 可选择的locker
 * Created by Jordan on 22/01/2018.
 */

public class SelectableLockerVO {
    private Integer lockerId;
    private String machineName;

    public Integer getLockerId() {
        return lockerId;
    }

    public void setLockerId(Integer lockerId) {
        this.lockerId = lockerId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
