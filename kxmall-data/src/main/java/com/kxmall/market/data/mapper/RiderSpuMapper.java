package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.RiderSpuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/02 17:30
 **/
public interface RiderSpuMapper extends BaseMapper<RiderSpuDO> {

    /**
     * 批量插入配送商品
     *
     * @param riderSpuDOList
     * @return
     */
    int insertBatch(@Param("list") List<RiderSpuDO> riderSpuDOList);
}
