package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class AdministratorServiceException extends Exception {
    public enum ExceptionTypeEnum {
        INTERNAL_ERROR("内部错误。", HttpStatus.INTERNAL_SERVER_ERROR),
        EMPTY_USERNAME("用户名为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PASSWORD("密码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ADMINISTRATOR_ID("管理员编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_ADMINISTRATOR_ID("管理员编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        ADMINISTRATOR_NOT_EXISTING("该管理员不存在。", HttpStatus.BAD_REQUEST),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.BAD_REQUEST),
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

    private ExceptionTypeEnum exceptionTypeEnum;
    public ExceptionTypeEnum getExceptionTypeEnum() {
        return exceptionTypeEnum;
    }

    public AdministratorServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this.exceptionTypeEnum = exceptionTypeEnum;
    }
}
