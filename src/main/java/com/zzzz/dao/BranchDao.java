package com.zzzz.dao;

import com.zzzz.po.Branch;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface BranchDao {
    int insert(Branch branch) throws SQLException;
    boolean checkExistenceById(long branchId) throws SQLException;
    Branch getById(long branchId) throws SQLException;
    int update(Branch branch) throws SQLException;

    /**
     * Count the total number of items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the branch belong
     * @param branchId The target branch ID (optional)
     * @param nameContaining Name containing (optional)
     * @param addressContaining Address containing (optional)
     * @return The total number of items meeting the requirements
     */
    long countTotal(@Param("enterpriseId") long enterpriseId,
                    @Param("branchId") Long branchId,
                    @Param("nameContaining") String nameContaining,
                    @Param("addressContaining") String addressContaining) throws SQLException;

    /**
     * Query all items meeting the requirements.
     * @param starting Starting index
     * @param pageSize Page size
     * @param enterpriseId The ID of the enterprise to which the branch belong
     * @param branchId The target branch ID (optional)
     * @param nameContaining Name containing (optional)
     * @param addressContaining Address containing (optional)
     * @return A list containing all items meeting the requirements.
     */
    List<Branch> list(@Param("starting") long starting,
                      @Param("pageSize") long pageSize,
                      @Param("enterpriseId") long enterpriseId,
                      @Param("branchId") Long branchId,
                      @Param("nameContaining") String nameContaining,
                      @Param("addressContaining") String addressContaining) throws SQLException;

    // TODO
    // Cascade when deleted. Implemented later.
}
