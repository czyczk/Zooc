package com.zzzz.repo;

import com.zzzz.po.Coupon;

public interface CouponRepo {
    void save(Coupon coupon);
    void update(Coupon coupon);
    void delete(long couponId);
    Coupon getById(long couponId);
    boolean isCached(long couponId);
}
