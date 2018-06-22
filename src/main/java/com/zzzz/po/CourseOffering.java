package com.zzzz.po;

public class CourseOffering {
    private long courseOfferingId;
    private long courseId;
    private long branchId;
    private long lecturerId;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CourseOffering{");
        sb.append("courseOfferingId=").append(courseOfferingId);
        sb.append(", courseId=").append(courseId);
        sb.append(", branchId=").append(branchId);
        sb.append(", lecturerId=").append(lecturerId);
        sb.append('}');
        return sb.toString();
    }
}
