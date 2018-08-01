package com.zzzz.dao;

import com.zzzz.po.Coupon;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CouponDao {
    int insert(Coupon coupon) throws SQLException;
    Coupon getById(long couponId) throws SQLException;
    int update(Coupon coupon) throws SQLException;
    boolean checkExistenceById(long couponId) throws SQLException;

    /**
     * Count the number of enabled coupons meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param couponId Coupon ID (Optional)
     * @param minValue Lower bound of value (Optional)
     * @param maxValue Upper bound of value (Optional)
     * @param minThreshold Lower bound of threshold (Optional)
     * @param maxThreshold Upper bound of threshold (Optional)
     * @param laterThan Coupon created later than (Optional)
     * @param earlierThan Coupon created earlier than (Optional)
     * @return The number of coupons meeting the requirements
     */
    long countTotal(@Param("enterpriseId") long enterpriseId,
                    @Param("couponId") Long couponId,
                    @Param("minValue") BigDecimal minValue,
                    @Param("maxValue") BigDecimal maxValue,
                    @Param("minThreshold") BigDecimal minThreshold,
                    @Param("maxThreshold") BigDecimal maxThreshold,
                    @Param("laterThan") Date laterThan,
                    @Param("earlierThan") Date earlierThan) throws SQLException;

    /**
     * Get a list of enabled coupons meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param couponId Coupon ID (Optional)
     * @param minValue Lower bound of value (Optional)
     * @param maxValue Upper bound of value (Optional)
     * @param minThreshold Lower bound of threshold (Optional)
     * @param maxThreshold Upper bound of threshold (Optional)
     * @param laterThan Coupon created later than (Optional)
     * @param earlierThan Coupon created earlier than (Optional)
     * @return A list of coupons meeting the requirements
     */
    List<Coupon> list(@Param("usePagination") boolean usePagination,
                      @Param("starting") Long starting,
                      @Param("pageSize") Long pageSize,
                      @Param("enterpriseId") long enterpriseId,
                      @Param("couponId") Long couponId,
                      @Param("minValue") BigDecimal minValue,
                      @Param("maxValue") BigDecimal maxValue,
                      @Param("minThreshold") BigDecimal minThreshold,
                      @Param("maxThreshold") BigDecimal maxThreshold,
                      @Param("laterThan") Date laterThan,
                      @Param("earlierThan") Date earlierThan) throws SQLException;
}
