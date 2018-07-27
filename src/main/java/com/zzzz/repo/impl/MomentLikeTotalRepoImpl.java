package com.zzzz.repo.impl;

import com.zzzz.po.MomentLike;
import com.zzzz.repo.MomentLikeTotalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class MomentLikeTotalRepoImpl implements MomentLikeTotalRepo {
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Number> hashOps;

    @Autowired
    private MomentLikeTotalRepoImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    // moment:like {momentId} => numTotalLike
    private static final String KEY = "moment:like:total";

    @Override
    public void saveTotal(long momentId, long total) {
        hashOps.put(KEY, momentId, total);
    }

    @Override
    public void deleteTotal(long momentId) {
        hashOps.delete(KEY, momentId);
    }

    @Override
    public void incrTotalIfCached(long momentId) {
        if (hashOps.hasKey(KEY, momentId))
            hashOps.increment(KEY, momentId, 1);
    }

    @Override
    public void decrTotalIfCached(long momentId) {
        if (hashOps.hasKey(KEY, momentId))
            hashOps.increment(KEY, momentId, -1);
    }

    @Override
    public Long getTotal(long momentId) {
        Number result = hashOps.get(KEY, momentId);
        return result == null ? null : result.longValue();
    }
}
