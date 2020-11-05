package com.kxmall.market.app.api.api.coupon;

import com.kxmall.market.biz.service.coupon.CouponBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.CouponDTO;
import com.kxmall.market.data.dto.UserCouponDTO;
import com.kxmall.market.data.mapper.CouponMapper;
import com.kxmall.market.data.mapper.UserCouponMapper;
import com.kxmall.market.data.model.KVModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券service
 *
 * @author kaixin
 * @date 2020/3/10
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    @Resource
    private CouponBizService couponBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String obtainCoupon(Long activityId, Long couponId, Long userId) throws ServiceException {
        return couponBizService.obtainCoupon(0,activityId, couponId, userId);
    }

    @Override
    public List<CouponDTO> getObtainableCoupon(Long userId) throws ServiceException {
        List<CouponDTO> couponDOS = couponMapper.getActiveCoupons();
        //活动中的优惠券Id
        List<Long> activeCouponIds = couponDOS.stream().map(couponDO -> couponDO.getId()).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(activeCouponIds)) {
            return new ArrayList<>();
        }

        List<KVModel<Long, Long>> userCouponsCount = couponMapper.getUserCouponsCount(userId, activeCouponIds);

        List<CouponDTO> couponDTOList = couponDOS.stream().map(item -> {
            /*item.setNowCount(0);
            for (int i = 0; i < userCouponsCount.size(); i++) {
                KVModel<Long, Long> kv = userCouponsCount.get(i);
                if (kv != null && kv.getKey().equals(item.getId())) {
                    item.setNowCount(kv.getValue().intValue());
                }
            }*/
            return item;
        }).collect(Collectors.toList());
        return couponDTOList;
    }

    @Override
    public List<UserCouponDTO> getUserCoupons(Long userId,Integer couponType,Integer status) throws ServiceException {
        return userCouponMapper.getUserCoupons(userId,couponType,status);
    }
}
