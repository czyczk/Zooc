package com.zzzz.dao;

import com.zzzz.po.Moment;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface MomentDao {
    /**
     * Insert a moment.
     * @param moment Moment
     * @return Number of rows affected.
     */
    int insert(Moment moment) throws SQLException;

    /**
     * Check whether a moment exists.
     * @param momentId Moment ID
     * @return `true` if the moment exists or `false` otherwise
     */
    boolean checkExistenceById(long momentId) throws SQLException;

    /**
     * Delete a moment.
     * The related likes, images and comments will be deleted as well.
     * @param momentId Moment ID
     * @return Number of rows affected
     */
    int delete(long momentId) throws SQLException;

    /**
     * Update the content of a moment. Only the content is open for modification.
     * @param moment Moment (only the content field is used)
     * @return Number of rows affected
     */
    int update(Moment moment) throws SQLException;

    /**
     * Get a moment by its ID.
     * @param momentId Moment ID
     * @return Moment
     */
    Moment getById(long momentId) throws SQLException;

    /**
     * Count the number of moments of an enterprise.
     * @param enterpriseId The ID of the enterprise to which the moments belong
     * @return The number of moments of an enterprise
     */
    long countTotal(long enterpriseId) throws SQLException;

    /**
     * Get a list of all moments of an enterprise.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the moments belong
     * @return A list of all moments of an enterprise
     */
    List<Moment> list(@Param("usePagination") boolean usePagination,
                      @Param("starting") Long starting, @Param("pageSize") Long pageSize,
                      @Param("enterpriseId") long enterpriseId) throws SQLException;
}
