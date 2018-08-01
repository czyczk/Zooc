package com.zzzz.po;

public class PromotionStrategy {
    private long enterpriseId;
    private boolean useCoupons;
    private boolean usePoints;
    private int pointsPerYuan;

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public boolean isUseCoupons() {
        return useCoupons;
    }

    public void setUseCoupons(boolean useCoupons) {
        this.useCoupons = useCoupons;
    }

    public boolean isUsePoints() {
        return usePoints;
    }

    public void setUsePoints(boolean usePoints) {
        this.usePoints = usePoints;
    }

    public int getPointsPerYuan() {
        return pointsPerYuan;
    }

    public void setPointsPerYuan(int pointsPerYuan) {
        this.pointsPerYuan = pointsPerYuan;
    }
}
