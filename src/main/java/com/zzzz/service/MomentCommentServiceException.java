package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class MomentCommentServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_MOMENT_COMMENT_ID("朋友圈评论编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_MOMENT_ID("朋友圈编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_USER_ID("用户编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_CONTENT("评论内容为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("发布时间为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_MOMENT_COMMENT_ID("朋友圈评论编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_MOMENT_ID("朋友圈编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_USER_ID("用户编号不合法。", HttpStatus.BAD_REQUEST),
        MOMENT_COMMENT_NOT_EXISTING("该朋友圈评论不存在。", HttpStatus.NOT_FOUND),
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

    public MomentCommentServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public MomentCommentServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
