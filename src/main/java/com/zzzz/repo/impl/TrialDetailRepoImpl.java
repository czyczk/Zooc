package com.zzzz.repo.impl;

import com.zzzz.repo.TrialDetailRepo;
import com.zzzz.vo.TrialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class TrialDetailRepoImpl implements TrialDetailRepo {
    static final String KEY = "trial";
    static final String KEY_LATEST = KEY + ":latest";
    private final RedisTemplate<String, TrialDetail> redisTemplate;
    private HashOperations<String, Long, TrialDetail> hashOps;
    private ListOperations<String, TrialDetail> listOps;

    @Autowired
    private TrialDetailRepoImpl(RedisTemplate<String, TrialDetail> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
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
        listOps.rightPushAll(KEY_LATEST, latestTrials);
    }

    @Override
    public void deleteLatestThree() {
        redisTemplate.delete(KEY_LATEST);
    }

    @Override
    public List<TrialDetail> getLatestThree(long enterpriseId) {
        return listOps.range(KEY_LATEST, 0, -1);
    }
}
