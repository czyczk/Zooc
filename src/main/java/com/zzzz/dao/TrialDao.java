package com.zzzz.dao;

import com.zzzz.po.TrialStatusEnum;
import com.zzzz.vo.TrialDetail;
import com.zzzz.po.Trial;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface TrialDao {
    int insert(Trial trial) throws SQLException;
    boolean checkExistenceById(long trialId) throws SQLException;
    Trial getById(long trialId) throws SQLException;
    TrialDetail getVoById(long trialId) throws SQLException;
    int update(Trial trial) throws SQLException;
    int delete(long trialId) throws SQLException;

    /**
     * Count the number of items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param trialId Trial ID (optional)
     * @param nameContaining Name containing (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param categoryId Category ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @param status Status (optional)
     * @return The number of items meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    long countTotal(@Param("enterpriseId") long enterpriseId,
                    @Param("trialId") Long trialId,
                    @Param("nameContaining") String nameContaining,
                    @Param("branchId") Long branchId,
                    @Param("branchNameContaining") String branchNameContaining,
                    @Param("categoryId") Long categoryId,
                    @Param("lecturerNameContaining") String lecturerNameContaining,
                    @Param("status") TrialStatusEnum status) throws SQLException;

    /**
     * Get a list of items meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param trialId Trial ID (optional)
     * @param nameContaining Name containing (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param categoryId Category ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @param status Status (optional)
     * @return A list of items meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<TrialDetail> list(@Param("usePagination") boolean usePagination,
                           @Param("starting") Long starting,
                           @Param("pageSize") Long pageSize,
                           @Param("enterpriseId") long enterpriseId,
                           @Param("trialId") Long trialId,
                           @Param("nameContaining") String nameContaining,
                           @Param("branchId") Long branchId,
                           @Param("branchNameContaining") String branchNameContaining,
                           @Param("categoryId") Long categoryId,
                           @Param("lecturerNameContaining") String lecturerNameContaining,
                           @Param("status") TrialStatusEnum status) throws SQLException;

    /**
     * Get a list of N latest available trials of the enterprise.
     * The actual number of items can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param n The number of items to be listed
     * @return A list of N latest trials of the enterprise
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<TrialDetail> listLatest(@Param("enterpriseId") long enterpriseId,
                                  @Param("n") int n) throws SQLException;
}
