package com.zzzz.dao;

import com.zzzz.po.Order;
import com.zzzz.vo.OrderDetail;

import java.sql.SQLException;

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
}
