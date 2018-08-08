package com.zzzz.repo.impl;

import com.zzzz.po.Enterprise;
import com.zzzz.repo.EnterpriseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class EnterpriseRepoImpl implements EnterpriseRepo {
    private static final String KEY = "enterprise";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Enterprise> hashOps;

    @Autowired
    private EnterpriseRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void saveEnterprise(Enterprise enterprise) {
        hashOps.put(KEY, enterprise.getEnterpriseId(), enterprise);
    }

    @Override
    public void updateEnterprise(Enterprise enterprise) {
        hashOps.put(KEY, enterprise.getEnterpriseId(), enterprise);
        // Delete all courses
        redisTemplate.delete(CourseDetailRepoImpl.KEY);
        redisTemplate.delete(CourseDetailRepoImpl.KEY_LATEST);
    }

    @Override
    public void deleteEnterprise(long enterpriseId) {
        hashOps.delete(KEY, enterpriseId);
        // Delete all courses
        redisTemplate.delete(CourseDetailRepoImpl.KEY);
        redisTemplate.delete(CourseDetailRepoImpl.KEY_LATEST);
    }

    @Override
    public Enterprise getEnterpriseById(long enterpriseId) {
        return hashOps.get(KEY, enterpriseId);
    }

    @Override
    public boolean isCached(long enterpriseId) {
        return hashOps.hasKey(KEY, enterpriseId);
    }
}
