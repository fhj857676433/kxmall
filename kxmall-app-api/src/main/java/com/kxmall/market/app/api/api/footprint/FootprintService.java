package com.kxmall.market.app.api.api.footprint;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.FootprintDTO;

import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 上午8:33
 */

@HttpOpenApi(group = "footprint", description = "足迹")
public interface FootprintService {

    @HttpMethod(description = "删除足迹")
    public boolean deleteFootprint(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId,
            @NotNull @HttpParam(name = "footprintId", type = HttpParamType.COMMON, description = "足迹Id") Long footprintId) throws ServiceException;

    @HttpMethod(description = "分页查询所有足迹,通过时间顺序")
    public List<FootprintDTO> getAllFootprint(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

}
