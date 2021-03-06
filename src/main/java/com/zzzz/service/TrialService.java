package com.zzzz.service;

import com.zzzz.vo.ListResult;
import com.zzzz.vo.TrialDetail;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface TrialService {
    /**
     * Insert a new trial. The ID of the last inserted trial will be returned.
     * Redis:
     *   - Clear cache of the latest three trials since the list has been changed.
     * @param name Name
     * @param detail Detail
     * @param imgUrl Image URL
     * @param categoryId Category ID
     * @param branchId The ID of the branch to which the trial belongs
     * @param lecturerId Lecturer ID
     * @param releaseTime Release time (The time the controller receives the request)
     * @return ID of the new trial
     * @throws TrialServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String name, String detail, String imgUrl, String categoryId, String branchId, String lecturerId,
                Date releaseTime) throws TrialServiceException, SQLException;

    /**
     * Get the VO of a trial by its ID.
     * Redis:
     *   - Try to fetch it from the cache first.
     *     On missing, fetch it from the DB and cache it.
     * @param trialId Trial ID
     * @return Trial VO
     * @throws TrialServiceException An exception is thrown if the query is not successful.
     */
    TrialDetail getVoById(String trialId) throws TrialServiceException, SQLException;

    /**
     * Update a trial. Fields will be left unchanged if the corresponding parameters are null.
     * Redis:
     *   - Delete the cache of it.
     *   - Clear cache of the latest three trials since the list has been changed.
     * @param targetTrialId Target trial ID
     * @param name New name
     * @param detail New detail
     * @param imgUrl New image URL
     * @param categoryId New category ID
     * @param branchId New branch ID
     * @param lecturerId New lecturer ID
     * @param releaseTime New release time
     * @param status New status
     * @throws TrialServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetTrialId,
                String name,
                String detail,
                String imgUrl,
                String categoryId,
                String branchId,
                String lecturerId,
                String releaseTime,
                String status) throws TrialServiceException, SQLException;

    /**
     * Get a list of trials meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param trialId Trial ID (optional)
     * @param nameContaining Name containing (optional)
     * @param branchId The ID of the branch to which the trials belong (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param categoryId Category ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @param status Status (optional)
     * @return A list of trials meeting the requirements
     * @throws TrialServiceException An exception is thrown if the query is not successful.
     */
    ListResult<TrialDetail> list(String usePagination,
                                 String targetPage,
                                 String pageSize,
                                 String enterpriseId,
                                 String trialId,
                                 String nameContaining,
                                 String branchId,
                                 String branchNameContaining,
                                 String categoryId,
                                 String lecturerNameContaining,
                                 String status) throws TrialServiceException, SQLException;

    /**
     * Get a list of N latest trials of a enterprise.
     * Redis:
     *   - Fetch them from the cache first.
     *     On missing, fetch them from the database and cache them.
     * The actual number of items can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param n The number of latest trials to list
     * @return A list of N latest trials of the enterprise
     * @throws TrialServiceException An exception is thrown if the query is not successful.
     */
    List<TrialDetail> listLatest(String enterpriseId,
                                 String n) throws TrialServiceException, SQLException;
}
