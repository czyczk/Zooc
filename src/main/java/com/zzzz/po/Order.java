package com.zzzz.po;

import java.util.Date;

public class Order {
    private long orderId;
    private long userId;
    private long courseId;
    private Date time;
    private OrderStatusEnum status;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", courseId=").append(courseId);
        sb.append(", time=").append(time);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
