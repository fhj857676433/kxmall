package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by admin on 2019/7/5.
 */
@Data
@TableName("kxmall_order")
public class OrderDO extends SuperDO {

    /**
     * 用户下单渠道
     */
    private String channel;

    @TableField("order_no")
    private String orderNo;

    @TableField("user_id")
    private Long userId;

    /**
     * 仓库id
     */
    @TableField("store_id")
    private Long storeId;

    private Integer status;

    @TableField("sku_original_total_price")
    private Integer skuOriginalTotalPrice;

    /**
     * 商品总价
     */
    @TableField("sku_total_price")
    private Integer skuTotalPrice;

    /**
     * 运费
     */
    @TableField("freight_price")
    private Integer freightPrice;

    @TableField("coupon_price")
    private Integer couponPrice;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("group_shop_id")
    private Long groupShopId;

    /**
     * 计算优惠后，实际需要支付的价格
     */
    @TableField("actual_price")
    private Integer actualPrice;

    /**
     * 支付加个
     */
    @TableField("pay_price")
    private Integer payPrice;

    /**
     * 支付流水号 (第三方)
     */
    @TableField("pay_id")
    private String payId;

    /**
     * 第三方支付渠道
     */
    @TableField("pay_channel")
    private String payChannel;

    /**
     * 下单时间
     */
    @TableField("gmt_pay")
    private Date gmtPay;

    @TableField("ship_no")
    private String shipNo;

    /**
     * 承运商
     */
    @TableField("ship_code")
    private String shipCode;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 备注
     */
    private String mono;

    @TableField("gmt_ship")
    private Date gmtShip;

    /**
     * 确实收货时间
     */
    @TableField("gmt_confirm")
    private Date gmtConfirm;

    /**
     * 预计到达时间段
     */
    @TableField("predict_time")
    private String predictTime;

    /**
     * 预计到达时间点
     */
    @TableField("gmt_predict")
    private Date gmtPredict;

    /**
     * 送货人
     */
    @TableField("postman")
    private String postman;

    /**
     * 送货人编号
     */
    @TableField("post_id")
    private Integer postId;

    /**
     * 异常原因
     */
    @TableField("exception_reason")
    private String exceptionReason;

    /**
     * 退款时间
     */
    @TableField("gmt_refund")
    private String gmtRefund;

}
