package com.zzzz.dto;

import java.util.Date;
import java.util.List;

public class Moment {
    private long momentId;
    private long enterpriseId;
    private String content;
    private Date time;
    private List<String> imgUrls;
    private List<MomentLike> likes;
    private List<MomentComment> comments;

    public long getMomentId() {
        return momentId;
    }

    public void setMomentId(long momentId) {
        this.momentId = momentId;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
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

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<MomentLike> getLikes() {
        return likes;
    }

    public void setLikes(List<MomentLike> likes) {
        this.likes = likes;
    }

    public List<MomentComment> getComments() {
        return comments;
    }

    public void setComments(List<MomentComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Moment{");
        sb.append("momentId=").append(momentId);
        sb.append(", enterpriseId=").append(enterpriseId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", time=").append(time);
        sb.append(", imgUrlsCount=").append(imgUrls == null ? 0 : imgUrls.size());
        sb.append(", likesCount=").append(likes == null ? 0 : likes.size());
        sb.append(", commentsCount=").append(comments == null ? 0 : comments.size());
        sb.append('}');
        return sb.toString();
    }
}
