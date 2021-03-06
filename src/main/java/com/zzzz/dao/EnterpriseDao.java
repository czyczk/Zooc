package com.zzzz.dao;

import com.zzzz.po.Enterprise;

import java.sql.SQLException;

public interface EnterpriseDao {
    int insert(Enterprise enterprise) throws SQLException;
    boolean checkExistenceById(long enterpriseId) throws SQLException;
    Enterprise getById(long enterpriseId) throws SQLException;
    int update(Enterprise enterprise) throws SQLException;
}
