package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.ActivityCounponDO;
import org.apache.ibatis.annotations.Param;

/**
 * 活动管理优惠券mapper
 *
 * @author kaixin
 * @date 2019/7/2
 */
public interface ActivityCouponMapper extends BaseMapper<ActivityCounponDO> {


    /**
     * 发一张券
     *
     * @param id 活动券id
     */
    void decCoupon(@Param("id") Long id);
}
