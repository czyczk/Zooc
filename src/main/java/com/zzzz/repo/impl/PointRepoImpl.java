package com.zzzz.repo.impl;

import com.zzzz.repo.PointRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class PointRepoImpl implements PointRepo {
    private static final String KEY = "point";

    private String getHashKey(long userId, long enterpriseId) {
        return userId + ":" + enterpriseId;
    }

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Long> hashOps;

    @Autowired
    private PointRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(long userId, long enterpriseId, long point) {
        hashOps.put(KEY, getHashKey(userId, enterpriseId), point);
    }

    @Override
    public Long getByPk(long userId, long enterpriseId) {
        return hashOps.get(KEY, getHashKey(userId, enterpriseId));
    }

    @Override
    public void incrByIfExisting(long userId, long enterpriseId, int numPoints) {
        String hashKey = getHashKey(userId, enterpriseId);
        if (hashOps.hasKey(KEY, hashKey)) {
            hashOps.increment(KEY, hashKey, numPoints);
        }
    }

    @Override
    public void decrByIfExisting(long userId, long enterpriseId, int numPoints) {
        String hashKey = getHashKey(userId, enterpriseId);
        if (hashOps.hasKey(KEY, hashKey)) {
            hashOps.increment(KEY, hashKey, -numPoints);
        }
    }
}
