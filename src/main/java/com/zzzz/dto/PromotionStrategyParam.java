package com.zzzz.dto;

public class PromotionStrategyParam {
    private String enterpriseId;
    private String useCoupons;
    private String usePoints;
    private String pointsPerYuan;
    private String checkinPoints;

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getUseCoupons() {
        return useCoupons;
    }

    public void setUseCoupons(String useCoupons) {
        this.useCoupons = useCoupons;
    }

    public String getUsePoints() {
        return usePoints;
    }

    public void setUsePoints(String usePoints) {
        this.usePoints = usePoints;
    }

    public String getPointsPerYuan() {
        return pointsPerYuan;
    }

    public void setPointsPerYuan(String pointsPerYuan) {
        this.pointsPerYuan = pointsPerYuan;
    }

    public String getCheckinPoints() {
        return checkinPoints;
    }

    public void setCheckinPoints(String checkinPoints) {
        this.checkinPoints = checkinPoints;
    }
}
