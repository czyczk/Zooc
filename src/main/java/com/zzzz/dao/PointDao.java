package com.zzzz.dao;

import com.zzzz.po.Point;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

public interface PointDao {
    /**
     * Insert a point entry with the default values.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @return Number of rows affected
     * @throws SQLException An exception is thrown if the insertion is not successful.
     */
    int insertWithDefaultValues(@Param("userId") long userId,
                                @Param("enterpriseId") long enterpriseId) throws SQLException;

    /**
     * Increment a point entry by the number specified.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param numPoints Number of points (unsigned)
     * @return Number of rows affected
     * @throws SQLException An exception is thrown if the update is not successful.
     */
    int incrBy(@Param("userId") long userId,
               @Param("enterpriseId") long enterpriseId,
               @Param("numPoints") long numPoints) throws SQLException;

    /**
     * Decrement a point entry by the number specified.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param numPoints Number of points (unsigned)
     * @return Number of rows affected
     * @throws SQLException An exception is thrown if the update is not successful.
     */
    int decrBy(@Param("userId") long userId,
               @Param("enterpriseId") long enterpriseId,
               @Param("numPoints") long numPoints) throws SQLException;

    /**
     * Get the number of points of the user in the enterprise.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @return The point entry
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    Point getByPk(@Param("userId") long userId,
                  @Param("enterpriseId") long enterpriseId) throws SQLException;
}
