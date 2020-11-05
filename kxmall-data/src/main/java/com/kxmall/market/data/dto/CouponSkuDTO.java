package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

/**
 * 活动管理商品DTO
 *
 * @author kaixin
 */
@Data
public class CouponSkuDTO extends SuperDTO {

    /**
     * 活动主表id
     */
    @DtoDescription(description = "优惠券id")
    private Long couponId;
    /**
     * 规格id
     */
    @DtoDescription(description = "规格id")
    private Long skuId;
    /**
     * 商品id
     */
    @DtoDescription(description = "商品id")
    private Long spuId;
    /**
     * 优惠价格
     */
    @DtoDescription(description = "优惠价格")
    private Integer discountPrice;
    /**
     * 每日限量
     */
    @DtoDescription(description = "每日限量")
    private Integer limitNum;

}
