package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.RiderCycleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RiderCycleMapper extends BaseMapper<RiderCycleDO> {

    Integer insertBatch(@Param("list")List<RiderCycleDO> riderCycleDOList);
}
