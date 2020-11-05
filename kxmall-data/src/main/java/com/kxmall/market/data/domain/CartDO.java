package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @author kaixin
 * @date 2019/7/3
 */
@Data
@TableName("kxmall_cart")
public class CartDO extends SuperDO {

    @TableField("sku_id")
    private Long skuId;

    @TableField("user_id")
    private Long userId;

    private Integer num;

    @TableField("activity_id")
    private Long activityId;

    @TableField("coupon_id")
    private Long couponId;


}
