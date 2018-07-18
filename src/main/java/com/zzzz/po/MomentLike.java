package com.zzzz.po;

import java.util.Date;

public class MomentLike {
    private long momentLikeId;
    private long momentId;
    private long userId;
    private Date time;

    public long getMomentLikeId() {
        return momentLikeId;
    }

    public void setMomentLikeId(long momentLikeId) {
        this.momentLikeId = momentLikeId;
    }

    public long getMomentId() {
        return momentId;
    }

    public void setMomentId(long momentId) {
        this.momentId = momentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
