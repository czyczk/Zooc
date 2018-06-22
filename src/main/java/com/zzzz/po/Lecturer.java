package com.zzzz.po;

public class Lecturer {
    private long lecturerId;
    private String name;
    private String photoUrl;
    private String introduction;
    private long branchId;

    public long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(long lecturerId) {
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

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lecturer{");
        sb.append("lecturerId=").append(lecturerId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", photoUrl='").append(photoUrl).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", branchId=").append(branchId);
        sb.append('}');
        return sb.toString();
    }
}
