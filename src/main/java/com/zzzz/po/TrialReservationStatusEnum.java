package com.zzzz.po;

public enum TrialReservationStatusEnum {
    PENDING("待受理"),
    CANCELED("已取消"),
    AVAILABLE("可用"),
    USED("已用");

    private final String desc;

    TrialReservationStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
