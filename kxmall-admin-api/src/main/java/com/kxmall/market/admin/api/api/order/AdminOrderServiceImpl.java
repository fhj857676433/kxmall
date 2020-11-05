package com.kxmall.market.admin.api.api.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.kxmall.market.biz.service.order.OrderBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.ratelimit.annotation.RateLimiter;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.OrderSkuDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.domain.UserDO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.enums.OrderStatusType;
import com.kxmall.market.data.enums.UserLoginType;
import com.kxmall.market.data.mapper.OrderMapper;
import com.kxmall.market.data.mapper.OrderSkuMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import com.kxmall.market.data.mapper.UserMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/7/10.
 */
@Service
@SuppressWarnings("all")
public class AdminOrderServiceImpl implements AdminOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    private static final String ORDER_STATUS_LOCK = "ORDER_STATUS_LOCK_";

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    IOrderMessageService iOrderMessageService;

    @Autowired
    StorageMapper storageMapper;

    @Value("${com.kxmall.market.wx.mini.app-id}")
    private String wxMiNiAppid;

    @Value("${com.kxmall.market.wx.app.app-id}")
    private String wxAppAppid;

    @Override
    public Page<OrderDO> list(Integer pageNo, Integer pageSize, Integer status, String orderNo, Long adminId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>();
        wrapper.orderBy("id", false);
        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        List<OrderDO> orderDOS = orderMapper.selectPage(new RowBounds((pageNo - 1) * pageSize, pageSize), wrapper);
        Integer count = orderMapper.selectCount(wrapper);
        return new Page<OrderDO>(orderDOS, pageNo, pageSize, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Integer type, Long adminId) throws ServiceException {
        if (lockComponent.tryLock(OrderBizService.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于退款中
                OrderDO orderDO = orderBizService.checkOrderExist(orderNo, null);
                if (orderDO.getStatus() != OrderStatusType.REFUNDING.getCode()) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
                }
                //2.退款处理
                if (type == 0) {
                    //2.1 店主拒绝退款
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
                    updateOrderDO.setGmtUpdate(new Date());
                    orderBizService.changeOrderStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    return "ok";
                } else if (type == 1) {
                    //2.2 店主同意退款
                    //2.2.1 先流转状态
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                    updateOrderDO.setGmtUpdate(new Date());
                    orderBizService.changeOrderStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    Long userId = orderDO.getUserId();
                    UserDO userDO = userMapper.selectById(userId);
                    Integer loginType = userDO.getLoginType();
                    //2.2.2 向微信支付平台发送退款请求
                    WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                    wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? wxMiNiAppid : wxAppAppid);
                    wxPayRefundRequest.setOutTradeNo(orderNo);
                    wxPayRefundRequest.setOutRefundNo("refund_" + orderNo);
                    wxPayRefundRequest.setTotalFee(orderDO.getPayPrice() - orderDO.getFreightPrice());
                    wxPayRefundRequest.setRefundFee(orderDO.getPayPrice() - orderDO.getFreightPrice());
                    WxPayRefundResult wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
                    if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    return "ok";
                } else {
                    throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[微信退款] 异常", e);
                throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(OrderBizService.ORDER_REFUND_LOCK + orderNo);
            }
        } else {
            throw new AdminServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ship(String orderNo, String shipCode, String shipNo, Long adminId) throws ServiceException {
        orderBizService.checkOrderExist(orderNo, null);
        OrderDO updateOrderDO = new OrderDO();
        Date now = new Date();
        updateOrderDO.setGmtUpdate(now);
        updateOrderDO.setGmtShip(now);
        updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
        if (!"NONE".equals(shipCode)) {
            updateOrderDO.setShipCode(shipCode);
            updateOrderDO.setShipNo(shipNo);
        }
        //流转订单状态
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.WAIT_STOCK.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public OrderDTO detail(Long orderId, Long adminId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, null);
    }

    @Override
    public List<OrderDTO> queryToExcel(Long gmtStart, Long gmtEnd, Integer status, Long adminId) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        if (gmtStart != null && gmtEnd != null) {
            if (gmtStart > gmtStart) {
                throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
            }
            wrapper.between("gmt_create", new Date(gmtStart), new Date(gmtEnd));
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (orderDOS == null || orderDOS.size() == 0) {
            return null;
        }
        OrderDTO orderDTO;
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderDO temp : orderDOS) {
            orderDTO = new OrderDTO();
            BeanUtils.copyProperties(temp, orderDTO);
            orderDTOList.add(orderDTO);
        }
        for (OrderDTO orderDTONew : orderDTOList) {
            orderDTONew.setSkuList(orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_no", orderDTONew.getOrderNo())));
        }
        return orderDTOList;
    }

    @Override
    public Page<OrderDTO> listByStatus(Integer status, Integer storageId, String orderNo, Long gmtStart, Long gmtEnd,
                                       Integer pageNo, Integer pageSize, Integer storeId, String postman, Long adminId) throws ServiceException {
        List<Integer> staList = new ArrayList<>();
        if (status != null) {
            staList.add(status);
            if (status == OrderStatusType.COMPLETE.getCode()) {
                //已完成和待评价都表示已完成
                staList.add(OrderStatusType.WAIT_APPRAISE.getCode());
            } else if (status == OrderStatusType.CANCELED.getCode()) {
                //取消包括退款和未支付订单取消
                staList.add(OrderStatusType.REFUNDED.getCode());
                staList.add(OrderStatusType.REFUNDING.getCode());
            }
        }
        EntityWrapper wrapper = new EntityWrapper();
        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (storeId != null) {
            wrapper.eq("store_id", storeId);
        }
        if (postman != null) {
            wrapper.eq("postman", postman);
        }
        if (gmtStart != null && gmtEnd != null) {
            if (gmtStart > gmtStart) {
                throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
            }
            wrapper.between("gmt_create", new Date(gmtStart), new Date(gmtEnd));
        }
        // 按预计送达时间 倒叙排序
        wrapper.orderBy("predict_time", true);
        wrapper.in("status", staList);
        List<OrderDO> orderDOList = orderMapper.selectPage(new RowBounds((pageNo - 1) * pageSize, pageSize), wrapper);
        Integer count = orderMapper.selectCount(wrapper);
        OrderDTO orderDTO;
        List<OrderDTO> orderDTOList = new LinkedList<>();
        for (OrderDO order : orderDOList) {
            orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTOList.add(orderDTO);
        }
        return new Page<>(orderDTOList, pageNo, pageSize, count);
    }

    @Override
    public Boolean updateOrderStatus(String orderNo, Integer status) throws Exception {
        try {
            if (lockComponent.tryLock(ORDER_STATUS_LOCK + orderNo, 30)) {
                OrderDO orderDO = new OrderDO();
                orderDO.setOrderNo(orderNo);
                orderDO.setStatus(status);
                return orderMapper.update(orderDO, new EntityWrapper<OrderDO>().eq("order_no", orderDO.getOrderNo())) > 0;
            } else {
                throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            }
        } catch (Exception e) {
            logger.error("[订单状态扭转] 异常", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(ORDER_STATUS_LOCK + orderNo);
        }
    }

    @Override
    /**
     * 1、更新骑手端、 2、更新订单状态
     */
    @RateLimiter(value = 1, timeout = 4, timeUnit = TimeUnit.SECONDS)
    @Transactional(rollbackFor = Exception.class)
    public Boolean distributeOrder(OrderDTO orderDTO, Long riderId) throws AppServiceException {
        boolean result = false;
        try {
            String orderNo = orderDTO.getOrderNo();
            List<OrderDO> orderDOList = orderMapper.selectList(new EntityWrapper<OrderDO>().eq("order_no", orderNo));
            if (CollectionUtils.isEmpty(orderDOList)) {
                throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
            }
            OrderDO orderDO = orderDOList.get(0);
            List<OrderSkuDO> orderSkuDoList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_no", orderNo));
            if (CollectionUtils.isEmpty(orderSkuDoList)) {
                throw new AppServiceException(ExceptionDefinition.ORDER_SKU_NOT_EXIST);
            }
            Long orderDOStoreId = orderDO.getStoreId();
            StorageDO storageDO = storageMapper.selectStorageById(orderDOStoreId);
            if (storageDO == null) {
                throw new AppServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
            }
            // 更新订单配送员信息
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setPostId(riderId.intValue());
            if (orderMapper.update(updateOrderDO, new EntityWrapper<OrderDO>().eq("order_no", orderDTO.getOrderNo())) > 0) {
                // 发送订单配送信息给骑手端
                result = true;//iOrderMessageService.sendOrderMessageToRiderSerivce(orderMessageBO);
            }
            if (!result) {
                throw new AppServiceException(ExceptionDefinition.ORDER_DISTRIBUTION_FAIL);
            }
            return result;
        } catch (AppServiceException e) {
            e.printStackTrace();
            logger.error("【订单分配异常】 ", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_DISTRIBUTION_FAIL);
        }
    }

}
