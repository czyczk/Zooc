package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class TrialReservationServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_RESERVATION_ID("预约编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TRIAL_ID("试听课程编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("预约时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_STATUS("状态为空。", HttpStatus.BAD_REQUEST),
        INVALID_RESERVATION_ID("预约编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TRIAL_ID("试听课程编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TIME("预约时间不合法。", HttpStatus.BAD_REQUEST),
        INVALID_STATUS("状态不合法。", HttpStatus.BAD_REQUEST),
        RESERVATION_NOT_EXISTING("该预约不存在。", HttpStatus.BAD_REQUEST),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND),
        TRIAL_NOT_EXISTING("该试听课程不存在。", HttpStatus.NOT_FOUND)
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

    public TrialReservationServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public TrialReservationServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}