package com.zzzz.service;

public class UserServiceException extends Exception {
    public static final String INTERNAL_ERROR = "服务器内部错误。";
    public static final String INVALID_USERNAME = "不合法的用户名。";
    public static final String INVALID_PASSWORD = "不合法的密码。";
    public static final String INVALID_EMAIL = "不合法的电子邮箱地址。";
    public static final String INVALID_TELEPHONE = "不合法的电话号码。";
    public static final String EMPTY_USERNAME = "用户名为空。";
    public static final String EMPTY_PASSWORD = "密码为空。";
    public static final String EMPTY_EMAIL = "电子邮箱地址为空。";
    public static final String EMPTY_TELEPHONE = "电话号码为空。";
    public static final String EMAIL_OCCUPIED = "该电子邮箱地址已被使用。";
    public static final String USER_NOT_EXISTING = "该用户不存在。";
    public static final String INCORRECT_PASSWORD = "密码不正确。";

    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
