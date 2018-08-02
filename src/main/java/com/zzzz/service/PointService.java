package com.zzzz.service;

import java.sql.SQLException;

public interface PointService {
    /**
     * Increment the point by the number of points specified.
     * If the point entry doesn't exist, a new one with the default values will be created first.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param numPoints Number of points to be added (non-negative)
     */
    void incrBy(String userId, String enterpriseId, String numPoints) throws PointServiceException, SQLException;

    /**
     * Decrement the point by the number of points specified.
     * If the point entry doesn't exist, a new one with the default values will be created first.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param numPoints Number of points to be reduced (non-negative)
     */
    void decrBy(String userId, String enterpriseId, String numPoints) throws PointServiceException, SQLException;

    /**
     * Get the point of the user in the enterprise.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @return Number of points of the user in the enterprise
     */
    long getByPk(String userId, String enterpriseId) throws PointServiceException, SQLException;
}
