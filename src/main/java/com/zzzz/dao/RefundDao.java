package com.zzzz.dao;

import com.zzzz.po.Refund;

import java.sql.SQLException;

public interface RefundDao {
    int insert(Refund refund) throws SQLException;
    Refund getById(long refundId) throws SQLException;

    /**
     * Update a refund. Only the reason is open for modification.
     * @param refund Refund. Only the refund ID and the reason will be used
     * @return Number of rows affected
     * @throws SQLException An exception is thrown if the update is not successful
     */
    int update(Refund refund) throws SQLException;

    /**
     * Delete a refund.
     * @param refundId Refund ID
     * @return Number of rows affected
     * @throws SQLException An exception is thrown if the deletion is not successful
     */
    int delete(long refundId) throws SQLException;
}
