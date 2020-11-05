package com.kxmall.market.data.enums;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-20
 * Time: 上午10:34
 */
public enum MerchantShowType {
    GOODS(1, "商品列表"),
    ORDER(2, "点餐列表"),
    ;

    MerchantShowType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
