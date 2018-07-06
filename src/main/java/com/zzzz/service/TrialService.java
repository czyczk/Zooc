package com.zzzz.service;

import com.zzzz.vo.TrialDetail;

import java.sql.SQLException;
import java.util.Date;

public interface TrialService {
    /**
     * Insert a new trial. The ID of the last inserted trial will be returned.
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
     * @param trialId Trial ID
     * @return Trial VO
     * @throws TrialServiceException An exception is thrown if the query is not successful.
     */
    TrialDetail getVoById(String trialId) throws TrialServiceException, SQLException;

    /**
     * Update a trial. Fields will be left unchanged if the corresponding parameters are null.
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
}
