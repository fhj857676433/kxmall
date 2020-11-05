package com.kxmall.market.data.dto.goods;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.SuperDTO;
import lombok.Data;
import lombok.ToString;

/**
 * @author kaixin
 * @date 2020/3/7
 */
@Data
@ToString
public class SkuDTO extends SuperDTO {

    @DtoDescription(description = "商品id")
    private Long spuId;

    @DtoDescription(description = "条形码")
    private String barCode;

    @DtoDescription(description = "类目id")
    private Long categoryId;

    @DtoDescription(description = "SKU显示文字")
    private String title;

    @DtoDescription(description = "商品显示文字")
    private String spuTitle;

    @DtoDescription(description = "SKU显示图片")
    private String img;

    @DtoDescription(description = "商品显示图片")
    private String spuImg;

    @DtoDescription(description = "商品单位")
    private String unit;

    @DtoDescription(description = "商品折扣")
    private Double discount;

    @DtoDescription(description = "活动id")
    private Long activityId;

    @DtoDescription(description = "优惠券id")
    private Long couponId;

    @DtoDescription(description = "库存信息对象")
    private StockDTO stockDTO;

    @DtoDescription(description = "商品对象")
    private SpuDO spuDO;


}
