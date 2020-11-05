package com.kxmall.market.biz.service.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.kxmall.market.biz.service.category.CategoryBizService;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.OrderSkuDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.domain.UserDO;
import com.kxmall.market.data.dto.CategoryTreeNodeDTO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.enums.OrderStatusType;
import com.kxmall.market.data.enums.UserLoginType;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.BizServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.mapper.OrderMapper;
import com.kxmall.market.data.mapper.OrderSkuMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2019/7/10.
 */
@Service
public class OrderBizService {

    private static final String ORDER_STATUS_LOCK = "ORDER_STATUS_LOCK_";

    //订单退款乐观锁
    public static final String ORDER_REFUND_LOCK = "ORDER_REFUND_LOCK_";

    private static final Logger logger = LoggerFactory.getLogger(OrderBizService.class);

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    private static final String CA_CATEGORY_SECOND_LEVEL_TREE = "CA_CATEGORY_SECOND_LEVEL_TREE";

    private static final String STOCK_LEFT = "stock_left";

    @Autowired
    private WxPayService wxPayService;

    @Value("${com.kxmall.market.wx.mini.app-id}")
    private String wxMiNiAppid;

    @Value("${com.kxmall.market.wx.app.app-id}")
    private String wxAppAppid;

    public boolean changeOrderStatus(String orderNo, int nowStatus, OrderDO orderDO) throws ServiceException {
        try {
            // 防止传入值为空,导致其余订单被改变
            if (orderNo == null || orderDO == null) {
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            }

            if (lockComponent.tryLock(ORDER_STATUS_LOCK + orderNo, 30)) {
                if (orderMapper.update(orderDO,
                        new EntityWrapper<OrderDO>()
                                .eq("order_no", orderNo)
                                .eq("status", nowStatus)) > 0) {
                    return true;
                }
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            } else {
                throw new BizServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[订单状态扭转] 异常", e);
            throw new BizServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(ORDER_STATUS_LOCK + orderNo);
        }
    }

    public OrderDO checkOrderExist(String orderNo, Long userId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>().eq("order_no", orderNo);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        return orderDOS.get(0);
    }

    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>()
                .eq("id", orderId);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDOS.get(0), orderDTO);
        List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", orderId));
        // 获取每个sku的1级和2级列表
        // 获取spu——category关联信息
        List<SpuDO> spuDOList = spuMapper.selectList(null);
        //List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(CA_CATEGORY_SECOND_LEVEL_TREE, CategoryTreeNodeDTO.class);
        List<CategoryTreeNodeDTO> objList = categoryBizService.categorySecondLevelTree();
        List<CategoryTreeNodeDTO> grandsonCatgoryList = new LinkedList<>();
        objList.stream().forEach(item -> grandsonCatgoryList.addAll(item.getChildren()));

        for (OrderSkuDO orderSkuDO : orderSkuList) {
            List<SpuDO> partSpuList = spuDOList.stream().filter(t -> t.getId() == orderSkuDO.getSpuId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(partSpuList)) {
                grandsonCatgoryList.stream().forEach(parentDTO -> {
                    parentDTO.getChildren().stream().forEach(dto -> {
                        dto.getChildren().stream().forEach(sonDTO -> {
                            if (sonDTO.getValue() == partSpuList.get(0).getCategoryId()) {
                                String right = dto.getFullName();
                                String left = parentDTO.getFullName();
                                orderSkuDO.setCategory(left + ">" + right);
                            }
                        });
                    });
                });
            }
        }
        // 获取可售量（总数-锁定量）
//        if (orderDTO.getStoreId() != null) {
//            for (OrderSkuDO orderSkuDO: orderSkuList) {
//                List<StockDO> stockDoList = cacheComponent.getObjList(STOCK_LEFT, StockDO.class);
//                List<StockDO> subStockList = stockDoList.stream().filter(t -> t.getSkuId() == orderSkuDO.getSkuId() &&
//                        t.getStorageId() == orderDOS.get(0).getStoreId()).collect(Collectors.toList());
//                if (!CollectionUtils.isEmpty(subStockList)) {
//                    orderSkuDO.setSalableNum(subStockList.get(0).getStock());
//                } else {
//                    logger.error("===========缓存未同步=========");
//                }
//            }
//        } else {
//            logger.error("===========库存id不存在========");
//        }

        orderDTO.setSkuList(orderSkuList);
        return orderDTO;
    }

    //    @Transactional(rollbackFor = Exception.class) 外面加了事务
    public String groupShopStatusRefund(String orderNo) throws ServiceException {
        if (lockComponent.tryLock(OrderBizService.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于团购状态中
                OrderDO orderDO = checkOrderExist(orderNo, null);
                if (orderDO.getStatus() != OrderStatusType.GROUP_SHOP_WAIT.getCode()) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_IS_NOT_GROUP_SHOP_STATUS);
                }
                //2.退款处理
                //2.1.1 先流转状态
                OrderDO updateOrderDO = new OrderDO();
                updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                updateOrderDO.setGmtUpdate(new Date());
                changeOrderStatus(orderNo, OrderStatusType.GROUP_SHOP_WAIT.getCode(), updateOrderDO);
                Long userId = orderDO.getUserId();
                UserDO userDO = userMapper.selectById(userId);
                Integer loginType = userDO.getLoginType();

                //2.1.2 向微信支付平台发送退款请求
                WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? wxMiNiAppid : wxAppAppid);
                wxPayRefundRequest.setOutTradeNo(orderNo);
                wxPayRefundRequest.setOutRefundNo("refund_" + orderNo);
                wxPayRefundRequest.setRefundDesc("团购失败退款");
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

    public Boolean updateOrderStatus(String orderNo, Integer status) throws ServiceException {
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setOrderNo(orderNo);
        updateOrderDO.setStatus(status);
        return updateOrder(orderNo, updateOrderDO);
    }

    public Boolean updateOrder(String orderNo, OrderDO orderDO) throws ServiceException {
        try {
            // 防止传入值为空,导致其余订单被改变
            if (orderNo == null || orderDO == null) {
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            }
            if (lockComponent.tryLock(ORDER_STATUS_LOCK + orderNo, 30)) {
                // orderDO 是要被更新的数据，orderNo是条件
                if (orderMapper.update(orderDO, new EntityWrapper<OrderDO>().eq("order_no", orderNo)) > 0) {
                    return true;
                }
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            } else {
                throw new BizServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
            }

        } catch (Exception e) {
            logger.error("[订单状态扭转] 异常", e);
            throw new BizServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(ORDER_STATUS_LOCK + orderNo);
        }
    }


}
