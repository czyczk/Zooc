package com.zzzz.po;

public class Administrator {
    private Long administratorId;
    private String username;
    private String password;
    private AdministratorTypeEnum type;

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdministratorTypeEnum getType() {
        return type;
    }

    public void setType(AdministratorTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Administrator{");
        sb.append("administratorId=").append(administratorId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
