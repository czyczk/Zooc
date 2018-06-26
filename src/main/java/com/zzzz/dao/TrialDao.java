package com.zzzz.dao;

import com.zzzz.vo.TrialDetail;
import com.zzzz.po.Trial;

import java.sql.SQLException;

public interface TrialDao {
    int insert(Trial trial) throws SQLException;
    boolean checkExistenceById(long trialId) throws SQLException;
    Trial getById(long trialId) throws SQLException;
    TrialDetail getVoById(long trialId) throws SQLException;
    int update(Trial trial) throws SQLException;
}
