package com.zzzz.repo;

import com.zzzz.po.Coupon;

import java.util.List;

public interface CouponRepo {
    void save(Coupon coupon);
    void update(Coupon coupon);
    void delete(long couponId);
    Coupon getById(long couponId);
    boolean isCached(long couponId);

    /**
     * Save user available coupons.
     * Triggered when the coupons are fetched from the DB but not cached.
     * @param enterpriseId Enterprise ID
     * @param userId User ID
     * @param userAvailableCoupons User available coupons
     */
    void saveUserAvailableCoupons(long enterpriseId, long userId, List<Coupon> userAvailableCoupons);

    /**
     * Delete user available coupons.
     * Triggered when a new CouponRecord is inserted.
     * @param enterpriseId Enterprise ID
     * @param userId User ID
     */
    void deleteUserAvailableCoupons(long enterpriseId, long userId);

    /**
     * Delete user available coupons.
     * Triggered when a new coupon is added, disabled or updated.
     * @param enterpriseId Enterprise ID
     */
    void deleteUserAvailableCoupons(long enterpriseId);

    List<Coupon> getUserAvailableCoupons(long enterpriseId, long userId);
}
