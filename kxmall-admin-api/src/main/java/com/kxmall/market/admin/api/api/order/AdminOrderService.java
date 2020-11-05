package com.kxmall.market.admin.api.api.order;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.model.Page;


import java.util.List;

/**
 * Created by admin on 2019/7/10.
 */
@HttpOpenApi(group = "admin.order", description = "管理员订单服务")
public interface AdminOrderService {

    @HttpMethod(description = "列表", permission = "operation:order:list", permissionParentName = "运营管理", permissionName = "订单管理")
    public Page<OrderDO> list(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "订单页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") Integer status,
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "退款", permission = "operation:order:refund", permissionParentName = "运营管理", permissionName = "订单管理")
    public String refund(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "0.拒绝退款 1.同意退款") Integer type,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "发货", permission = "operation:order:ship", permissionParentName = "运营管理", permissionName = "订单管理")
    public String ship(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "shipCode", type = HttpParamType.COMMON, description = "承运商Code") String shipCode,
            @HttpParam(name = "shipNo", type = HttpParamType.COMMON, description = "运单号") String shipNo,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "operation:order:detail", permissionParentName = "运营管理", permissionName = "订单管理")
    public OrderDTO detail(
            @NotNull @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单Id") Long orderNo,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "根据传入时间和订单状态查询订单信息")
    public List<OrderDTO> queryToExcel(
            @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "查询开始时间") Long gmtStart,
            @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "查询结束时间") Long gmtEnd,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态", valueDef = "20") Integer status,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "根据订单状态查询订单（配送中、待支付、已支付...）")
    public Page<OrderDTO> listByStatus(
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") Integer status,
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓ID") Integer storageId,
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "要求送货时间起始时间") Long gmtStart,
            @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "要求送货时间终止时间") Long gmtEnd,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit,
            @HttpParam(name = "storeId", type = HttpParamType.COMMON, description = "仓库id") Integer storeId,
            @HttpParam(name = "postman", type = HttpParamType.COMMON, description = "送货人") String postman,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改订单状态")
    public Boolean updateOrderStatus(
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") Integer status) throws Exception;

    @HttpMethod(description = "下发订单")
    public Boolean distributeOrder(
            @HttpParam(name = "orderDTO", type = HttpParamType.COMMON, description = "订单") OrderDTO orderDTO,
            @HttpParam(name = "riderId", type = HttpParamType.COMMON, description = "骑手id") Long riderId) throws AppServiceException;

}
