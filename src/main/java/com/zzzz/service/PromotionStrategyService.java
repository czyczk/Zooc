package com.zzzz.service;

import com.zzzz.po.PromotionStrategy;

import java.sql.SQLException;

public interface PromotionStrategyService {
    /**
     * Update the promotion strategy of an enterprise. Do not pass in fields that meant to be left unchanged.
     * @param enterpriseId Enterprise ID
     * @param useCoupons New status of coupon strategy
     * @param usePoints New status of point strategy
     * @param pointsPerYuan New strategy of the number of points that can offset 1 yuan
     * @param checkinPoints New number of points a user can get after checking in
     * @throws PromotionStrategyServiceException An exception is thrown if the update is not successful.
     */
    void update(String enterpriseId, String useCoupons, String usePoints, String pointsPerYuan, String checkinPoints) throws PromotionStrategyServiceException, SQLException;

    /**
     * Get the promotion strategy of an enterprise.
     * @param enterpriseId Enterprise ID
     * @return The promotion strategy of an enterprise
     * @throws PromotionStrategyServiceException An exception is thrown if the query is not successful.
     */
    PromotionStrategy getByEnterpriseId(String enterpriseId) throws PromotionStrategyServiceException, SQLException;
}
