package com.zzzz.po;

import java.util.Date;

/**
 * A CheckinRecord is produced after a user checks in in an enterprise page.
 */
public class CheckinRecord {
    // The user ID
    private long userId;
    // The enterprise ID
    private long enterpriseId;
    // The date in which the user checked in
    private Date date;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
