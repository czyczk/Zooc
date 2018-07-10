package com.zzzz.service;

import com.zzzz.po.Order;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.OrderDetail;

import java.sql.SQLException;
import java.util.Date;

public interface OrderService {
    /**
     * Insert a new order. The status is `PENDING` (not paid) by default.
     * @param userId User ID
     * @param courseId Course ID
     * @param time The time the controller receives the request
     * @return New order ID
     * @throws OrderServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String userId, String courseId, Date time) throws SQLException, OrderServiceException;

    Order getById(String orderId) throws SQLException, OrderServiceException;

    OrderDetail getVoById(String orderId) throws SQLException, OrderServiceException;

    /**
     * Update an order. Only the status is open for modification.
     * @param targetOrderId Target order ID
     * @param status New status
     * @throws OrderServiceException An exception is thrown if the insertion is not successful.
     */
    void update(String targetOrderId, String status) throws SQLException, OrderServiceException;

    /**
     * Get a list containing orders meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param enterpriseId Enterprise ID (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional)
     * @return A list containing orders meeting the requirements
     * @throws OrderServiceException An exception is thrown if the query is not successful.
     */
    ListResult<OrderDetail> list(String usePagination, String targetPage, String pageSize,
                                           String orderId, String userId, String enterpriseId,
                                           String courseId, String courseNameContaining,
                                           String status) throws SQLException, OrderServiceException;
}
