package com.zzzz.po;

import java.math.BigDecimal;

public class Branch {
    private Long branchId;
    private Long enterpriseId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String telephone;
    private boolean isDisabled;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Branch{");
        sb.append("branchId=").append(branchId);
        sb.append(", enterpriseId=").append(enterpriseId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", isDisabled=").append(isDisabled);
        sb.append('}');
        return sb.toString();
    }
}
