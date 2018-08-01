package com.zzzz.dao;

import com.zzzz.po.PromotionStrategy;

import java.sql.SQLException;

public interface PromotionStrategyDao {
    int insertWithDefaultValues(long enterpriseId) throws SQLException;
    int insert(PromotionStrategy promotionStrategy) throws SQLException;

    /**
     * Update the promotion strategy of an enterprise.
     * `useCoupons`, `usePoints`, `pointsPerYuan` and `checkinPoints` are open for modification.
     * @param promotionStrategy Promotion strategy
     * @return Number of rows affected
     */
    int update(PromotionStrategy promotionStrategy) throws SQLException;
    PromotionStrategy getByEnterpriseId(long enterpriseId) throws SQLException;
}
