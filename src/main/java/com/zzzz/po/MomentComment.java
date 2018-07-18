package com.zzzz.po;

import java.util.Date;

public class MomentComment {
    private long momentCommentId;
    private long momentId;
    private long userId;
    private String content;
    private Date time;

    public long getMomentCommentId() {
        return momentCommentId;
    }

    public void setMomentCommentId(long momentCommentId) {
        this.momentCommentId = momentCommentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
