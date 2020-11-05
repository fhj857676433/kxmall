package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.RiderOrderDO;
import com.kxmall.market.data.dto.rider.RiderStatisticalDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/02 17:29
 **/
public interface RiderOrderMapper extends BaseMapper<RiderOrderDO> {


    public List<String> selectRiderOrderExpireOrderNos(@Param("status") Integer status, @Param("time") Date time);


    int updateRiderOrderListStatus(@Param("orderNoList") List<String> orderNoList, @Param("status") Integer status);

    int updateRiderOrderStatus(@Param("orderNo") String orderNo, @Param("status") Integer status, @Param("updateAbnormalFlag") Boolean updateAbnormalFlag);

    List<RiderStatisticalDTO> statisticalCount(@Param("riderId") Long riderId);
}

