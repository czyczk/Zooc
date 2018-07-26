package com.zzzz.repo.impl;

import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.vo.EnterpriseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class EnterpriseRepoImpl implements EnterpriseRepo {
    private static final String KEY = "enterprise";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, EnterpriseDetail> hashOps;

    @Autowired
    private EnterpriseRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void saveEnterpriseVO(EnterpriseDetail enterprise) {
        hashOps.put(KEY, enterprise.getEnterpriseId(), enterprise);
    }

    @Override
    public void updateEnterpriseVO(EnterpriseDetail enterprise) {
        hashOps.put(KEY, enterprise.getEnterpriseId(), enterprise);
        // TODO Delete related cache
    }

    @Override
    public void deleteEnterpriseVO(long enterpriseId) {
        hashOps.delete(KEY, enterpriseId);
    }

    @Override
    public EnterpriseDetail getEnterpriseVO(long enterpriseId) {
        return hashOps.get(KEY, enterpriseId);
    }

    @Override
    public boolean isCached(long enterpriseId) {
        return hashOps.hasKey(KEY, enterpriseId);
    }
}
