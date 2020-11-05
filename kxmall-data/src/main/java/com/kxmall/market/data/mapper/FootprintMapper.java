package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.FootprintDTO;
import com.kxmall.market.data.domain.FootprintDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 上午8:59
 */
public interface FootprintMapper extends BaseMapper<FootprintDO> {

    public List<FootprintDTO> getAllFootprint(@Param("userId")Long userId, @Param("offset")Integer offset, @Param("size")Integer size);

}
