package com.zzzz.po;

import java.util.Date;

public class Trial {
    private long trialId;
    private String name;
    private String detail;
    private String imgUrl;
    private long categoryId;
    private long branchId;
    private long lecturerId;
    private Date releaseTime;
    private TrialStatusEnum status;

    public long getTrialId() {
        return trialId;
    }

    public void setTrialId(long trialId) {
        this.trialId = trialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public TrialStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TrialStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trial{");
        sb.append("trialId=").append(trialId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", imgUrl='").append(imgUrl).append('\'');
        sb.append(", categoryId=").append(categoryId);
        sb.append(", branchId=").append(branchId);
        sb.append(", lecturerId=").append(lecturerId);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
