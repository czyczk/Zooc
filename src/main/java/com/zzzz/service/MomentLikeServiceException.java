package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class MomentLikeServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_MOMENT_LIKE_ID("朋友圈点赞编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_MOMENT_ID("朋友圈编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("发布时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_LATEST_NUMBER("最新条数为空。", HttpStatus.BAD_REQUEST),
        INVALID_MOMENT_LIKE_ID("朋友圈点赞编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_MOMENT_ID("朋友圈编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_LATEST_NUMBER("最新条数不合法。", HttpStatus.BAD_REQUEST),
        MOMENT_LIKE_NOT_EXISTING("该朋友圈点赞不存在。", HttpStatus.NOT_FOUND),
        MOMENT_NOT_EXISTING("该朋友圈不存在。", HttpStatus.NOT_FOUND),
        USER_NOT_EXISTING("该用户不存在。", HttpStatus.NOT_FOUND)
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

    public MomentLikeServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public MomentLikeServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
