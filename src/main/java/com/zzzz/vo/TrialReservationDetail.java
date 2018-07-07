package com.zzzz.vo;

import com.zzzz.po.TrialReservationStatusEnum;

import java.util.Date;

public class TrialReservationDetail {
    private long reservationId;
    private long trialId;
    private String trialName;
    private Date time;
    private String message;
    private TrialReservationStatusEnum status;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getTrialId() {
        return trialId;
    }

    public void setTrialId(long trialId) {
        this.trialId = trialId;
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
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
}
