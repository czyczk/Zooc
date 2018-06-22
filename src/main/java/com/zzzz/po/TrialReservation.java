package com.zzzz.po;

import java.util.Date;

public class TrialReservation {
    private long reservationId;
    private long userId;
    private long trialId;
    private Date time;
    private String message;
    private TrialReservationStatusEnum status;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTrialId() {
        return trialId;
    }

    public void setTrialId(long trialId) {
        this.trialId = trialId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TrialReservationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TrialReservationStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TrialReservation{");
        sb.append("reservationId=").append(reservationId);
        sb.append(", userId=").append(userId);
        sb.append(", trialId=").append(trialId);
        sb.append(", time=").append(time);
        sb.append(", message='").append(message).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
