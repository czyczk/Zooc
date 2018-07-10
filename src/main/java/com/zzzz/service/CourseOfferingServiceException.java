package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class CourseOfferingServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_TARGET_PAGE("目标页码为空。", HttpStatus.BAD_REQUEST),
        EMPTY_PAGE_SIZE("页大小为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COURSE_OFFERING_ID("课程课堂编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_COURSE_ID("课程编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_BRANCH_ID("分部编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_LECTURER_ID("讲师编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_TARGET_PAGE("目标页码不合法。", HttpStatus.BAD_REQUEST),
        INVALID_PAGE_SIZE("页大小不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_OFFERING_ID("课程课堂编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_COURSE_ID("课程编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_BRANCH_ID("分部编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_LECTURER_ID("讲师编号不合法。", HttpStatus.BAD_REQUEST),
        COURSE_OFFERING_NOT_EXISTING("该课程课堂不存在。", HttpStatus.NOT_FOUND),
        COURSE_NOT_EXISTING("该课程不存在。", HttpStatus.NOT_FOUND),
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

    public CourseOfferingServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CourseOfferingServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
