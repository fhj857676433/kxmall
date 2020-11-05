package com.kxmall.market.admin.api.api.recommend;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.annotation.param.Range;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.EnumsDTO;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.model.Page;

import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午3:42
 * @author kaixin
 */

@HttpOpenApi(group = "admin.recommend", description = "推荐商品")
public interface AdminRecommendService {

    @HttpMethod(description = "创建", permission = "promote:recommend:create", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Boolean addRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType) throws ServiceException;

    @HttpMethod(description = "批量创建", permission = "promote:recommend:create", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Boolean addRecommendBatch(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "idsJson", type = HttpParamType.COMMON, description = "商品Ids")String idsJson,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType) throws ServiceException;

    @HttpMethod(description = "删除", permission = "promote:recommend:delete", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Boolean deleteRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "推荐id") Long id,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:recommend:query", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Page<RecommendDTO> queryRecommendByType(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:recommend:query", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Page<RecommendDTO> queryAllRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "推荐类型枚举列表")
    public List<EnumsDTO> getRecommendTypeEnums() throws ServiceException;


}
