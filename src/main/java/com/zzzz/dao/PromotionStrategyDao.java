package com.zzzz.dao;

import com.zzzz.po.PromotionStrategy;

import java.sql.SQLException;

public interface PromotionStrategyDao {
    int insertWithDefaultValues(long enterpriseId) throws SQLException;
    int insert(PromotionStrategy promotionStrategy) throws SQLException;
    int update(PromotionStrategy promotionStrategy) throws SQLException;
    PromotionStrategy getByEnterpriseId(long enterpriseId) throws SQLException;
}
