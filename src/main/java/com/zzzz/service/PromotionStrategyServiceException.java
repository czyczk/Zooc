package com.zzzz.service;

import org.springframework.http.HttpStatus;

public class PromotionStrategyServiceException extends ServiceException {
    public enum ExceptionTypeEnum {
        EMPTY_ENTERPRISE_ID("企业编号为空。", HttpStatus.BAD_REQUEST),
        INVALID_ENTERPRISE_ID("企业编号不合法。", HttpStatus.BAD_REQUEST),
//        INVALID_STATUS_OF_USE_COUPONS("是否使用优惠券状态不合法。", HttpStatus.BAD_REQUEST),
//        INVALID_STATUS_OF_USE_POINTS("是否使用积分与签到状态不合法。", HttpStatus.BAD_REQUEST),
        INVALID_POINTS_PER_YUAN("每元所需积分不合法。", HttpStatus.BAD_REQUEST),
        COUPON_STRATEGY_CANNOT_BE_DISABLED("优惠券策略已开启，无法关闭。", HttpStatus.BAD_REQUEST),
        POINT_STRATEGY_CANNOT_BE_DISABLED("积分策略已开启，无法关闭。", HttpStatus.BAD_REQUEST),
        ENTERPRISE_NOT_EXISTING("该企业不存在。", HttpStatus.NOT_FOUND)
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

    public PromotionStrategyServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public PromotionStrategyServiceException(ExceptionTypeEnum typeEnum) {
        super(typeEnum.getMessage(), typeEnum.getStatus());
    }
}
