package com.zzzz.repo.impl;

import com.zzzz.po.PromotionStrategy;
import com.zzzz.repo.PromotionStrategyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class PromotionStrategyRepoImpl implements PromotionStrategyRepo {
    private static final String KEY = "promotion_strategy";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, PromotionStrategy> hashOps;

    @Autowired
    private PromotionStrategyRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(PromotionStrategy promotionStrategy) {
        hashOps.put(KEY, promotionStrategy.getEnterpriseId(), promotionStrategy);
    }

    @Override
    public void update(PromotionStrategy promotionStrategy) {
        hashOps.put(KEY, promotionStrategy.getEnterpriseId(), promotionStrategy);
    }

    @Override
    public PromotionStrategy getByEnterpriseId(long enterpriseId) {
        return hashOps.get(KEY, enterpriseId);
    }
}
