package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.ActivityDO;
import com.kxmall.market.data.domain.CouponDO;
import com.kxmall.market.data.domain.UserDO;
import lombok.Data;

import java.util.Date;

/**
 * 用户优惠券DTO
 *
 * @author admin
 * @date 2019/7/5
 */
@Data
public class UserCouponDTO extends SuperDTO {

    private String categoryTitle;

    private Long userId;

    @DtoDescription(description = "优惠券id")
    private Long couponId;

    @DtoDescription(description = "活动id")
    private Long activityId;

    @DtoDescription(description = "活动类型")
    private Long activityType;

    /**
     * 对应的订单id
     */
    @DtoDescription(description = "对应的订单id")
    private Long orderId;

    /**
     * 优惠券使用时间
     */
    @DtoDescription(description = "优惠券使用时间")
    private Date gmtUsed;

    /**
     * 优惠券开始时间
     */
    @DtoDescription(description = "优惠券开始时间")
    private Date gmtStart;
    /**
     * 优惠券结束时间
     */
    @DtoDescription(description = "优惠券结束时间")
    private Date gmtEnd;

    /**
     * 用户对象
     */
    @DtoDescription(description = "用户对象")
    private UserDO userDO;
    /**
     * 优惠券对象
     */
    @DtoDescription(description = "优惠券对象")
    private CouponDO couponDO;

    /**
     * 活动对象
     */
    @DtoDescription(description = "活动对象")
    private ActivityDO activityDO;


}
