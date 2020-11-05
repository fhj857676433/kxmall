package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

/**
 * @author: Mr Wang
 * @date: 2020/3/9
 * @time: 23:23
 */

@Data
public class SalesTopDTO extends SuperDTO {

    @DtoDescription(description = "名称")
    private String title;

    @DtoDescription(description = "商品ID")
    private Long spuId;

    @DtoDescription(description = "商品规格ID")
    private Long skuId;

    @DtoDescription(description = "销量")
    private Long totalSales;

    @DtoDescription(description = "销售额")
    private Float totalSalesVolume;


}
