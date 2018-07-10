package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CourseCategoryServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_COURSE_CATEGORY_NAME("课程分类名称为空。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_CATEGORY_ID("课程分类编号不合法。", HttpStatus.BAD_REQUEST),
        COURSE_CATEGORY_NAME_OCCUPIED("课程分类名称已被使用。", HttpStatus.BAD_REQUEST),
        COURSE_CATEGORY_NOT_EXISTING("该课程分类不存在。", HttpStatus.NOT_FOUND)
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

    public CourseCategoryServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CourseCategoryServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
