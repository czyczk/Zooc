package com.zzzz.po;

public class Enterprise {
    private Long enterpriseId;
    private long administratorId;
    private String name;
    private String imgUrl;
    private String introduction;
    private String videoUrl;
    private String detail;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Enterprise{");
        sb.append("enterpriseId=").append(enterpriseId);
        sb.append(", administratorId=").append(administratorId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", imgUrl='").append(imgUrl).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", videoUrl='").append(videoUrl).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
