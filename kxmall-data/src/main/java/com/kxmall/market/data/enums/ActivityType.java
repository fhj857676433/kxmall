package com.kxmall.market.data.enums;

/**
 * 活动类型枚举
 *
 * @author kaixin
 */

public enum ActivityType {

    /**
     * 新人注册活动
     */
    NEW_REGISTRATION(1, "新人注册"),
    /**
     *
     */
    INVITE_NEW_PEOPLE(2, "邀请新人活动"),
    ;

    ActivityType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public static ActivityType getBycode(Integer code) {
        for (ActivityType activityType : values()) {
            if (activityType.getCode().equals(code)) {
                return activityType;
            }
        }
        return ActivityType.NEW_REGISTRATION;
    }
}
