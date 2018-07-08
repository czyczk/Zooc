package com.zzzz.service;

import com.zzzz.po.Refund;

import java.sql.SQLException;
import java.util.Date;

public interface RefundService {
    /**
     * Create for a refund record and set the corresponding order to `REFUND_REQUESTED`.
     * The ID of the new refund record will be returned.
     * @param orderId The ID of the order requested to be refunded
     * @param time The time the controller receives the request
     * @param reason Reason
     * @return New refund record ID
     * @throws RefundServiceException An exception is thrown if the any of the operations is not successful.
     */
    long insert(String orderId, Date time, String reason) throws SQLException, RefundServiceException;

    /**
     * Get a refund record by its ID.
     * @param refundId Refund ID
     * @return Refund record
     * @throws RefundServiceException An exception is thrown if the query is not successful.
     */
    Refund getById(String refundId) throws SQLException, RefundServiceException;

    /**
     * Update a refund record. Only the reason is open for modification.
     * Nothing will be changed if the parameter is null or empty.
     * @param targetRefundId Target refund ID
     * @param reason New reason
     * @throws RefundServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetRefundId, String reason) throws SQLException, RefundServiceException;

    /**
     * Delete a refund record and set the corresponding order to `AVAILABLE`.
     * @param refundId Refund ID
     * @throws RefundServiceException An exception is thrown if the deletion is not successful.
     */
    void delete(String refundId) throws SQLException, RefundServiceException;
}
