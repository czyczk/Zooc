package com.zzzz.po;

import java.util.Date;

/**
 * This class describes a coupon record item.
 * After a user uses a coupon, a CouponRecord is created so that the user won't be able to use it again.
 */
public class CouponRecord {
    // Coupon history ID
    private long couponHistoryId;
    // The ID of the user who used the coupon
    private long userId;
    // The ID of the coupon that has been used
    private long couponId;
    // The time the coupon was used
    private Date time;

    public long getCouponHistoryId() {
        return couponHistoryId;
    }

    public void setCouponHistoryId(long couponHistoryId) {
        this.couponHistoryId = couponHistoryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
