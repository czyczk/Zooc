package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CourseServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COURSE_ID("课程编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_NAME("名称为空。", HttpStatus.BAD_REQUEST),
        EMPTY_DETAIL("详情为空。", HttpStatus.BAD_REQUEST),
        EMPTY_IMG_URL("图片 URL 为空。", HttpStatus.BAD_REQUEST),
        EMPTY_CATEGORY_ID("课程类别编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_RELEASE_TIME("发布时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PRICE("价格为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_ID("课程编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_CATEGORY_ID("课程类别编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_RELEASE_TIME("发布时间不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PRICE("价格不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PRICE_RANGE("价格范围不合法。", HttpStatus.BAD_REQUEST),
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

    public CourseServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CourseServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
