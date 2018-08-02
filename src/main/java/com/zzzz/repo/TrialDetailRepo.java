package com.zzzz.repo;

import com.zzzz.vo.TrialDetail;

import java.util.List;

public interface TrialDetailRepo {
    void save(TrialDetail trial);
    void delete(long trialId);
    TrialDetail getById(long trialId);
    void saveLatestThree(List<TrialDetail> latestTrials);
    void deleteLatestThree();
    List<TrialDetail> getLatestThree(long enterpriseId);
}
