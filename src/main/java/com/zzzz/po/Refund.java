package com.zzzz.po;

import java.util.Date;

public class Refund {
    private long refundId;
    private long orderId;
    private Date time;
    private String reason;

    public long getRefundId() {
        return refundId;
    }

    public void setRefundId(long refundId) {
        this.refundId = refundId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Refund{");
        sb.append("refundId=").append(refundId);
        sb.append(", orderId=").append(orderId);
        sb.append(", time=").append(time);
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
