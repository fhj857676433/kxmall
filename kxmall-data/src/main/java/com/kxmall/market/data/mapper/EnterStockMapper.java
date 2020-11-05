package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.EnterStockDO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *@author wxf
 *@date  2029/3/1 - 21:29
 * 入库Mapper
 */
public interface EnterStockMapper extends BaseMapper<EnterStockDO> {

    Map<String,Object> getGoodsDetail(@Param("id") Long id);
}
