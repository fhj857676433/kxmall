package com.kxmall.market.app.api.api.integral;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.IntegralIndexDataDTO;

/**
 * 将多个接口聚合到一起，减少HTTP访问次数
 *
 * @author admin
 * @date 2019/7/14
 */
@HttpOpenApi(group = "integral", description = "聚合接口")
public interface IntegralService {

    @Deprecated
    @HttpMethod(description = "获取首页聚合数据")
    public IntegralIndexDataDTO getIndexData() throws ServiceException;


    @HttpMethod(description = "获取指定仓库下首页聚合数据")
    public IntegralIndexDataDTO getIndexDataByStorage(@NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id") Long storageId) throws ServiceException;

}
