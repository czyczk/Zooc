package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CouponRecordServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COUPON_RECORD_ID("优惠券使用记录编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COUPON_ID("优惠券编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COUPON_RECORD_ID("优惠券使用记录编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COUPON_ID("优惠券编号不合法。", HttpStatus.BAD_REQUEST),
        COUPON_RECORD_NOT_EXISTING("该优惠券使用记录不存在。", HttpStatus.NOT_FOUND),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND),
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

    public CouponRecordServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CouponRecordServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
