package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class FdfsServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        FILE_NOT_SPECIFIED("文件未指定。", HttpStatus.BAD_REQUEST)
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

    public FdfsServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public FdfsServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
