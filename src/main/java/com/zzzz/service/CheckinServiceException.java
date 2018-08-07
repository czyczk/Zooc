package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CheckinServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_YEAR("年份为空。", HttpStatus.BAD_REQUEST),
        EMPTY_MONTH("月份为空。", HttpStatus.BAD_REQUEST),
        EMPTY_DATE("日期为空。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_YEAR("年份不合法。", HttpStatus.BAD_REQUEST),
        INVALID_MONTH("月份不合法。", HttpStatus.BAD_REQUEST),
        INVALID_DATE("日期不合法。", HttpStatus.BAD_REQUEST),
        ALREADY_CHECKED_IN_TODAY("今日已签到。", HttpStatus.BAD_REQUEST),
        CHECKIN_RECORD_NOT_EXISTING("该签到记录不存在。", HttpStatus.NOT_FOUND),
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

    public CheckinServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CheckinServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
