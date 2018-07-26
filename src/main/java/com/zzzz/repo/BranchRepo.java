package com.zzzz.repo;

import com.zzzz.po.Branch;

public interface BranchRepo {
    /**
     * Save a branch.
     * @param branch Branch
     */
    void saveBranch(Branch branch);

    /**
     * Get a branch by its ID.
     * @param branchId Branch ID
     * @return Branch
     */
    Branch getBranch(long branchId);

    /**
     * Update a branch. Related cache will be cleared as well,
     * including trials and course offerings.
     * @param branch Branch
     */
    void updateBranch(Branch branch);

    /**
     * Check if a branch is cached.
     * @param branchId Branch ID
     * @return `true` if it's cached or `false` otherwise
     */
    boolean isCached(long branchId);
}
