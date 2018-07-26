package com.zzzz.repo.impl;

import com.zzzz.po.Branch;
import com.zzzz.repo.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class BranchRepoImpl implements BranchRepo {
    private static final String KEY = "branch";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Branch> hashOps;

    @Autowired
    private BranchRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }


    @Override
    public void saveBranch(Branch branch) {
        hashOps.put(KEY, branch.getBranchId(), branch);
    }

    @Override
    public Branch getBranch(long branchId) {
        return hashOps.get(KEY, branchId);
    }

    @Override
    public void deleteBranch(long branchId) {
        hashOps.delete(KEY, branchId);
        // TODO delete related cache (trials and course offerings)
    }

    @Override
    public void updateBranch(Branch branch) {
        hashOps.put(KEY, branch.getBranchId(), branch);
        // TODO delete related cache (trials and course offerings)
    }

    @Override
    public boolean isCached(long branchId) {
        return hashOps.hasKey(KEY, branchId);
    }
}
