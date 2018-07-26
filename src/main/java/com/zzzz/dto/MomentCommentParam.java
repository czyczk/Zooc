package com.zzzz.dto;

public class MomentCommentParam {
    private String momentCommentId;
    private String momentId;
    private String userId;
    private String content;

    public String getMomentCommentId() {
        return momentCommentId;
    }

    public void setMomentCommentId(String momentCommentId) {
        this.momentCommentId = momentCommentId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
