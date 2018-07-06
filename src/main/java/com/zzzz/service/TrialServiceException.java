package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class TrialServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TRIAL_ID("试听编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_NAME("名称为空。", HttpStatus.BAD_REQUEST),
        EMPTY_DETAIL("详情为空。", HttpStatus.BAD_REQUEST),
        EMPTY_IMG_URL("图片 URL 为空。", HttpStatus.BAD_REQUEST),
        EMPTY_CATEGORY_ID("课程类型编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_BRANCH_ID("分部编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_LECTURER_ID("讲师编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_RELEASE_TIME("发布时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_STATUS("状态为空。", HttpStatus.BAD_REQUEST),
        INVALID_TRIAL_ID("试听编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_CATEGORY_ID("课程类型编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_BRANCH_ID("分部编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_LECTURER_ID("讲师编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_RELEASE_TIME("发布时间不合法。", HttpStatus.BAD_REQUEST),
        INVALID_STATUS("状态不合法。", HttpStatus.BAD_REQUEST),
        TRIAL_NOT_EXISTING("该试听不存在。", HttpStatus.NOT_FOUND),
        CATEGORY_NOT_EXISTING("该课程分类不存在。", HttpStatus.NOT_FOUND),
        BRANCH_NOT_EXISTING("该分部不存在。", HttpStatus.NOT_FOUND),
        LECTURER_NOT_EXISTING("该讲师不存在。", HttpStatus.NOT_FOUND)
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

    public TrialServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public TrialServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
