package com.zzzz.repo;

import com.zzzz.po.PromotionStrategy;

public interface PromotionStrategyRepo {
    void save(PromotionStrategy promotionStrategy);
    void update(PromotionStrategy promotionStrategy);
    PromotionStrategy getByEnterpriseId(long enterpriseId);
}
