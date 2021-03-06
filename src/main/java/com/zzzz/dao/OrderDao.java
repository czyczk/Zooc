package com.zzzz.dao;

import com.zzzz.po.Order;
import com.zzzz.po.OrderStatusEnum;
import com.zzzz.vo.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    int insert(Order order) throws SQLException;
    Order getById(long orderId) throws SQLException;
    OrderDetail getVoById(long orderId) throws SQLException;

    /**
     * Update an order. Only the `status` is open for modification.
     * @param order New order. Only the order ID and the status will be used.
     * @return Number of rows affected.
     * @throws SQLException An exception is thrown if the update is unsuccessful.
     */
    int update(Order order) throws SQLException;

    /**
     * Count the number of orders meeting the requirements.
     * @param enterpriseId Enterprise ID (optional)
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param userEmail User email (optional)
     * @param userMobile User mobile (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional)
     * @return The number of orders meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    long countTotal(@Param("enterpriseId") Long enterpriseId,
                    @Param("orderId") Long orderId,
                    @Param("userId") Long userId,
                    @Param("userEmail") String userEmail,
                    @Param("userMobile") String userMobile,
                    @Param("courseId") Long courseId,
                    @Param("courseNameContaining") String courseNameContaining,
                    @Param("status") OrderStatusEnum status) throws SQLException;

    /**
     * Get a list of all orders meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param enterpriseId Enterprise ID (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional)
     * @return A list of all orders meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<OrderDetail> list(@Param("usePagination") boolean usePagination,
                           @Param("starting") Long starting,
                           @Param("pageSize") Long pageSize,
                           @Param("orderId") Long orderId,
                           @Param("userId") Long userId,
                           @Param("enterpriseId") Long enterpriseId,
                           @Param("courseId") Long courseId,
                           @Param("courseNameContaining") String courseNameContaining,
                           @Param("status") OrderStatusEnum status) throws SQLException;

    /**
     * Get a list of all orders that are being requested to be refunded or already refunded.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
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
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<OrderDetail> listRefund(@Param("usePagination") boolean usePagination,
                                 @Param("starting") Long starting,
                                 @Param("pageSize") Long pageSize,
                                 @Param("enterpriseId") Long enterpriseId,
                                 @Param("orderId") Long orderId,
                                 @Param("userId") Long userId,
                                 @Param("userEmail") String userEmail,
                                 @Param("userMobile") String userMobile,
                                 @Param("courseId") Long courseId,
                                 @Param("courseNameContaining") String courseNameContaining,
                                 @Param("status") OrderStatusEnum status) throws SQLException;
}
