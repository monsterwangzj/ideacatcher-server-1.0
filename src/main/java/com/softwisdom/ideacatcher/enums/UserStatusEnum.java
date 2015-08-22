package com.softwisdom.ideacatcher.enums;

public enum UserStatusEnum {
    USER_STATUS_NORMAL(0, "正常"),
    USER_STATUS_FREEZE(-1, "冻结");

    private final int code;
    private final String description;

    UserStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserStatusEnum valueOf(int code) {
        if (code == USER_STATUS_NORMAL.code) {
            return USER_STATUS_NORMAL;
        } else if (code == USER_STATUS_FREEZE.code) {
            return USER_STATUS_FREEZE;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

}
