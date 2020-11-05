package com.kxmall.market.app.api.api.positioning;

import com.kxmall.market.biz.service.positioning.PositioningBizService;
import com.kxmall.market.data.dto.storage.RecentlyStorageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description: H5获取最近前置仓接口实现
 * @author: fy
 * @date: 2020/02/22 10:56
 **/
@Service
public class PositioningServiceImpl implements PositioningService {

    @Autowired
    private PositioningBizService positioningBizService;

    /**
     * 获取最近的前置仓
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param adcode    高德API区域adcode
     * @return 最近的前置仓信息
     */
    @Override
    public RecentlyStorageDTO getRecentlyStorage(String adcode, BigDecimal longitude, BigDecimal latitude) {
        return positioningBizService.getRecentlyStorage(adcode,longitude, latitude);
    }

}
