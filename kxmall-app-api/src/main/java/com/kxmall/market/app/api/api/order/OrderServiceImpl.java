package com.kxmall.market.app.api.api.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.kxmall.market.app.api.api.order.builder.OrderBuilder;
import com.kxmall.market.app.api.api.order.builder.OrderDirector;
import com.kxmall.market.biz.service.freight.FreightBizService;
import com.kxmall.market.biz.service.order.OrderBizService;
import com.kxmall.market.biz.service.user.UserBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.exception.ThirdPartServiceException;
import com.kxmall.market.core.ratelimit.annotation.RateLimiter;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.OrderSkuDO;
import com.kxmall.market.data.domain.UserFormIdDO;
import com.kxmall.market.data.dto.UserDTO;
import com.kxmall.market.data.dto.freight.ShipTraceDTO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.dto.order.OrderRequestDTO;
import com.kxmall.market.data.enums.OrderStatusType;
import com.kxmall.market.data.enums.PayChannelType;
import com.kxmall.market.data.enums.UserLoginType;
import com.kxmall.market.data.mapper.OrderMapper;
import com.kxmall.market.data.mapper.OrderSkuMapper;
import com.kxmall.market.data.model.Page;
import com.kxmall.market.data.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by admin on 2019/7/4.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final String TAKE_ORDER_LOCK = "TAKE_ORDER_";

    @Autowired
    private OrderBuilder orderBuilder;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private FreightBizService freightBizService;

    @Autowired
    private UserBizService userBizService;

    @Value("${com.kxmall.market.wx.mini.app-id}")
    private String wxMiNiAppid;

    @Value("${com.kxmall.market.wx.app.app-id}")
    private String wxAppAppid;

    @Value("${com.kxmall.market.wx.h5.app-id}")
    private String wxH5Appid;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RateLimiter(value = 3)
    public String takeOrder(OrderRequestDTO orderRequest, String channel, Long userId) throws ServiceException {
        if (lockComponent.tryLock(TAKE_ORDER_LOCK + userId, 20)) {
            //加上乐观锁，防止用户重复提交订单
            try {
                OrderDO orderDO = new OrderDO();
                OrderDirector orderDirector = new OrderDirector(orderBuilder);
                orderDirector.constructOrder(orderDO, orderRequest, channel, userId);
                return orderDO.getOrderNo();
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[提交订单] 异常", e);
                throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(TAKE_ORDER_LOCK + userId);
            }
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
    }

    @Override
    public Page<OrderDTO> getOrderPage(Integer pageNo, Integer pageSize, String state, Long userId) throws ServiceException {
        List<Integer> status = new ArrayList<>();
        if (state != null) {
            String[] states = state.split(",");
            for (int i = 0; i < states.length; i++) {
                status.add(Integer.parseInt(states[i]));
            }
        }
        List<OrderDTO> orderDTOList = orderMapper.selectOrderPages(status, (pageNo - 1) * pageSize, pageSize, userId);
        Long count = orderMapper.countOrders(status, (pageNo - 1) * pageSize, pageSize, userId);
        //封装SKU
        orderDTOList.forEach(item -> {
            item.setSkuList(orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", item.getId())));
        });
        return new Page<>(orderDTOList, pageNo, pageSize, count);
    }

    @Override
    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object wxPrepay(String orderNo, String ip, Long userId) throws ServiceException {
        Date now = new Date();
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        // 检测订单状态
        Integer status = orderDO.getStatus();
        if (status != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
        }

        Integer loginType = SessionUtil.getUser().getLoginType();
        String appId;
        String tradeType;
        if (UserLoginType.MP_WEIXIN.getCode() == loginType) {
            appId = wxMiNiAppid;
            tradeType = WxPayConstants.TradeType.JSAPI;
        } else if (UserLoginType.APP_WEIXIN.getCode() == loginType || UserLoginType.REGISTER.getCode() == loginType) {
            appId = wxAppAppid;
            tradeType = WxPayConstants.TradeType.APP;
        } else if (UserLoginType.H5_WEIXIN.getCode() == loginType) {
            appId = wxH5Appid;
            tradeType = WxPayConstants.TradeType.JSAPI;
        } else {
            throw new AppServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
        }

        Object result = null;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setOpenid(SessionUtil.getUser().getOpenId());
            String nonceStr = "ibuaiVcKdpRxkhJA";
            String body = "订单说明" + orderNo;
            orderRequest.setBody(body);
            orderRequest.setAppid("wxe8a1014f56d327d0");
            orderRequest.setTotalFee(orderDO.getActualPrice());
            orderRequest.setSpbillCreateIp(ip);
            orderRequest.setTradeType(tradeType);
            orderRequest.setNonceStr(nonceStr);
            result = wxPayService.createOrder(orderRequest);
            if (result instanceof WxPayMpOrderResult) {
                String prepayId = ((WxPayMpOrderResult) result).getPackageValue();
                prepayId = prepayId.replace("prepay_id=", "");
                UserFormIdDO userFormIdDO = new UserFormIdDO();
                userFormIdDO.setFormId(prepayId);
                userFormIdDO.setUserId(userId);
                userFormIdDO.setOpenid(SessionUtil.getUser().getOpenId());
                userFormIdDO.setGmtUpdate(now);
                userFormIdDO.setGmtCreate(now);
                userBizService.setValidFormId(userFormIdDO);
            }
        } catch (WxPayException e) {
            logger.error("[微信支付] 异常", e);
            throw new ThirdPartServiceException(e.getErrCodeDes(), ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        } catch (Exception e) {
            logger.error("[预付款异常]", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object offlinePrepay(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        // 检测订单状态
        Integer status = orderDO.getStatus();
        if (status != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setPayChannel(PayChannelType.OFFLINE.getCode());
        updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        boolean succ = orderBizService.changeOrderStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
        if (succ) {
            return "ok";
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (PayChannelType.OFFLINE.getCode().equals(orderDO.getPayChannel())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND);
        }
        if (OrderStatusType.refundable(orderDO.getStatus())) {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
            orderBizService.changeOrderStatus(orderNo, orderDO.getStatus(), updateOrderDO);
            // GlobalExecutor.execute(() -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderDO, orderDTO);
            List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_no", orderDO.getOrderNo()));
            orderDTO.setSkuList(orderSkuList);
            // adminNotifyBizService.refundOrder(orderDTO);
            //});
            if (refundDirect(orderDTO)) {
                return "ok";
            }
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
    }


    private Boolean refundDirect(OrderDTO orderDTO) throws ServiceException {
        String orderNo = orderDTO.getOrderNo();
        if (lockComponent.tryLock(OrderBizService.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于团购状态中
                OrderDO orderDO = orderBizService.checkOrderExist(orderNo, null);
                if (OrderStatusType.refundable(orderDO.getStatus())) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
                }
                //2.退款处理
                //2.1.1 先流转状态
                Long userId = orderDO.getUserId();
                UserDTO userDTO = SessionUtil.getUser();
                Integer loginType = userDTO.getLoginType();

                //2.1.2 向微信支付平台发送退款请求
                WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? wxMiNiAppid : wxAppAppid);
                wxPayRefundRequest.setOutTradeNo(orderNo);
                wxPayRefundRequest.setOutRefundNo("refund_" + orderNo);
                wxPayRefundRequest.setRefundDesc("用户退款");
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
                return true;
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
    public String cancel(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CANCEL);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.CANCELED.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public String confirm(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() != OrderStatusType.WAIT_CONFIRM.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CONFIRM);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public ShipTraceDTO queryShip(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() < OrderStatusType.WAIT_CONFIRM.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_HAS_NOT_SHIP);
        }
        if (StringUtils.isEmpty(orderDO.getShipCode()) || StringUtils.isEmpty(orderDO.getShipNo())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_DID_NOT_SET_SHIP);
        }
        ShipTraceDTO shipTraceList = freightBizService.getShipTraceList(orderDO.getShipNo(), orderDO.getShipCode());
        if (CollectionUtils.isEmpty(shipTraceList.getTraces())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_DO_NOT_EXIST_SHIP_TRACE);
        }
        return shipTraceList;
    }

}
