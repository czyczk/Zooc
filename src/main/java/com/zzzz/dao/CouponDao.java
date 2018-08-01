package com.zzzz.dao;

import com.zzzz.po.Coupon;
import com.zzzz.vo.ListResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public interface CouponDao {
    int insert(Coupon coupon) throws SQLException;
    int getById(long couponId) throws SQLException;
    int update(Coupon coupon) throws SQLException;
    boolean checkExistenceById(long couponId) throws SQLException;

    /**
     * Count the number of coupons meeting the requirements.
     * @param couponId Coupon ID
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param minValue Lower bound of value
     * @param maxValue Upper bound of value
     * @param minThreshold Lower bound of threshold
     * @param maxThreshold Upper bound of threshold
     * @param laterThan Coupon created later than
     * @param earlierThan Coupon created earlier than
     * @return The number of coupons meeting the requirements
     */
    long countTotal(@Param("couponId") Long couponId,
                    @Param("enterpriseId") Long enterpriseId,
                    @Param("minValue") BigDecimal minValue,
                    @Param("maxValue") BigDecimal maxValue,
                    @Param("minThreshold") BigDecimal minThreshold,
                    @Param("maxThreshold") BigDecimal maxThreshold,
                    @Param("laterThan") Date laterThan,
                    @Param("earlierThan") Date earlierThan) throws SQLException;

    /**
     * Get a list of coupons meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param couponId Coupon ID
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param minValue Lower bound of value
     * @param maxValue Upper bound of value
     * @param minThreshold Lower bound of threshold
     * @param maxThreshold Upper bound of threshold
     * @param laterThan Coupon created later than
     * @param earlierThan Coupon created earlier than
     * @return A list of coupons meeting the requirements
     */
    ListResult<Coupon> list(@Param("usePagination") boolean usePagination,
                            @Param("starting") Integer starting,
                            @Param("pageSize") Integer pageSize,
                            @Param("couponId") Long couponId,
                            @Param("enterpriseId") Long enterpriseId,
                            @Param("minValue") BigDecimal minValue,
                            @Param("maxValue") BigDecimal maxValue,
                            @Param("minThreshold") BigDecimal minThreshold,
                            @Param("maxThreshold") BigDecimal maxThreshold,
                            @Param("laterThan") Date laterThan,
                            @Param("earlierThan") Date earlierThan) throws SQLException;
}
