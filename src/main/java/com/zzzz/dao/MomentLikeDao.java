package com.zzzz.dao;

import com.zzzz.po.MomentLike;
import com.zzzz.vo.MomentLikeDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface MomentLikeDao {
    int insert(MomentLike momentLike) throws SQLException;
    int delete(long momentLikeId) throws SQLException;
    long countTotal(long momentId) throws SQLException;

    /**
     * List all likes of a moment.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param momentId Moment ID
     * @return A list of all likes of a moment
     */
    List<MomentLikeDetail> list(@Param("usePagination") boolean usePagination,
                                @Param("starting") Long starting, @Param("pageSize") Long pageSize,
                                @Param("momentId") long momentId) throws SQLException;

}
