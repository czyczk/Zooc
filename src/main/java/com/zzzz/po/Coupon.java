package com.zzzz.po;

import java.math.BigDecimal;
import java.util.Date;

public class Coupon {
    // Coupon ID
    private long couponId;
    // The ID of the enterprise to which the coupon belongs
    private long enterpriseId;
    // The amount of value the coupon can offset
    private BigDecimal value;
    // The coupon is available only when the order price is over the threshold
    private BigDecimal threshold;
    // The time the coupon is announced
    private Date time;
    // The status of the coupon
    private CouponStatusEnum status;

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public CouponStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CouponStatusEnum status) {
        this.status = status;
    }
}
