package com.kxmall.market.app.api.api.cart;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.biz.service.category.CategoryBizService;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.CartDO;
import com.kxmall.market.data.dto.CartDTO;
import com.kxmall.market.data.mapper.CartMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @date 2019/7/3
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private CategoryBizService categoryBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartDO addCartItem(Long skuId, Integer num, Long userId, Long activityId, Long couponId) throws ServiceException {
        List<CartDO> cartDos = cartMapper.selectList(
                new EntityWrapper<CartDO>()
                        .eq("activity_id", activityId)
                        .eq("coupon_id", couponId)
                        .eq("sku_id", skuId)
                        .eq("user_id", userId));
        CartDO cartDO = new CartDO();
        Date now = new Date();
        if (!CollectionUtils.isEmpty(cartDos)) {
            //若非空
            cartDO.setId(cartDos.get(0).getId());
            cartDO.setNum(cartDos.get(0).getNum() + num);
            cartDO.setGmtUpdate(now);
            if (cartMapper.updateById(cartDO) <= 0) {
                throw new AppServiceException(ExceptionDefinition.CART_UPDATE_FAILED);
            }
        } else {
            //不存在，则添加购物车
            cartDO.setSkuId(skuId);
            cartDO.setNum(num);
            cartDO.setActivityId(activityId);
            cartDO.setCouponId(couponId);
            cartDO.setUserId(userId);
            cartDO.setGmtUpdate(now);
            cartDO.setGmtCreate(now);
            if (cartMapper.insert(cartDO) <= 0) {
                throw new AppServiceException(ExceptionDefinition.CART_UPDATE_FAILED);
            }
        }
        return cartDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean subCartItem(Long skuId, Integer num, Long userId, Long activityId, Long couponId) throws ServiceException {
        List<CartDO> cartDOS = cartMapper.selectList(
                new EntityWrapper<CartDO>()
                        .eq("activity_id", activityId)
                        .eq("coupon_id", couponId)
                        .eq("sku_id", skuId)
                        .eq("user_id", userId));

        CartDO cartDO = new CartDO();
        if (!CollectionUtils.isEmpty(cartDOS)) {
            cartDO.setId(cartDOS.get(0).getId());
            cartDO.setNum(cartDOS.get(0).getNum() - num);
            if (cartDO.getNum() <= 0) {
                //直接删除此商品
                return cartMapper.deleteById(cartDO.getId()) > 0;
            } else {
                return cartMapper.updateById(cartDO) > 0;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCartItem(Long cartId, Long userId) throws ServiceException {
        return cartMapper.delete(
                new EntityWrapper<CartDO>()
                        .eq("id", cartId)
                        .eq("user_id", userId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCartItemBatch(String cartIdList, Long userId) throws ServiceException {
        if (StringUtils.isEmpty(cartIdList)) {
            throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        String[] split = cartIdList.split(",");
        if (split.length == 0) {
            throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        List<Long> array = new ArrayList<>(split.length);
        for (String idRaw : split) {
            array.add(new Long(idRaw));
        }
        return cartMapper.delete(
                new EntityWrapper<CartDO>()
                        .in("id", array)
                        .eq("user_id", userId)) > 0;
    }

    @Override
    @Transactional
    public Boolean removeCartAll(Long userId) throws ServiceException {
        return cartMapper.delete(
                new EntityWrapper<CartDO>()
                        .eq("user_id", userId)) > 0;
    }

    @Override
    public Integer updateCartItemNum(Long cartId, Integer num, Long userId) throws ServiceException {
        CartDO cartDO = new CartDO();
        cartDO.setNum(num);
        Integer update = cartMapper.update(cartDO,
                new EntityWrapper<CartDO>()
                        .eq("id", cartId)
                        .eq("user_id", userId));
        if (update > 0) {
            return num;
        }
        throw new AppServiceException(ExceptionDefinition.CART_UPDATE_FAILED);
    }

    @Override
    public Integer countCart(Long userId) {
        Integer userCountCart = cartMapper.countCart(userId);
        return (userCountCart == null) ? 0 : userCountCart;
    }

    @Override
    public List<CartDTO> getCartList(Long userId, Long storageId) throws ServiceException {
        List<CartDTO> cartList = cartMapper.getCartList(userId, storageId);
        for (CartDTO cartDTO : cartList) {
            cartDTO.setCategoryIdList(categoryBizService.getCategoryFamily(cartDTO.getCategoryId()));
        }
        return cartList;
    }
}
