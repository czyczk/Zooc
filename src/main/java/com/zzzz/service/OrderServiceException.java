package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class OrderServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ORDER_ID("订单编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COURSE_ID("课程编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("下单时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_STATUS("状态为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ORDER_ID("订单编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_ID("课程编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TIME("下单时间不合法。", HttpStatus.BAD_REQUEST),
        INVALID_STATUS("状态不合法。", HttpStatus.BAD_REQUEST),
        ORDER_NOT_EXISTING("该订单不存在。", HttpStatus.NOT_FOUND),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND),
        COURSE_NOT_EXISTING("该课程不存在。", HttpStatus.NOT_FOUND)
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

    public OrderServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public OrderServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
