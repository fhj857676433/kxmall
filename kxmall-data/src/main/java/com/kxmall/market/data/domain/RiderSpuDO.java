package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 配送商品信息表
 * @author: fy
 * @date: 2020/03/02 16:42
 **/
@Data
@TableName("rider_spu")
public class RiderSpuDO implements Serializable {

    private Long id;

    // 配送单主键
    @TableField("rider_order_id")
    private Long riderOrderId;

    // 商品名称
    @TableField("spu_name")
    private String spuName;

    // 数量
    @TableField("amount")
    private String amount;

    // 商品URL
    @TableField("url")
    private String url;

    // 单价
    @TableField("unit_price")
    private Double unitPrice;

    // 优惠单价格
    @TableField("preferential_price")
    private Double preferentialPrice;

}
