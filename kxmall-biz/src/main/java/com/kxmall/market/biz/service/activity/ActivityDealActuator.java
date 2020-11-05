package com.kxmall.market.biz.service.activity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.biz.service.coupon.CouponBizService;
import com.kxmall.market.biz.service.orderobserve.OrderObserver;
import com.kxmall.market.biz.service.orderobserve.OrderUpdater;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.InviteDO;
import com.kxmall.market.data.dto.ActivityDTO;
import com.kxmall.market.data.enums.ActivityType;
import com.kxmall.market.data.mapper.InviteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单推送通知对象类执行器
 *
 * @author kaixin
 * @version 1.0
 * @date 2020/3/10
 */
@Component
public class ActivityDealActuator implements OrderObserver {

    @Resource
    private InviteMapper inviteMapper;
    @Resource
    private CouponBizService couponBizService;
    @Resource
    private ActivityBizService activityBizService;

    private static final Logger logger = LoggerFactory.getLogger(ActivityDealActuator.class);

    /**
     * 处理
     *
     * @param obj 默认传参对象
     */
    @Override
    public void doSomeThing(OrderUpdater obj) {
        if (ObjectUtils.isEmpty(obj.getOrderDo())) {
            return;
        }
        if (ObjectUtils.isEmpty(obj.getOrderDo().getUserId())) {
            return;
        }


        try {

            //方法级别事物
            InviteDO inviteDO = new InviteDO();
            inviteDO.setBeinviteId(obj.getOrderDo().getUserId());
            inviteDO = inviteMapper.selectOne(inviteDO);
            if (ObjectUtils.isEmpty(inviteDO)) {
                return;
            }
            inviteDO.setStatus(1);
            //将状态设置为1
            inviteMapper.updateById(inviteDO);

            //查看主动邀请的人是否满足发券条件了
            Wrapper<InviteDO> wrapper = new EntityWrapper<>();
            wrapper.eq("invite_id", inviteDO.getInviteId());
            wrapper.eq("status", 1);
            List<InviteDO> selectList = inviteMapper.selectList(wrapper);
            //获取活动规则
            ActivityDTO activityDTO = activityBizService.selectOneByType(ActivityType.INVITE_NEW_PEOPLE);
            if (ObjectUtils.isEmpty(activityDTO)) {
                return;
            }
            //活动是否在区间内
            Date now = new Date();
            if (activityDTO.getActivityStartTime().after(now) || activityDTO.getActivityEndTime().before(now)) {
                return;
            }
            if (activityDTO.getPullNew() && activityDTO.getPullNum() <= selectList.size()) {
                Long couponId = activityDTO.getActivityCounponDO().get(0).getCouponId();
                //判断是否资格发券
                couponBizService.obtainCoupon(activityDTO.getActivityType(),activityDTO.getId(), couponId, obj.getOrderDo().getUserId());
                selectList.forEach(item -> {
                    item.setStatus(2);
                    inviteMapper.updateById(item);
                });
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
