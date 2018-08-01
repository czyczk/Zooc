package com.zzzz.repo.impl;

import com.zzzz.po.Coupon;
import com.zzzz.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class CouponRepoImpl implements CouponRepo {
    private static final String KEY = "coupon";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Coupon> hashOps;

    @Autowired
    private CouponRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(Coupon coupon) {
        hashOps.put(KEY, coupon.getCouponId(), coupon);
    }

    @Override
    public void update(Coupon coupon) {
        hashOps.put(KEY, coupon.getCouponId(), coupon);
    }

    @Override
    public void delete(long couponId) {
        hashOps.delete(KEY, couponId);
    }

    @Override
    public Coupon getById(long couponId) {
        return hashOps.get(KEY, couponId);
    }

    @Override
    public boolean isCached(long couponId) {
        return hashOps.hasKey(KEY, couponId);
    }
}
