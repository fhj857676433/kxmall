package com.kxmall.market.data.dto.order;

import lombok.Data;

/**
 * Created by admin on 2019/7/6.
 */
@Data
public class OrderRequestSkuDTO {

    private Long skuId;

    private Integer price;

    private Integer num;

}
