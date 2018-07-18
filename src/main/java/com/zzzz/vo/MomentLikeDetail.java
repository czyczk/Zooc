package com.zzzz.vo;

import java.util.Date;

public class MomentLikeDetail {
    private long momentLikeId;
    private long userId;
    private String username;
    private String userEmail;
    private String userMobile;
    private Date time;

    public long getMomentLikeId() {
        return momentLikeId;
    }

    public void setMomentLikeId(long momentLikeId) {
        this.momentLikeId = momentLikeId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
