package com.zzzz.repo.impl;

import com.zzzz.repo.UserRepo;
import com.zzzz.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class UserRepoImpl implements UserRepo {
    // user {id} => User
    private static final String KEY = "user";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, User> hashOps;

    @Autowired
    private UserRepoImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void saveUser(User user) {
        hashOps.put(KEY, user.getUserId(), user);
    }

    @Override
    public void updateUser(User user) {
        hashOps.put(KEY, user.getUserId(), user);
    }

    @Override
    public User getUser(long userId) {
        return hashOps.get(KEY, userId);
    }

    @Override
    public boolean isCached(long userId) {
        return hashOps.hasKey(KEY, userId);
    }
}
