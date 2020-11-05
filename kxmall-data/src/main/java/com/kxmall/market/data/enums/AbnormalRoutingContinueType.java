package com.kxmall.market.data.enums;

public enum AbnormalRoutingContinueType {

    CONTINUE(1, "继续"),
    NO_CONTINUE(0, "不继续");

    private int code;

    private String msg;

    AbnormalRoutingContinueType(int code, String msg) {
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
