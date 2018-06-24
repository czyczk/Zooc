package com.zzzz.po;

public enum AdministratorTypeEnum {
    SYSTEM("系统"),
    ENTERPRISE("企业");

    private final String desc;

    AdministratorTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
