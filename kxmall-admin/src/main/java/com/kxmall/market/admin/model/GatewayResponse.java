package com.kxmall.market.admin.model;

import lombok.Data;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-08-12
 * Time: 上午10:35
 * @author Administrator
 */
@Data
public class GatewayResponse {
    private int errno;
    private String errmsg;
    private Object data;
    private long timestamp;
}
