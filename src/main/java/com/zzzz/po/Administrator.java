package com.zzzz.po;

public class Administrator {
    private long administratorId;
    private String username;
    private String password;

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Administrator{");
        sb.append("administratorId=").append(administratorId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
