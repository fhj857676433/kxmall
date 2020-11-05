package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.RiderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RiderMapper extends BaseMapper<RiderDO> {
    /**
     * 批量更新骑手状态
     *
     * @param ids   骑手主键集合
     * @param state 骑手资料状态
     * @return 影响行数
     */
    Integer batchUpdateState(@Param("ids") List<Long> ids, @Param("state") int state);

    /**
     * 批量更新前置仓库资料营业状态
     *
     * @param ids            前置仓库主键集合
     * @param workState 前置仓库资料营业状态
     * @return 影响行数
     */
    Integer batchUpdateWeekState(@Param("ids") List<Long> ids, @Param("workState") int workState);
}
