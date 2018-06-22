package com.zzzz.po;

public enum CourseStatusEnum {
    AVAILABLE("可用"),
    OFF("已下线"),
    IN_REVIEW("正在审核");

    private final String desc;

    CourseStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
