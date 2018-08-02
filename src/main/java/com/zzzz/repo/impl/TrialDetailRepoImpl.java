package com.zzzz.repo.impl;

import com.zzzz.repo.TrialDetailRepo;
import com.zzzz.vo.TrialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class TrialDetailRepoImpl implements TrialDetailRepo {
    static final String KEY = "trial";
    static final String KEY_LATEST = KEY + ":latest";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, TrialDetail> hashOps;

    @Autowired
    private TrialDetailRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(TrialDetail trial) {
        hashOps.put(KEY, trial.getTrialId(), trial);
    }

    @Override
    public void delete(long trialId) {
        hashOps.delete(KEY, trialId);
    }

    @Override
    public TrialDetail getById(long courseId) {
        return hashOps.get(KEY, courseId);
    }

    @Override
    public void saveLatestThree(List<TrialDetail> latestTrials) {
        redisTemplate.opsForValue().set(KEY_LATEST, latestTrials);
    }

    @Override
    public void deleteLatestThree() {
        redisTemplate.delete(KEY_LATEST);
    }

    @Override
    public List<TrialDetail> getLatestThree(long enterpriseId) {
        return (List<TrialDetail>) redisTemplate.opsForValue().get(KEY_LATEST);
    }
}
