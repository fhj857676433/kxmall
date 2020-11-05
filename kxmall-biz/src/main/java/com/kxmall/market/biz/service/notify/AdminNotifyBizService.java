package com.kxmall.market.biz.service.notify;

import com.kxmall.market.data.dto.order.OrderDTO;

/**
 *
 * Description: 管理员通知相关接口，意为：发货、退款审核等需要通知管理员即时处理。此业务不应该影响主流业务，不应该抛异常，并且可以异步执行
 * User: admin
 * Date: 2019/12/27
 * Time: 16:15
 */
public interface AdminNotifyBizService {

    public void newOrder(OrderDTO orderDTO);

    public void refundOrder(OrderDTO orderDTO);

}
