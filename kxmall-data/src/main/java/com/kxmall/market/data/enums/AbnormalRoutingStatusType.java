package com.kxmall.market.data.enums;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/11 18:35
 **/
public enum  AbnormalRoutingStatusType {

    IMPLEMENT(1, "执行"),
    NON_EXECUTION(0, "不执行");

    private int code;

    private String msg;

    AbnormalRoutingStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }
}
