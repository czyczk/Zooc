package com.zzzz.service;

import com.zzzz.po.Order;
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
}
