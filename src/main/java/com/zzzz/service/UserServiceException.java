package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class UserServiceException extends Exception {
    public enum ExceptionTypeEnum {
        INTERNAL_ERROR("服务器内部错误。", HttpStatus.INTERNAL_SERVER_ERROR),
        INVALID_USERNAME("不合法的用户名。", HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD("不合法的密码。", HttpStatus.BAD_REQUEST),
        INVALID_EMAIL("不合法的电子邮箱地址。", HttpStatus.BAD_REQUEST),
        INVALID_TELEPHONE("不合法的电话号码。", HttpStatus.BAD_REQUEST),
        EMPTY_USERNAME("用户名为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PASSWORD("密码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_EMAIL("电子邮箱地址为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TELEPHONE("电话号码为空。", HttpStatus.BAD_REQUEST),
        EMAIL_OCCUPIED("该电子邮箱地址已被使用。", HttpStatus.BAD_REQUEST),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND),
        INCORRECT_PASSWORD("密码不正确。", HttpStatus.UNAUTHORIZED)
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

    public UserServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this.exceptionTypeEnum = exceptionTypeEnum;
    }

}
