package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/3
 */
@Data
public class CartDTO extends SuperDTO {

    @DtoDescription(description = "规格id")
    private Long skuId;
    @DtoDescription(description = "数量")
    private Integer num;
    @DtoDescription(description = "名称")
    private String title;
    @DtoDescription(description = "原始价格")
    private Integer originalPrice;
    @DtoDescription(description = "当前价格")
    private Integer price;
    @DtoDescription(description = "规格名称")
    private String skuTitle;
    @DtoDescription(description = "商品图片")
    private String spuImg;
    @DtoDescription(description = "规格图片")
    private String skuImg;
    @DtoDescription(description = "库存id")
    private Integer stock;
    @DtoDescription(description = "商品id")
    private Long spuId;
    @DtoDescription(description = "类目id")
    private Long categoryId;
    @DtoDescription(description = "类目集合")
    private List<Long> categoryIdList;
    @DtoDescription(description = "活动Id")
    private Long activityId;
    @DtoDescription(description = "优惠券id")
    private Long couponId;

}
