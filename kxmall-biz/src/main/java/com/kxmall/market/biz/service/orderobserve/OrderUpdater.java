package com.kxmall.market.biz.service.orderobserve;

import com.kxmall.market.data.domain.OrderDO;

/**
 * 观察者对象
 *
 * @author kaixin
 */
public class OrderUpdater {

    /**
     * 订单对象
     */
    private OrderDO orderDo;


    /**
     * 默认构造参数
     *
     * @param orderDo 订单对象
     */
    public OrderUpdater(OrderDO orderDo) {
        this.orderDo = orderDo;
    }

    public OrderDO getOrderDo() {
        return orderDo;
    }

    public void setOrderDo(OrderDO orderDo) {
        this.orderDo = orderDo;
    }
}
