package com.zzzz.service;

import com.zzzz.po.Coupon;
import com.zzzz.vo.ListResult;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CouponService {
    /**
     * Insert a new coupon and return the ID of the new coupon.
     * Redis:
     *   Cache the coupon.
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param value Value
     * @param threshold Threshold
     * @param time The time the controller receives the request
     * @return New coupon ID
     * @throws CouponServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String enterpriseId, String value, String threshold, Date time) throws CouponServiceException, SQLException;

    /**
     * Disable a coupon.
     * Redis:
     *   Delete the coupon from cache.
     * @param couponId Coupon ID
     * @throws CouponServiceException An exception is thrown if the deletion is not successful.
     */
    void disable(String couponId) throws CouponServiceException, SQLException;

    /**
     * Update a coupon. The value and the threshold is open for modification.
     * Do not pass in fields that meant to be left unchanged.
     * Redis:
     *   Update the coupon.
     * @param targetCouponId Target coupon ID
     * @param value New value
     * @param threshold New threshold
     * @throws CouponServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetCouponId, String value, String threshold) throws CouponServiceException, SQLException;

    /**
     * Get a coupon by its ID.
     * Redis:
     *   Save the coupon if not cached.
     * @param couponId Coupon ID
     * @return Coupon
     * @throws CouponServiceException An exception is thrown if the query is not successful.
     */
    Coupon getById(String couponId) throws CouponServiceException, SQLException;

    /**
     * Get a list of coupons meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param couponId Coupon ID (Optional)
     * @param minValue Lower bound of value (Optional)
     * @param maxValue Upper bound of value (Optional)
     * @param minThreshold Lower bound of threshold (Optional)
     * @param maxThreshold Upper bound of threshold (Optional)
     * @param laterThan Coupon created later than (Optional)
     * @param earlierThan Coupon created earlier than (Optional)
     * @return A list of coupons meeting the requirements.
     * @throws CouponServiceException An exception is thrown if the query is not successful.
     */
    ListResult<Coupon> list(String usePagination, String targetPage, String pageSize,
                            String enterpriseId,
                            String couponId,
                            String minValue, String maxValue,
                            String minThreshold, String maxThreshold,
                            String laterThan, String earlierThan) throws CouponServiceException, SQLException;

    /**
     * List coupons that are created by the enterprise and available for the user (i.e. not used by the user).
     * @param enterpriseId Enterprise ID
     * @param userId User ID
     * @return Available coupons for the user
     * @throws CouponServiceException An exception is thrown if the query is not successful.
     */
    List<Coupon> listUserAvailable(String enterpriseId, String userId) throws CouponServiceException, SQLException;
}
