package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class BranchServiceException extends Exception {
    public enum ExceptionTypeEnum {
        INTERNAL_ERROR("内部错误。", HttpStatus.INTERNAL_SERVER_ERROR),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_BRANCH_ID("分部编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_NAME("名称为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ADDRESS("地址为空。", HttpStatus.BAD_REQUEST),
        EMPTY_LATITUDE("纬度为空。", HttpStatus.BAD_REQUEST),
        EMPTY_LONGITUDE("经度为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TELEPHONE("电话为空。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_BRANCH_ID("分部编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TELEPHONE("电话不合法。", HttpStatus.BAD_REQUEST),
        INVALID_LATITUDE("纬度不合法。", HttpStatus.BAD_REQUEST),
        INVALID_LONGITUDE("经度不合法。", HttpStatus.BAD_REQUEST),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.BAD_REQUEST),
        BRANCH_NOT_EXISTING("该分部不存在。", HttpStatus.NOT_FOUND)
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

    public BranchServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this.exceptionTypeEnum = exceptionTypeEnum;
    }
}
