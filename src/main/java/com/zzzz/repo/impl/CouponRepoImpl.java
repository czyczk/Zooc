package com.zzzz.repo.impl;

import com.zzzz.po.Coupon;
import com.zzzz.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Repository
public class CouponRepoImpl implements CouponRepo {
    private static final String KEY = "coupon";
    private static String getUserAvailableCouponsKey(long enterpriseId, long userId) {
        return KEY + ":user_available:" + enterpriseId + ":" + userId;
    }
    private static String getUserAvailableCouponsKey(long enterpriseId) {
        return KEY + ":user_available:" + enterpriseId + ":*";
    }

    private final RedisTemplate<String, Coupon> redisTemplate;
    private HashOperations<String, Long, Coupon> hashOps;
    private ListOperations<String, Coupon> listOps;

    @Autowired
    private CouponRepoImpl(RedisTemplate<String, Coupon> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
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

    @Override
    public void saveUserAvailableCoupons(long enterpriseId, long userId, List<Coupon> userAvailableCoupons) {
        listOps.rightPushAll(getUserAvailableCouponsKey(enterpriseId, userId), userAvailableCoupons);
    }

    @Override
    public void deleteUserAvailableCoupons(long enterpriseId, long userId) {
        redisTemplate.delete(getUserAvailableCouponsKey(enterpriseId, userId));
    }

    @Override
    public void deleteUserAvailableCoupons(long enterpriseId) {
        Set<String> keys = redisTemplate.keys(getUserAvailableCouponsKey(enterpriseId));
        redisTemplate.delete(keys);
    }

    @Override
    public List<Coupon> getUserAvailableCoupons(long enterpriseId, long userId) {
        return listOps.range(getUserAvailableCouponsKey(enterpriseId, userId), 0, -1);
    }
}
