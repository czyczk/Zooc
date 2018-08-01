package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CouponServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_VALUE("价值为空。", HttpStatus.BAD_REQUEST),
        EMPTY_THRESHOLD("门槛为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("最后修改时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COUPON_ID("优惠券编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_VALUE("价值不合法。", HttpStatus.BAD_REQUEST),
        INVALID_THRESHOLD("门槛不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COUPON_ID("优惠券不合法。", HttpStatus.BAD_REQUEST),
        COUPON_DISABLED("该优惠券已禁用。", HttpStatus.BAD_REQUEST),
        INVALID_VALUE_RANGE("价值范围不合法。", HttpStatus.BAD_REQUEST),
        INVALID_THRESHOLD_RANGE("门槛范围不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TIME_RANGE("最后修改时间范围不合法。", HttpStatus.BAD_REQUEST),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND),
        COUPON_NOT_EXISTING("该优惠券不存在。", HttpStatus.NOT_FOUND)
        ;
        private String message;

        private HttpStatus status;
        public String getMessage() {
            return message;
        }

        public HttpStatus getStatus() {
            return status;
        }
        ExceptionTypeEnum(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

    }

    public CouponServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CouponServiceException(ExceptionTypeEnum typeEnum) {
        super(typeEnum.getMessage(), typeEnum.getStatus());
    }
}
