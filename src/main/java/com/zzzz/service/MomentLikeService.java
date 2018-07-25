package com.zzzz.service;

import com.zzzz.vo.MomentLikeDetail;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface MomentLikeService {
    /**
     * Insert a moment like.
     * Redis:
     *   Increment the number of total moment likes.
     * @param momentId Moment ID
     * @param userId User ID
     * @param time The time the controller receives the request
     * @return The new moment like ID
     * @throws MomentLikeServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String momentId,
                String userId,
                Date time) throws MomentLikeServiceException, SQLException;

    void delete(String momentLikeId) throws MomentLikeServiceException, SQLException;

    /**
     * Check if the user has liked a moment.
     * Redis:
     *   Decrement the number of total moment likes.
     * @param momentId Moment ID
     * @param userId User ID
     * @return `true` if the user has liked it or `false` otherwise
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    boolean hasLiked(String momentId, String userId) throws MomentLikeServiceException, SQLException;

    /**
     * Count the number of likes of a given moment.
     * Redis:
     *   Fetch it from the cache; else: cache it.
     * @param momentId Moment ID
     * @return The number of likes of a moment meeting the requirements
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    long countTotal(String momentId) throws MomentLikeServiceException, SQLException;

    /**
     * Get a list of the latest N moment likes of a moment.
     * @param momentId Moment ID
     * @return A list of N latest moment likes meeting the requirements
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    List<MomentLikeDetail> listLatest(String momentId, String n) throws MomentLikeServiceException, SQLException;
}
