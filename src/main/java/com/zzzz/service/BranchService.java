package com.zzzz.service;

import com.zzzz.po.Branch;

public interface BranchService {
    /**
     * Insert a new branch
     * @param enterpriseId Enterprise ID
     * @param name Name
     * @param address Address
     * @param latitude Latitude
     * @param longitude Longitude
     * @param telephone Telephone
     * @return The ID of the new branch
     * @throws BranchServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String enterpriseId,
                String name,
                String address,
                String latitude,
                String longitude,
                String telephone) throws BranchServiceException;

    boolean checkExistenceById(String branchId) throws BranchServiceException;

    Branch getById(String branchId) throws BranchServiceException;

    void update(String targetBranchId,
                String name,
                String address,
                String latitude,
                String longitude,
                String telephone) throws BranchServiceException;

    // TODO deletions can cascade
}
