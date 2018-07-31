package com.zzzz.po;

public enum CouponStatusEnum {
    ENABLED("已启用"),
    DISABLED("已禁用");

    private final String desc;

    CouponStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
