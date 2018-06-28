package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class EnterpriseServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_ADMINISTRATOR_ID("管理员编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_NAME("名称为空。", HttpStatus.BAD_REQUEST),
        EMPTY_IMG_URL("图片 URL 为空。", HttpStatus.BAD_REQUEST),
        EMPTY_INTRODUCTION("简介为空。", HttpStatus.BAD_REQUEST),
        EMPTY_VIDEO_URL("视频 URL 为空。", HttpStatus.BAD_REQUEST),
        EMPTY_DETAIL("详情为空。", HttpStatus.BAD_REQUEST),
        INVALID_ADMINISTRATOR_ID("管理员编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        ADMINISTRATOR_NOT_EXISTING("该管理员不存在。", HttpStatus.NOT_FOUND),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND),
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

    public EnterpriseServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public EnterpriseServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
