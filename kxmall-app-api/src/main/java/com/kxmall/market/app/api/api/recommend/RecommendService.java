package com.kxmall.market.app.api.api.recommend;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.model.Page;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午3:33
 * @author kaixin
 */

@HttpOpenApi(group = "recommend", description = "推荐商品接口")
public interface RecommendService {

    @HttpMethod(description = "指定仓库id根据传入推荐类型获得对应类型推荐商品")
    public Page<RecommendDTO> getRecommendByStorage(
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id") Long storageId,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "所需推荐类型") Integer recommedType,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize) throws ServiceException;

}
