package com.kxmall.market.admin.controller;


import com.kxmall.market.admin.api.api.order.AdminOrderService;
import com.kxmall.market.data.enums.OrderStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Bob
 * @date: 2020/3/6
 * @time: 19:50
 */

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderFileController {

    @Autowired
    private AdminOrderService adminOrderService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportOrderExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //Long orderId = Long.valueOf(request.getParameter("orderId"));
        Long orderNo = Long.valueOf(request.getParameter("orderNo"));

        //  Long adminId = Long.valueOf(request.getParameter("adminId"));

        //更新订单状态
        adminOrderService.updateOrderStatus(orderNo.toString(), OrderStatusType.PREPARING_GOODS.getCode());

//        OrderDTO orderDTO = adminOrderService.detail(orderId,null);
//        List<OrderSkuDO> skuDOList = orderDTO.getSkuList();
//
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("订单详情", "订单", ExcelType.HSSF), OrderSkuDO.class, skuDOList);
//        ExcelUtils.downLoadExcel("订单详情", workbook, request, response);

    }

}
