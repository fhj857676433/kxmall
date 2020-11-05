package com.kxmall.market.admin.api.api.region;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * 行政区域接口
 *
 * @author lw
 * @date 2020/2/18
 */
@HttpOpenApi(group = "region", description = "行政区域接口")
public interface RegionService {

    @HttpMethod(description = "获取所有省份")
    public List<Map<String, Object>> getProvinceAll() throws AdminServiceException;

    @HttpMethod(description = "获取所有城市")
    public List<Map<String, Object>> getCityAll() throws AdminServiceException;

    @HttpMethod(description = "获取所有区(县)")
    public List<Map<String, Object>> getCountyAll() throws AdminServiceException;

    @HttpMethod(description = "根据省份主键获取城市")
    public List<Map<String, Object>> getCity(@NotNull @HttpParam(name = "provinceId", type = HttpParamType.COMMON, description = "省份主键ID") Long provinceId) throws ServiceException;

    @HttpMethod(description = "根据城市主键获取区(县)")
    public List<Map<String, Object>> getCounty(@NotNull @HttpParam(name = "cityId", type = HttpParamType.COMMON, description = "城市主键ID") Long cityId) throws ServiceException;


}
