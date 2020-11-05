package com.kxmall.market.data.dto.order;

import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.OrderSkuDO;
import com.kxmall.market.data.dto.SuperDTO;
import com.kxmall.market.data.dto.freight.ShipTraceDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2019/7/6.
 */
@Data
public class OrderDTO extends SuperDTO {

    /**
     * 用户下单渠道
     */
    private String channel;

    private String orderNo;

    private Long userId;

    private Long storeId;

    private Integer status;

    private Integer skuOriginalTotalPrice;
    /**
     * 商品总价
     */
    private Integer skuTotalPrice;

    private Integer freightPrice;

    private Integer couponPrice;

    private Long couponId;

    /**
     * 计算优惠后，实际需要支付的价格
     */
    private Integer actualPrice;

    /**
     * 用户支付价格
     */
    private Integer payPrice;

    /**
     * 支付流水号 (第三方)
     */
    private String payId;

    /**
     * 第三方支付渠道
     */
    private String payChannel;

    private Date gmtPay;

    private String shipNo;

    /**
     * 承运商
     */
    private String shipCode;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    private String consignee;

    private String mono;

    private Date gmtShip;

    /**
     * 确实收货时间
     */
    private Date gmtConfirm;

    /**
     * 预计到达时间段
     */
    private String predictTime;

    /**
     * 预计到达时间点
     */
    private Date gmtPredict;

    private List<OrderSkuDO> skuList;

    private List<OrderDO> orderList;

    private ShipTraceDTO shipTraceDTO;

    /**
     * 总条数，便于分页
     */
    private Integer total;

    /**
     * 退款时间
     */
    private String gmtRefund;

    private Integer postId;

    private String exceptionReason;

}
