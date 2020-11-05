package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author kaixin
 * @date 2020/3/4
 */
@Data
@TableName("kxmall_coupon")
@AllArgsConstructor
@NoArgsConstructor
public class CouponDO extends SuperDO {

    @DtoDescription(description = "购物券名称")
    private String title;
    @DtoDescription(description = "购物券类型1优惠券2抢购券")
    private Integer type;
    @DtoDescription(description = "优惠类型1满减券")
    @TableField("discount_type")
    private Integer discountType;
    @DtoDescription(description = "活动介绍")
    private String description;
    @DtoDescription(description = "满减金额")
    private Integer discount;
    @DtoDescription(description = "最低消费")
    private Integer min;
    @DtoDescription(description = "优惠券状态0下架1正常")
    private Integer status;
    @DtoDescription(description = "类目id")
    @TableField("category_id")
    private Long categoryId;
    @DtoDescription(description = "领券相对天数")
    private Integer days;
    @DtoDescription(description = "指定绝对开始时间")
    @TableField("gmt_start")
    private Date gmtStart;
    @DtoDescription(description = "指定结束开始时间")
    @TableField("gmt_end")
    private Date gmtEnd;
    @DtoDescription(description = "是否多选一 0否 1 是")
    @TableField("choose_one")
    private Integer chooseOne;

}
