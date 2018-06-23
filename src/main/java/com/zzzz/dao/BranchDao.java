package com.zzzz.dao;

import com.zzzz.po.Branch;

public interface BranchDao {
    int insert(Branch branch);
    boolean checkExistenceById(long branchId);
    Branch getById(long branchId);
    Branch getDtoById(long branchId);
    int update(Branch branch);

    // TODO
    // Cascade when deleted. Implemented later.
}
