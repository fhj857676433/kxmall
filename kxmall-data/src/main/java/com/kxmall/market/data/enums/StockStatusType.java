package com.kxmall.market.data.enums;

/**
 * 商品销售状态，枚举
 * @author kaixin
 */
public enum StockStatusType {
    /**
     *  下架
     */
    TAKEOFF(0, "下架"),
    /**
     *  在售
     */
    TAKEON(1, "在售");

    private int code;

    private String msg;

    StockStatusType(int code, String msg) {
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
