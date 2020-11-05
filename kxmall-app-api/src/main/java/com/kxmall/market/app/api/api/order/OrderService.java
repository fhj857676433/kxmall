package com.kxmall.market.app.api.api.order;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.freight.ShipTraceDTO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.dto.order.OrderRequestDTO;
import com.kxmall.market.data.model.Page;

/**
 * Created by admin on 2019/7/4.
 */
@HttpOpenApi(group = "order", description = "订单服务")
public interface OrderService {

    @HttpMethod(description = "提交订单")
    public String takeOrder(
            @NotNull @HttpParam(name = "orderRequest", type = HttpParamType.COMMON, description = "订单请求实例") OrderRequestDTO orderRequest,
            @NotNull @HttpParam(name = "channel", type = HttpParamType.COMMON, description = "订单提交渠道") String channel,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "userId") Long userId) throws ServiceException;

    @HttpMethod(description = "获取订单分页")
    public Page<OrderDTO> getOrderPage(
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") String status,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取订单详情")
    public OrderDTO getOrderDetail(
            @NotNull @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单号") Long orderId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "微信小程序预先支付")
    public Object wxPrepay(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "ip地址") String ip,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "线下支付")
    public Object offlinePrepay(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "用户申请退款")
    public String refund(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "取消订单")
    public String cancel(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "确认订单")
    public String confirm(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "查询物流")
    public ShipTraceDTO queryShip(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
