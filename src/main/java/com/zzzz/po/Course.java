package com.zzzz.po;

import java.math.BigDecimal;
import java.util.Date;

public class Course {
    private long courseId;
    private String name;
    private String detail;
    private String imgUrl;
    private long categoryId;
    private Date releaseTime;
    private BigDecimal price;
    private CourseStatusEnum status;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CourseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CourseStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Course{");
        sb.append("courseId=").append(courseId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", imgUrl='").append(imgUrl).append('\'');
        sb.append(", categoryId=").append(categoryId);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
