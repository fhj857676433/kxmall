package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.GoodsOutStockDO;

public interface GoodsOutStockMapper extends BaseMapper<GoodsOutStockDO> {

    /**
     * 出库商品数更新
     *
     * @return
     */
    GoodsOutStockDO selectByMax();
}
