package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CourseServiceException extends Exception {
    public enum ExceptionTypeEnum {
        INTERNAL_ERROR("内部错误。", HttpStatus.INTERNAL_SERVER_ERROR),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COURSE_ID("课程编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_NAME("名称为空。", HttpStatus.BAD_REQUEST),
        EMPTY_DETAIL("详情为空。", HttpStatus.BAD_REQUEST),
        EMPTY_IMG_URL("图片 URL 为空。", HttpStatus.BAD_REQUEST),
        EMPTY_CATEGORY_ID("课程类别编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_RELEASE_TIME("发布时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PRICE("价格为空。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_ID("课程编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_CATEGORY_ID("课程类别编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_RELEASE_TIME("发布时间不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PRICE("价格不合法。", HttpStatus.BAD_REQUEST),
        INVALID_STATUS("状态不合法。", HttpStatus.BAD_REQUEST),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.BAD_REQUEST),
        CATEGORY_NOT_EXISTING("该课程类别不存在。", HttpStatus.BAD_REQUEST),
        COURSE_NOT_EXISTING("该课程不存在。", HttpStatus.NOT_FOUND)
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

    public CourseServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this.exceptionTypeEnum = exceptionTypeEnum;
    }
}
