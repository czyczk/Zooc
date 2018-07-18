package com.zzzz.vo;

import java.util.Date;

public class MomentCommentDetail {
    private long momentCommentId;
    private long userId;
    private String username;
    private String userEmail;
    private String userMobile;
    private String content;
    private Date time;

    public long getMomentCommentId() {
        return momentCommentId;
    }

    public void setMomentCommentId(long momentCommentId) {
        this.momentCommentId = momentCommentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
