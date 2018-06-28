package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class UserServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USERNAME("用户名为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PASSWORD("密码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_EMAIL("电子邮箱地址为空。", HttpStatus.BAD_REQUEST),
        EMPTY_MOBILE("手机号码为空。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("不合法的用户编号。", HttpStatus.BAD_REQUEST),
        INVALID_USERNAME("不合法的用户名。", HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD("不合法的密码。", HttpStatus.BAD_REQUEST),
        INVALID_EMAIL("不合法的电子邮箱地址。", HttpStatus.BAD_REQUEST),
        INVALID_MOBILE("不合法的手机号码。", HttpStatus.BAD_REQUEST),
        MISSING_IDENTIFIER("请提供电子邮箱地址或手机号码（至少其一）作为登录凭证。", HttpStatus.BAD_REQUEST),
        EMAIL_OCCUPIED("该电子邮箱地址已被使用。", HttpStatus.BAD_REQUEST),
        MOBILE_OCCUPIED("该手机号码已被使用。", HttpStatus.BAD_REQUEST),
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

    public UserServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public UserServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }

}
