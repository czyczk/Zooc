package com.zzzz.service;

import com.zzzz.po.Branch;

import java.sql.SQLException;

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
                String telephone) throws BranchServiceException, SQLException;

    boolean checkExistenceById(String branchId) throws BranchServiceException, SQLException;

    Branch getById(String branchId) throws BranchServiceException, SQLException;

    /**
     * Update a branch. A field will be left unchanged if the parameter is null.
     * @param targetBranchId Target branch ID
     * @param name New name
     * @param address New address
     * @param latitude New latitude
     * @param longitude New longitude
     * @param telephone New telephone
     * @throws BranchServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetBranchId,
                String name,
                String address,
                String latitude,
                String longitude,
                String telephone) throws BranchServiceException, SQLException;

    // TODO deletions can cascade
}
