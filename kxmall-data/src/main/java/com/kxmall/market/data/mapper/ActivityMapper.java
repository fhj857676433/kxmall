package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.ActivityDTO;
import com.kxmall.market.data.domain.ActivityDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 活动管理mapper
 *
 * @author kaixin
 * @date 2019/7/2
 */
public interface ActivityMapper extends BaseMapper<ActivityDO> {

    /**
     * 查询活动列表
     *
     * @param rowBounds     分页对象
     * @param title         标题
     * @param activityType  活动类型
     * @param status        状态
     * @param activityStartTime 开始时间
     * @param activityEndTime 结束时间
     * @return 活动集合
     */
    public List<ActivityDTO> selectActivityPage(@Param("rowBounds") RowBounds rowBounds, @Param("title") String title, @Param("activityType") Integer activityType, @Param("status") Integer status, @Param("activityStartTime") Long activityStartTime, @Param("activityEndTime") Long activityEndTime);

    /**
     * 获取
     * @param id 活动Id
     * @return 活动对象
     */
    ActivityDTO selectOneById(@Param("id") Long id);

    /**
     * 获取活动规则
     * @param type 活动类型
     * @return 活动对象
     */
    ActivityDTO selectOneByType(@Param("type") Integer type);
}
