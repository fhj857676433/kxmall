package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @description: 配送员订单信息
 * @author: fy
 * @date: 2020/03/01 19:16
 **/
@Data
@TableName("rider_order")
public class RiderOrderDO extends SuperDO {

    // 配送订单状态
    private Integer status;

    // 配送订单状态附加的异常状态
    private Integer abnormal;

    // 配送订单异常原因
    private String reason;

    // 配送员主键
    @TableField("rider_id")
    private Long riderId;

    /*** ============用户信息==============*/

    @TableField("user_id")
    private Long userId;

    // 收货人
    private String consignee;

    // 电话
    private String phone;

    // 省
    private String province;

    // 市
    private String city;

    // 区（县）
    private String county;

    // 地址
    private String address;

    // 备注
    private String mono;

    /*** ============仓库信息==================*/

    //仓库主键ID
    @TableField("store_id")
    private Integer storeId;

    // 仓库名称
    @TableField("store_name")
    private String storeName;

    /*** ==============订单信息===============*/
    // 订单编号
    @TableField("order_no")
    private String orderNo;

    //商品总价
    @TableField("total_price")
    private Double totalPrice;

    //优惠总价
    @TableField("total_preferential_price")
    private Double totalPreferentialPrice;

    // 配送费
    @TableField("freight_price")
    private Double freightPrice;

    // 支付方式
    @TableField("pay_channel")
    private String payChannel;

    // 支付时间
    @TableField("gmt_pay")
    private Date gmtPay;

    // 下单时间
    @TableField("gmt_order_create")
    private Date gmtOrderCreate;

    // 预计送达时间
    @TableField("predict_time")
    private Date predictTime;

}
