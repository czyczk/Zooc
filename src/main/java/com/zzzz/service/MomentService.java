package com.zzzz.service;

import com.zzzz.po.Moment;
import com.zzzz.vo.ListResult;

import java.sql.SQLException;
import java.util.Date;

public interface MomentService {
    /**
     * Insert a moment. The ID of the new moment will be returned.
     * @param enterpriseId The ID of the enterprise to which the moment belongs
     * @param content Content
     * @param time The time the moment received by the controller
     * @return The ID of the new moment
     * @throws MomentServiceException An exception is thrown if the insertion is not successful.
     */
    Long insert(String enterpriseId,
                String content, Date time) throws MomentServiceException, SQLException;

    /**
     * Delete a moment.
     * @param momentId Moment ID
     * @throws MomentServiceException An exception is thrown if the deletion is not successful.
     */
    void delete(String momentId) throws MomentServiceException, SQLException;

    /**
     * Update a moment. Only the content is open for modification.
     * @param momentId Moment ID
     * @param content New content (left unchanged if it's null)
     * @throws MomentServiceException An exception is thrown if the update is not successful.
     */
    void update(String momentId, String content) throws MomentServiceException, SQLException;

    /**
     * Get a list of all moments of an enterprise.
     * @param usePagination Use pagination or not
     * @param targetPage Target page
     * @param pageSize Page size
     * @param enterpriseId Enterprise ID
     * @return A list of all moments of an enterprise
     * @throws MomentServiceException An exception is thrown if the query is not successful.
     */
    ListResult<Moment> list(String usePagination,
                            String targetPage, String pageSize,
                            String enterpriseId) throws MomentServiceException, SQLException;
}
