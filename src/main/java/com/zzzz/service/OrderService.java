package com.zzzz.service;

import com.zzzz.po.Order;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.OrderDetail;
import com.zzzz.vo.OrderPreview;

import java.sql.SQLException;
import java.util.Date;

public interface OrderService {
    /**
     * Preview an order.
     * @param userId User ID
     * @param courseId Course ID
     * @param couponId Coupon ID
     * @param usePoints Use points or not
     * @return Order preview
     * @throws OrderServiceException An exception is thrown if the preview is not successful.
     */
    OrderPreview preview(String userId, String courseId, String couponId, String usePoints) throws SQLException, OrderServiceException;

    /**
     * Insert a new order. The status is `PENDING` (not paid) by default.
     * @param userId User ID
     * @param courseId Course ID
     * @param time The time the controller receives the request
     * @return New order ID
     * @throws OrderServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String userId, String courseId, String couponId, String usePoints, Date time) throws SQLException, OrderServiceException;

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

    /**
     * Get a list of all orders that are being requested to be refunded or already refunded.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the orders belong
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param userEmail User email (optional)
     * @param userMobile User mobile (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional) (REFUND_REQUESTED/REFUNDED)
     * @return A list of all orders that are being requested to be refunded or already refunded
     * @throws OrderServiceException An exception is thrown if the query is not successful.
     */
    ListResult<OrderDetail> listRefund(String usePagination, String targetPage, String pageSize,
                                       String enterpriseId,
                                       String orderId,
                                       String userId, String userEmail, String userMobile,
                                       String courseId, String courseNameContaining,
                                       String status) throws SQLException, OrderServiceException;
}
