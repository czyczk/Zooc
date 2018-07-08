package com.zzzz.controller;

import com.zzzz.dto.OrderParam;
import com.zzzz.service.OrderService;
import com.zzzz.service.OrderServiceException;
import com.zzzz.vo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Place an order. Open for users who have logged in. An order is by default created to be `PENDING` (not yet paid).
     * @param courseId Course ID
     * @param param userId
     * @return Success: New order ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/course/{id}/order")
    public ResponseEntity<Long> create(@PathVariable("id") String courseId,
                                       @RequestBody OrderParam param) throws SQLException, OrderServiceException {
        long lastId = orderService.insert(param.getUserId(), courseId, new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get the detail of an order. Open for the purchaser and the course owner.
     * @param orderId Order ID
     * @return Success: Order detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/order/detail/{id}")
    public ResponseEntity<OrderDetail> getDetailById(@PathVariable("id") String orderId) throws SQLException, OrderServiceException {
        OrderDetail result = orderService.getVoById(orderId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update an order.
     * Open for the purchaser to cancel the order.
     * @param orderId Order ID
     * @param param status
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/order/{id}")
    public ResponseEntity update(@PathVariable("id") String orderId,
                                 @RequestBody OrderParam param) throws SQLException, OrderServiceException {
        orderService.update(orderId, param.getStatus());
        return ResponseEntity.noContent().build();
    }
}
