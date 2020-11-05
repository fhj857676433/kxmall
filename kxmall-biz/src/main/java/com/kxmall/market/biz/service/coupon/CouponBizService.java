package com.kxmall.market.biz.service.coupon;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.biz.service.activity.ActivityBizService;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.ActivityCounponDO;
import com.kxmall.market.data.domain.CouponDO;
import com.kxmall.market.data.domain.UserCouponDO;
import com.kxmall.market.data.enums.StatusType;
import com.kxmall.market.data.mapper.ActivityCouponMapper;
import com.kxmall.market.data.mapper.CouponMapper;
import com.kxmall.market.data.mapper.UserCouponMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 优惠券发放
 *
 * @author kaixin
 */
@Service
public class CouponBizService {


    @Resource
    private CouponMapper couponMapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private ActivityCouponMapper activityCouponMapper;

    @Resource
    private LockComponent lockComponent;

    private static final String COUPON_USER_LOCK = "COUPON_USER_LOCK_";

    private static final Logger logger = LoggerFactory.getLogger(ActivityBizService.class);

    public String obtainCoupon(Integer activityType,Long activityId, Long couponId, Long userId) throws ServiceException {

        String lockKey = COUPON_USER_LOCK + userId + "_" + couponId;
        Integer overTime = 10;
        //防止用户一瞬间提交两次表单，导致超领
        if (lockComponent.tryLock(lockKey, overTime)) {
            try {
                CouponDO couponDO = couponMapper.selectById(couponId);
                //判断优惠券是否冻结
                if (couponDO.getStatus() == StatusType.LOCK.getCode()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_HAS_LOCKED);
                }
                //判断优惠券是否在期间内
                Date now = new Date();
                if (couponDO.getGmtEnd() != null && couponDO.getGmtEnd().getTime() < now.getTime()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_ACTIVITY_HAS_END);
                }
                if (couponDO.getGmtStart() != null && couponDO.getGmtStart().getTime() > now.getTime()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_ACTIVITY_NOT_START);
                }
                //每日限量校验-查询已发送的量
                ActivityCounponDO activityCounponDo = new ActivityCounponDO();
                activityCounponDo.setActivityId(activityId);
                activityCounponDo.setCouponId(couponId);
                activityCounponDo = activityCouponMapper.selectOne(activityCounponDo);
                // 0 说明不限量
                if (activityCounponDo.getNumLimit() != 0) {
                    if (activityCounponDo.getNumSend() > activityCounponDo.getNumLimit()) {
                        throw new AppServiceException(ExceptionDefinition.COUPON_ISSUE_OVER);
                    } else {
                        activityCouponMapper.decCoupon(activityCounponDo.getId());
                    }
                }
                //0 说明不限领校验
                if (activityCounponDo.getManLimit() != 0) {
                    //校验用户是否已经领了
                    Integer count = userCouponMapper.selectCount(
                            new EntityWrapper<UserCouponDO>()
                                    .eq("user_id", userId)
                                    .eq("activity_id", activityId)
                                    .eq("coupon_id", couponId));

                    if (count >= activityCounponDo.getManLimit()) {
                        throw new AppServiceException(ExceptionDefinition.COUPON_YOU_HAVE_OBTAINED);
                    }
                }
                //领取优惠券
                UserCouponDO userCouponDO = new UserCouponDO();
                userCouponDO.setUserId(userId);
                userCouponDO.setCouponId(couponId);
                userCouponDO.setActivityId(activityId);
                userCouponDO.setActivityType(activityType);
                if (couponDO.getGmtStart() != null && couponDO.getGmtEnd() != null) {
                    //如果优惠券是按区间领取的
                    userCouponDO.setGmtStart(couponDO.getGmtStart());
                    userCouponDO.setGmtEnd(couponDO.getGmtEnd());
                } else if (couponDO.getDays() != null) {
                    //如果是任意领取的，则从当前时间 加上 可用天数
                    userCouponDO.setGmtStart(now);
                    userCouponDO.setGmtEnd(new Date(now.getTime() + 1000L * 60 * 60 * 24 * couponDO.getDays()));
                } else {
                    throw new AppServiceException(ExceptionDefinition.COUPON_STRATEGY_INCORRECT);
                }
                userCouponDO.setGmtUpdate(now);
                userCouponDO.setGmtCreate(now);
                userCouponMapper.insert(userCouponDO);
                return "ok";
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("用户id:" + userId + "==>[领取优惠券] 异常", e);
                throw new AppServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(lockKey);
            }
        } else {
            throw new AppServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }

    }

}
