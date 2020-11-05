package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动优惠券对象
 *
 * @author kaixin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_activity_coupon")
public class ActivityCounponDO extends SuperDO {

    @DtoDescription(description = "活动主表id")
    @TableField("activity_id")
    private Long activityId;

    @DtoDescription(description = "优惠券id")
    @TableField("coupon_id")
    private Long couponId;

    @DtoDescription(description = "最低消费")
    @TableField("min_money")
    private Integer minMoney;

    @DtoDescription(description = "每日限量（0表示不限量）")
    @TableField("num_limit")
    private Integer numLimit;

    @DtoDescription(description = "每人限领(0表示不限）")
    @TableField("man_limit")
    private Integer manLimit;

    @DtoDescription(description = "今日优惠券已发送的量")
    @TableField("num_send")
    private Integer numSend;

}
