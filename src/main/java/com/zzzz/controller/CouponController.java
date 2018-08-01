package com.zzzz.controller;

import com.zzzz.dto.CouponParam;
import com.zzzz.po.Coupon;
import com.zzzz.service.CouponService;
import com.zzzz.service.CouponServiceException;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * Create a coupon.
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param param value, threshold
     * @return Success: New coupon ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/enterprise/{id}/coupon")
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 @RequestBody CouponParam param) throws SQLException, CouponServiceException {
        long lastId = couponService.insert(enterpriseId, param.getValue(), param.getThreshold(), new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get a coupon by its ID.
     * @param couponId Coupon ID
     * @return Success: Coupon; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/coupon/{id}")
    public ResponseEntity<Coupon> getById(@PathVariable("id") String couponId) throws SQLException, CouponServiceException {
        Coupon result = couponService.getById(couponId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a coupon. The value and the threshold is open for modification.
     * Fields will be left unchanged if the corresponding parameter is not specified.
     * The (last modified) date will be updated automatically.
     * @param couponId Coupon ID
     * @param param value, threshold
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/coupon/{id}")
    public ResponseEntity update(@PathVariable("id") String couponId,
                                 @RequestBody CouponParam param) throws SQLException, CouponServiceException {
        couponService.update(couponId, param.getValue(), param.getThreshold());
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a coupon. It's in fact disabled.
     * @param couponId Coupon ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping("/coupon/{id}")
    public ResponseEntity delete(@PathVariable("id") String couponId) throws SQLException, CouponServiceException {
        couponService.disable(couponId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list of coupons meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the coupon belongs
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (Required when using pagination)
     * @param pageSize Page size (Required when using pagination)
     * @param couponId Coupon ID (Optional)
     * @param minValue Lower bound of value (Optional)
     * @param maxValue Upper bound of value (Optional)
     * @param minThreshold Lower bound of threshold (Optional)
     * @param maxThreshold Upper bound of threshold (Optional)
     * @param laterThan Coupon created later than [Included] (Optional)
     * @param earlierThan Coupon created earlier than [Included] (Optional)
     * @return Success: Coupon list; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/coupon/list")
    public ResponseEntity<ListResult<Coupon>> list(@PathVariable("id") String enterpriseId,
                               String usePagination,
                               String targetPage, String pageSize,
                               String couponId,
                               String minValue, String maxValue,
                               String minThreshold, String maxThreshold,
                               String laterThan, String earlierThan) throws SQLException, CouponServiceException {
        ListResult<Coupon> result = couponService.list(usePagination, targetPage, pageSize,
                enterpriseId, couponId,
                minValue, maxValue,
                minThreshold, maxThreshold,
                laterThan, earlierThan);
        return ResponseEntity.ok(result);
    }
}
