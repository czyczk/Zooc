package com.zzzz.dto;

public class CourseOffering {
    private long courseOfferingId;
    private long courseId;
    private String courseName;
    private long branchId;
    private String branchName;
    private long lecturerId;
    private String lecturerName;

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CourseOffering{");
        sb.append("courseOfferingId=").append(courseOfferingId);
        sb.append(", courseId=").append(courseId);
        sb.append(", courseName='").append(courseName).append('\'');
        sb.append(", branchId=").append(branchId);
        sb.append(", branchName='").append(branchName).append('\'');
        sb.append(", lecturerId=").append(lecturerId);
        sb.append(", lecturerName='").append(lecturerName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
