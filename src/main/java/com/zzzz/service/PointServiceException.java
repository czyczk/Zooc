package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class PointServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_POINT_DELTA("积分变动值为空。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_POINT_DELTA("积分变动值不合法，要求非负整数。", HttpStatus.BAD_REQUEST),
        NOT_ENOUGH_POINTS("积分不足。", HttpStatus.BAD_REQUEST),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND)
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

    public PointServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public PointServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
