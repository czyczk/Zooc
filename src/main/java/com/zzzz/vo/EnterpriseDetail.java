package com.zzzz.vo;

import java.util.List;

public class EnterpriseDetail {
    private long enterpriseId;
    private String name;
    private String imgUrl;
    private String introduction;
    private String videoUrl;
    private String detail;
    private List<Branch> branches;
    private List<Lecturer> lecturers;
    private List<Course> courses;

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
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

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Enterprise{");
        sb.append("enterpriseId=").append(enterpriseId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", imgUrl='").append(imgUrl).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", videoUrl='").append(videoUrl).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", branchesCount=").append(branches == null ? 0 : branches.size());
        sb.append(", lecturersCount=").append(lecturers == null ? 0 : lecturers.size());
        sb.append(", coursesCount=").append(courses == null ? 0 : courses.size());
        sb.append('}');
        return sb.toString();
    }
}
