package com.zzzz.dao;

import com.zzzz.po.MomentComment;
import com.zzzz.vo.MomentCommentDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface MomentCommentDao {
    int insert(MomentComment momentComment) throws SQLException;
    int delete(long momentCommentId) throws SQLException;

    /**
     * Update a moment comment. Only the content is open for modification.
     * @param momentComment Moment comment. Only the content field will be used
     * @return Number of rows affected
     */
    int update(MomentComment momentComment) throws SQLException;

    /**
     * Count the number of comments of a moment.
     * @param momentId Moment ID
     * @return The total number of comments of a moment
     */
    long countTotal(long momentId) throws SQLException;

    /**
     * List all comments of a moment.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param momentId Moment ID
     * @return A list of all comments of a moment
     */
    List<MomentCommentDetail> list(@Param("usePagination") boolean usePagination,
                                   @Param("starting") Long starting, @Param("pageSize") Long pageSize,
                                   @Param("momentId") long momentId) throws SQLException;
}
