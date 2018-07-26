package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class MomentImgServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_MOMENT_ID("朋友圈编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_IMG_URLS("图片 URL 列表为空。", HttpStatus.BAD_REQUEST),
        INVALID_MOMENT_ID("朋友圈编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_IMG_URLS("图片 URL 列表不合法，可能因为其内包含不合法的 URL 或数量超过上限。", HttpStatus.BAD_REQUEST),
        MOMENT_NOT_EXISTING("该朋友圈不存在。", HttpStatus.NOT_FOUND)
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

    public MomentImgServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public MomentImgServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
