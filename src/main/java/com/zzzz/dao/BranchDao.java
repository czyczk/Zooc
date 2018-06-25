package com.zzzz.dao;

import com.zzzz.po.Branch;

import java.sql.SQLException;

public interface BranchDao {
    int insert(Branch branch) throws SQLException;
    boolean checkExistenceById(long branchId) throws SQLException;
    Branch getById(long branchId) throws SQLException;
    int update(Branch branch) throws SQLException;

    // TODO
    // Cascade when deleted. Implemented later.
}
