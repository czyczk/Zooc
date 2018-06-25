package com.zzzz.po;

public class Lecturer {
    private Long lecturerId;
    private String name;
    private String photoUrl;
    private String introduction;
    private Long enterpriseId;

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lecturer{");
        sb.append("lecturerId=").append(lecturerId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", photoUrl='").append(photoUrl).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", enterpriseId=").append(enterpriseId);
        sb.append('}');
        return sb.toString();
    }
}
