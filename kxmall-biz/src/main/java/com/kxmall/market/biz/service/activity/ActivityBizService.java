package com.kxmall.market.biz.service.activity;


import com.kxmall.market.biz.service.coupon.CouponBizService;
import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.AppServiceException;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.CouponDO;
import com.kxmall.market.data.domain.InviteDO;
import com.kxmall.market.data.domain.UserDO;
import com.kxmall.market.data.dto.ActivityDTO;
import com.kxmall.market.data.enums.ActivityType;
import com.kxmall.market.data.mapper.ActivityMapper;
import com.kxmall.market.data.mapper.CouponMapper;
import com.kxmall.market.data.mapper.InviteMapper;
import com.kxmall.market.data.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author kaixin
 * @date 2020/3/8
 */
@Service
public class ActivityBizService {

    /**
     * 活动缓存
     */
    private static final String CA_ACTIVITY_PREFIX = "CA_ACTIVITY_";

    @Resource
    private UserMapper userMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private InviteMapper inviteMapper;
    @Resource
    private CouponBizService couponBizService;

    @Resource
    private CacheComponent cacheComponent;

    private static final Logger logger = LoggerFactory.getLogger(ActivityBizService.class);

    /**
     * 获取优惠券新人+ 邀请新人
     *
     * @param userId 用户
     */
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.REQUIRES_NEW)
    public Boolean newRegistration(Long userId, String param, String ip) {
        if (ObjectUtils.isEmpty(userId)) {
            return false;
        }
        try {
            UserDO userDO = userMapper.selectById(userId);
            if (!ObjectUtils.isEmpty(userDO)) {
                //老用户不参与新人活动
                if (userDO.getOldMan()) {
                    return false;
                }
            }
            //如果是新人就新增邀请表
            inviteNewpeople(userId, param, ip);
            //处理拉新的动作
            //1.获取活动规则
            ActivityDTO activityDTO = this.selectOneByType(ActivityType.NEW_REGISTRATION);
            if (ObjectUtils.isEmpty(activityDTO)) {
                throw new AppServiceException("不存在该活动",404);
            }
            //2.活动结束了
            if (activityDTO.getStatus() != 1) {
                throw new AppServiceException("活动状态不正确",404);
            }
            //活动是否在区间内
            Date now = new Date();
            if (activityDTO.getActivityStartTime().after(now) || activityDTO.getActivityEndTime().before(now)) {
                //活动时间不在范围内，更新为到期
                throw new AppServiceException("活动已过期",404);
            }
            //3.新用户是否有资格参与
            if (!activityDTO.getQualifyNew()) {
                throw new AppServiceException("新人无权参加活动",404);
            }
            if (CollectionUtils.isEmpty(activityDTO.getActivityCounponDO())) {
                throw new AppServiceException("该活动下无优惠券",404);
            }
            //因为拉新的抢购券只有一个/如果有多张也只使用第一张
            Long couponId = activityDTO.getActivityCounponDO().get(0).getCouponId();
            //4.获取优惠券信息
            CouponDO couponDO = couponMapper.selectById(couponId);
            if (ObjectUtils.isEmpty(couponDO)) {
                throw new AppServiceException("该优惠券已被删除，请重新添加优惠券",404);
            }
            //5.符合资格发券
            couponBizService.obtainCoupon(activityDTO.getActivityType(),activityDTO.getId(), couponId, userId);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 邀请新人
     *
     * @param userId 登录用户Id
     * @param param  被邀请人加密参数
     */
    private void inviteNewpeople(Long userId, String param, String ip) {
        //参数校验
        if (StringUtils.isEmpty(param)) {
            return;
        }
        if (ObjectUtils.isEmpty(userId)) {
            return;
        }
        Date date = new Date();
        InviteDO inviteDO = new InviteDO();
        inviteDO.setStatus(0);
        inviteDO.setGmtUpdate(date);
        inviteDO.setGmtCreate(date);
        inviteDO.setInviteId(ShareCodeUtil.codeToId(param));
        //被邀请人id
        inviteDO.setBeinviteId(userId);
        inviteDO.setBeinviteIp(ip);
        inviteMapper.insert(inviteDO);
    }

    /**
     * 获取互动设置缓存
     *
     * @param activityType 活动类型
     * @return 活动对象
     */
    public ActivityDTO selectOneByType(ActivityType activityType) {
        String cacheKey = CA_ACTIVITY_PREFIX + "_" + activityType.getCode();
        ActivityDTO objCache = cacheComponent.getObj(cacheKey, ActivityDTO.class);
        if (objCache != null) {
            return objCache;
        }
        objCache = activityMapper.selectOneByType(activityType.getCode());
        if (!ObjectUtils.isEmpty(objCache)) {
            //若关键字为空，制作缓存
            cacheComponent.putObj(cacheKey, objCache, Const.CACHE_ONE_DAY);
        }
        return objCache;
    }
}
