package com.share.locker.dto;

/**
 * 可选择的locker
 * Created by Jordan on 22/01/2018.
 */

public class ValidLockerDTO {
    private Long lockerId;
    private String machineName;

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
}
