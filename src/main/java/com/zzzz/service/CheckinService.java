package com.zzzz.service;

import com.zzzz.po.CheckinRecord;

import java.sql.SQLException;
import java.util.List;

public interface CheckinService {
    /**
     * Check in.
     * Insert a checkin record and assign the points.
     * Redis:
     *   Update points.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     */
    void checkIn(String userId, String enterpriseId) throws CheckinServiceException, SQLException;

    /**
     * List checkin records of the user in the enterprise within the month of the year.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param year Year
     * @param month Month
     * @return Checkin records
     */
    List<CheckinRecord> list(String userId, String enterpriseId,
                             String year, String month) throws CheckinServiceException, SQLException;
}
