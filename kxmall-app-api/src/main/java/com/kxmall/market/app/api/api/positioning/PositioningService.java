package com.kxmall.market.app.api.api.positioning;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.data.dto.storage.RecentlyStorageDTO;

import java.math.BigDecimal;

/**
 * @description: 获取最近前置仓接口
 * @author: fy
 * @date: 2020/02/22 10:30
 **/
@HttpOpenApi(group = "position", description = "前置仓定位接口")
public interface PositioningService {

    /**
     * 获取最近的前置仓
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param adcode    高德API区域adcode
     * @return 最近的前置仓信息
     */
    @HttpMethod(description = "获取最近的前置仓")
    public RecentlyStorageDTO getRecentlyStorage(
            @HttpParam(name = "adcode", type = HttpParamType.COMMON, description = "区域adcode") String adcode,
            @NotNull @HttpParam(name = "lng", type = HttpParamType.COMMON, description = "经度") BigDecimal longitude,
            @NotNull @HttpParam(name = "lat", type = HttpParamType.COMMON, description = "纬度") BigDecimal latitude);
}
