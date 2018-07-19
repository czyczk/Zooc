package com.zzzz.service;

import com.zzzz.vo.ListResult;
import com.zzzz.vo.MomentCommentDetail;

import java.sql.SQLException;
import java.util.Date;

public interface MomentCommentService {
    /**
     * Insert a moment comment. The ID of the new comment will be returned.
     * @param momentId The ID of the moment to which the comment belongs
     * @param userId The ID of the user who makes this comment
     * @param content Content
     * @param time The time the controller receives the request
     * @return The ID of the new comment
     * @throws MomentCommentServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String momentId,
                String userId,
                String content,
                Date time) throws MomentCommentServiceException, SQLException;

    /**
     * Delete a moment comment.
     * @param momentCommentId Moment comment ID
     * @throws MomentCommentServiceException An exception is thrown if the deletion is not successful.
     */
    void delete(String momentCommentId) throws MomentCommentServiceException, SQLException;

    /**
     * Update a moment comment. Only the content is open for modification.
     * @param targetMomentCommentId Moment comment ID
     * @param content New content (left unchanged if it's null)
     * @throws MomentCommentServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetMomentCommentId, String content) throws MomentCommentServiceException, SQLException;

    /**
     * Get a list of moment comments of a moment.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param momentId Moment ID
     * @return A list of moment comments of a moment.
     * @throws MomentCommentServiceException An exception is thrown if the query is not successful.
     */
    ListResult<MomentCommentDetail> list(String usePagination,
                                         String targetPage, String pageSize,
                                         String momentId) throws MomentCommentServiceException, SQLException;
}
