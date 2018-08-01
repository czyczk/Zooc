package com.zzzz.service;

import com.zzzz.po.PromotionStrategy;

import java.sql.SQLException;

public interface PromotionStrategyService {
    /**
     * Update the promotion strategy of an enterprise.
     * @param enterpriseId Enterprise ID
     * @param useCoupons Use coupons or not
     * @param usePoints Use points or not
     * @param pointsPerYuan The number of points that can offset 1 yuan
     * @throws PromotionStrategyServiceException An exception is thrown if the update is not successful.
     */
    void update(String enterpriseId, String useCoupons, String usePoints, String pointsPerYuan) throws PromotionStrategyServiceException, SQLException;

    /**
     * Get the promotion strategy of an enterprise.
     * @param enterpriseId Enterprise ID
     * @return The promotion strategy of an enterprise
     * @throws PromotionStrategyServiceException An exception is thrown if the query is not successful.
     */
    PromotionStrategy getByEnterpriseId(String enterpriseId) throws PromotionStrategyServiceException, SQLException;
}
