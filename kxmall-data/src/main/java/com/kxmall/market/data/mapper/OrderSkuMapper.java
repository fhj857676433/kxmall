package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.OrderSkuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/7/6.
 */
public interface OrderSkuMapper extends BaseMapper<OrderSkuDO> {


    Integer insertBatch(@Param("list") List<OrderSkuDO> orderSkuDOList);
}
