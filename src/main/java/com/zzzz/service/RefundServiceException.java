package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class RefundServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_REFUND_ID("退款编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_ORDER_ID("订单编号为空。", HttpStatus.BAD_REQUEST),
        EMPTY_TIME("退款时间为空。", HttpStatus.BAD_REQUEST),
        EMPTY_REASON("原因为空。", HttpStatus.BAD_REQUEST),
        INVALID_REFUND_ID("退款编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_ORDER_ID("订单编号不合法。", HttpStatus.BAD_REQUEST),
        INVALID_TIME("退款时间不合法。", HttpStatus.BAD_REQUEST),
        UNABLE_TO_APPLY_FOR_REFUND("无法申请退款。可能因为已经申请退款、订单未付款或订单已取消。", HttpStatus.BAD_REQUEST),
        UNABLE_TO_CANCEL_REFUND("无法取消退款。可能因为该订单已退款或未申请退款。", HttpStatus.BAD_REQUEST),
        REFUND_NOT_EXISTING("该退款不存在。", HttpStatus.NOT_FOUND),
        ORDER_NOT_EXISTING("该订单不存在。", HttpStatus.NOT_FOUND)
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

    public RefundServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public RefundServiceException(ExceptionTypeEnum exceptionTypeEnum) {
        this(exceptionTypeEnum.message, exceptionTypeEnum.status);
    }
}
