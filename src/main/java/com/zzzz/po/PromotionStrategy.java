package com.zzzz.po;

public class PromotionStrategy {
    private long enterpriseId;
    private boolean useCoupon;
    private boolean usePoint;
    private int pointsPerYuan;

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public boolean isUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(boolean useCoupon) {
        this.useCoupon = useCoupon;
    }

    public boolean isUsePoint() {
        return usePoint;
    }

    public void setUsePoint(boolean usePoint) {
        this.usePoint = usePoint;
    }

    public int getPointsPerYuan() {
        return pointsPerYuan;
    }

    public void setPointsPerYuan(int pointsPerYuan) {
        this.pointsPerYuan = pointsPerYuan;
    }
}
