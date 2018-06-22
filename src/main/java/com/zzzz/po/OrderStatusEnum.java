package com.zzzz.po;

public enum OrderStatusEnum {
    PENDING("待受理"),
    CANCELED("已取消"),
    AVAILABLE("可用"),
    REFUND_REQUESTED("已申请退款"),
    REFUNDED("已退款");

    private final String desc;

    OrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
