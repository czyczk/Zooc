package com.zzzz.dao;

import com.zzzz.po.MomentLike;
import com.zzzz.vo.MomentLikeDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface MomentLikeDao {
    /**
     * Insert a moment like.
     * @param momentLike Moment like
     * @return Number of rows affected
     */
    int insert(MomentLike momentLike) throws SQLException;

    /**
     * Delete a moment like.
     * @param momentLikeId Moment like ID
     * @return Number of rows affected
     */
    int delete(long momentLikeId) throws SQLException;

    /**
     * Get a moment like by its ID.
     * @param momentLikeId Moment like ID
     * @return Moment like
     */
    MomentLike getById(long momentLikeId) throws SQLException;

    /**
     * Get a moment like by its moment ID and user ID.
     * @param momentId Moment ID
     * @param userId User ID
     * @return Moment like
     */
    MomentLike getByMomentIdAndUserId(@Param("momentId") long momentId, @Param("userId") long userId) throws SQLException;

    /**
     * Get the number of likes of a moment that meet the requirements.
     * @param momentId Moment ID
     * @param userId User ID (optional)
     * @return The number of likes of a moment
     */
    long countTotal(@Param("momentId") long momentId, @Param("userId") Long userId) throws SQLException;

    /**
     * List N latest likes of a moment.
     * @param momentId Moment ID
     * @param n The number of likes to be listed
     * @return A N latest likes of a moment
     */
    List<MomentLikeDetail> list(@Param("momentId") long momentId,
                                @Param("n") int n) throws SQLException;

}
