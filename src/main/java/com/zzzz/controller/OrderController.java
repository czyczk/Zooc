package com.zzzz.controller;

import com.zzzz.dto.OrderParam;
import com.zzzz.dto.RefundParam;
import com.zzzz.service.OrderService;
import com.zzzz.service.OrderServiceException;
import com.zzzz.service.RefundService;
import com.zzzz.service.RefundServiceException;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.OrderDetail;
import com.zzzz.vo.OrderPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;
    private final RefundService refundService;

    @Autowired
    public OrderController(OrderService orderService, RefundService refundService) {
        this.orderService = orderService;
        this.refundService = refundService;
    }

    /**
     * Preview an order. Open for users who have logged in.
     * @param courseId Course ID
     * @param userId User ID
     * @param couponId Coupon ID (optional)
     * @param usePoints Use points or not (`false` by default)
     * @return Success: Order preview; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/course/{id}/order-preview")
    public ResponseEntity<OrderPreview> previewOrder(@PathVariable("id") String courseId,
                                                     String userId, String couponId, String usePoints) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        OrderPreview preview = orderService.preview(userId, courseId, couponId, usePoints);
        return ResponseEntity.ok(preview);
    }

    /**
     * Place an order. Open for users who have logged in. An order is by default created to be `PENDING` (not yet paid).
     * @param courseId Course ID
     * @param param userId, couponId (optional), usePoints (`false` by default)
     * @return Success: New order ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/course/{id}/order")
    public ResponseEntity<Long> create(@PathVariable("id") String courseId,
                                       @RequestBody OrderParam param) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        long lastId = orderService.insert(param.getUserId(), courseId, param.getCouponId(), param.getUsePoints(), new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get the detail of an order. Open for the purchaser and the course owner.
     * @param orderId Order ID
     * @return Success: Order detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/order/detail/{id}")
    public ResponseEntity<OrderDetail> getDetailById(@PathVariable("id") String orderId) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        OrderDetail result = orderService.getVoById(orderId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update an order.
     * Open for the purchaser to cancel the order.
     * Open for the course owner to approve a refund request.
     * @param orderId Order ID
     * @param param status
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/order/{id}")
    public ResponseEntity update(@PathVariable("id") String orderId,
                                 @RequestBody OrderParam param) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        orderService.update(orderId, param.getStatus());
        return ResponseEntity.noContent().build();
    }

    /**
     * Apply for a refund. Open for only the purchaser.
     * @param orderId The ID of the order to be refunded
     * @param param reason
     * @return Success: New refund record ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/order/{id}/refund")
    public ResponseEntity<Long> applyForRefund(@PathVariable("id") String orderId,
                                               @RequestBody RefundParam param) throws SQLException, RefundServiceException {
        // TODO authentication not implemented yet
        long lastId = refundService.insert(orderId, new Date(), param.getReason());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Cancel a refund. Open for only the purchaser.
     * @param refundId The ID of the refund request to be canceled
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping("/refund/{id}")
    public ResponseEntity cancelRefund(@PathVariable("id") String refundId) throws SQLException, RefundServiceException {
        // TODO authentication not implemented yet
        refundService.delete(refundId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get the user's order history.
     * @param userId User ID
     * @param usePagination Use pagination (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param orderId Order ID (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/user/{id}/order/list")
    public ResponseEntity<ListResult<OrderDetail>> getUserHistory(@PathVariable("id") String userId,
                                                                  String usePagination, String targetPage, String pageSize,
                                                                  String orderId,
                                                                  String courseId, String courseNameContaining,
                                                                  String status) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        ListResult<OrderDetail> result = orderService.list(usePagination, targetPage, pageSize, orderId, userId, null, courseId, courseNameContaining, status);
        return ResponseEntity.ok(result);
    }

    /**
     * Get the order list of an enterprise.
     * @param enterpriseId Enterprise ID
     * @param usePagination Use pagination (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/order/list")
    public ResponseEntity<ListResult<OrderDetail>> list(@PathVariable("id") String enterpriseId,
                                                        String usePagination, String targetPage, String pageSize,
                                                        String orderId, String userId,
                                                        String courseId, String courseNameContaining,
                                                        String status) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        ListResult<OrderDetail> result = orderService.list(usePagination, targetPage, pageSize, orderId, userId, enterpriseId, courseId, courseNameContaining, status);
        return ResponseEntity.ok(result);
    }

    /**
     * Get the refund list of an enterprise.
     * @param enterpriseId Enterprise ID
     * @param usePagination Use pagination (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param orderId Order ID (optional)
     * @param userId User ID (optional)
     * @param userEmail User email (optional)
     * @param userMobile User mobile (optional)
     * @param courseId Course ID (optional)
     * @param courseNameContaining Course name containing (optional)
     * @param status Status (optional) (REFUND_REQUESTED/REFUNDED)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/refund/list")
    public ResponseEntity<ListResult<OrderDetail>> listRefund(@PathVariable("id") String enterpriseId,
                                                              String usePagination, String targetPage, String pageSize,
                                                              String orderId,
                                                              String userId, String userEmail, String userMobile,
                                                              String courseId, String courseNameContaining,
                                                              String status) throws SQLException, OrderServiceException {
        // TODO authentication not implemented yet
        ListResult<OrderDetail> result = orderService.listRefund(usePagination, targetPage, pageSize, enterpriseId, orderId, userId, userEmail, userMobile, courseId, courseNameContaining, status);
        return ResponseEntity.ok(result);
    }
}
