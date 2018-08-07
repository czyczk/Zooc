package com.zzzz.vo;

import java.math.BigDecimal;

public class OrderPreview {
    private long enterpriseId;
    private long userId;
    private long courseId;
    private String courseName;
    private BigDecimal originalPrice;
    private Long couponId;
    private BigDecimal discountedByCoupon;
    private long totalPoints;
    private Long numPointsUsed;
    private BigDecimal discountedByPoints;
    private BigDecimal actualPayment;

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public void setNumPointsUsed(Long numPointsUsed) {
        this.numPointsUsed = numPointsUsed;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountedByCoupon() {
        return discountedByCoupon;
    }

    public void setDiscountedByCoupon(BigDecimal discountedByCoupon) {
        this.discountedByCoupon = discountedByCoupon;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Long getNumPointsUsed() {
        return numPointsUsed;
    }

    public BigDecimal getDiscountedByPoints() {
        return discountedByPoints;
    }

    public void setDiscountedByPoints(BigDecimal discountedByPoints) {
        this.discountedByPoints = discountedByPoints;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }
}
