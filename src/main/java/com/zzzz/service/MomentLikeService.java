package com.zzzz.service;

import com.zzzz.po.MomentLike;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface MomentLikeService {
    long insert(String momentId,
                String userId,
                Date time) throws MomentLikeServiceException, SQLException;

    void delete(String momentLikeId) throws MomentLikeServiceException, SQLException;

    /**
     * Check if the user has liked a moment.
     * @param momentId Moment ID
     * @param userId User ID
     * @return `true` if the user has liked it or `false` otherwise
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    boolean hasLiked(String momentId, String userId) throws MomentLikeServiceException, SQLException;

    /**
     * Count the number of likes of a moment meeting the requirements.
     * @param momentId Moment ID
     * @param userId User ID (optional)
     * @return The number of likes of a moment meeting the requirements
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    long countTotal(String momentId, String userId) throws MomentLikeServiceException, SQLException;

    /**
     * Get a list of the latest N moment likes of a moment.
     * @param momentId Moment ID
     * @return A list of N latest moment likes meeting the requirements
     * @throws MomentLikeServiceException An exception is thrown if the query is not successful.
     */
    List<MomentLike> listLatest(String momentId, String n) throws MomentLikeServiceException, SQLException;
}
