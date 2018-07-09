package com.zzzz.service;

import com.zzzz.dto.BranchParam;
import com.zzzz.po.Branch;
import com.zzzz.vo.ListResult;

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

    /**
     * Disable a branch. The branch will be invisible from now on.
     * @param branchId Branch ID
     * @throws BranchServiceException An exception will be thrown if the operation is not successful.
     */
    void disable(String branchId) throws BranchServiceException, SQLException;

    /**
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId Enterprise ID
     * @param branchId Branch ID (optional)
     * @param nameContaining Name containing (optional)
     * @param addressContaining Address containing (optional)
     * @return List containing items meeting the requirements
     * @throws BranchServiceException An exception is thrown if the query is not successful.
     */
    ListResult<Branch> list(String usePagination,
                            String targetPage,
                            String pageSize,
                            String enterpriseId,
                            String branchId,
                            String nameContaining,
                            String addressContaining) throws BranchServiceException, SQLException;
}
