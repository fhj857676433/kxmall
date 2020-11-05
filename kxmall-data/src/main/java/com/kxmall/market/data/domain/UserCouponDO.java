package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @date 2019/7/4
 */
@Data
@TableName("kxmall_user_coupon")
public class UserCouponDO extends SuperDO {

    @TableField("user_id")
    private Long userId;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("activity_id")
    private Long activityId;

    @TableField("order_id")
    private Long orderId;

    @TableField("gmt_used")
    private Date gmtUsed;

    @TableField("gmt_start")
    private Date gmtStart;

    @TableField("gmt_end")
    private Date gmtEnd;

    @DtoDescription(description = "活动类型")
    @TableField("activity_type")
    private Integer activityType;

}
