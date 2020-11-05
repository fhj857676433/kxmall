package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午3:30
 * @author kaixin
 */
@Data
public class RecommendDTO extends SuperDTO {

    private Integer recommendType;

    private Long skuId;

    private Long spuId;

    private Integer spuOriginalPrice;

    private Integer spuPrice;

    private Integer spuSales;

    private String spuImg;

    private String spuTitle;

    private String spuUnit;

    private Long spuCategoryId;

    @DtoDescription(description = "活动id")
    private Long activityId;
    @DtoDescription(description = "优惠券id")
    private Long couponId;
}
