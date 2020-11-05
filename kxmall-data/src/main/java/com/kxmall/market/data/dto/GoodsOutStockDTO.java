package com.kxmall.market.data.dto;

import com.kxmall.market.data.domain.GoodsOutStockDO;
import com.kxmall.market.data.domain.OutStockSpuDO;
import lombok.Data;

import java.util.List;

@Data
public class GoodsOutStockDTO extends GoodsOutStockDO{
    //出库商品
    private List<OutStockSpuDO> outStockSpuDOS;
}
