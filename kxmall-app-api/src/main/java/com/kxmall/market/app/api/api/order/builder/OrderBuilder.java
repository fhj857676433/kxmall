package com.kxmall.market.app.api.api.order.builder;

import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.dto.order.OrderPriceDTO;
import com.kxmall.market.data.dto.order.OrderRequestDTO;

/**
 * @description: 抽象建造者
 * @author: fy
 * @date: 2020/03/13 13:04
 **/
public abstract class OrderBuilder {

    /**
     * 1.订单初始创建校验部分
     */
    public abstract void buildOrderCheckHandlePart(OrderRequestDTO orderRequest, Long userId) throws ServiceException;

    /**
     * 2.订单价格处理部分
     */
    public abstract OrderPriceDTO buildOrderPriceHandlePart(OrderDO orderDO, OrderRequestDTO orderRequest, Long userId) throws ServiceException;

    /**
     * 3.构建订单部分
     */
    public abstract void buildOrderHandlePart(OrderDO orderDO, OrderPriceDTO orderPriceDTO, OrderRequestDTO orderRequest, String channel, Long userId) throws ServiceException;

    /**
     * 4.更新优惠券部分
     */
    public abstract void buildCoupontHandlePart(OrderDO orderDO) throws ServiceException;

    /**
     * 5.订单商品SKU部分
     */
    public abstract void buildOrderSkuHandlePart(OrderDO orderDO, OrderPriceDTO orderPriceDTO, OrderRequestDTO orderRequest);

    /**
     * 6.购物车部分
     */
    public abstract void buildCartHandlePart(OrderRequestDTO orderRequest, Long userId);

    /**
     * 7.触发订单创建完成后通知事件部分
     */
    public abstract void buildNotifyHandlePart(OrderDO orderDO);
}
