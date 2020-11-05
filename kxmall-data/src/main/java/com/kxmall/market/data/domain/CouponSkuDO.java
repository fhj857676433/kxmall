package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动管理商品DTO
 *
 * @author kaixin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_coupon_sku")
public class CouponSkuDO extends SuperDO {

    /**
     * 活动主表id
     */
    @TableField("coupon_id")
    private Long couponId;
    /**
     * 规格id
     */
    @TableField("sku_id")
    private Long skuId;
    /**
     * 商品id
     */
    @TableField("spu_id")
    private Long spuId;
    /**
     * 优惠价格
     */
    @TableField("discount_price")
    private Integer discountPrice;
    /**
     * 每日限量
     */
    @TableField("limit_num")
    private Integer limitNum;

}
