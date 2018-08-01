package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class AdministratorServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_USERNAME("用户名为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PASSWORD("密码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ADMINISTRATOR_ID("管理员编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_ADMINISTRATOR_ID("管理员编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        ADMINISTRATOR_NOT_EXISTING("该管理员不存在。", HttpStatus.NOT_FOUND),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND),
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

    public AdministratorServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public AdministratorServiceException(ExceptionTypeEnum typeEnum) {
        super(typeEnum.getMessage(), typeEnum.getStatus());
    }
}
