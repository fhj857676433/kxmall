package com.kxmall.market.admin.api.api.config;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.ConfigDO;
import com.kxmall.market.data.model.Page;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-20
 * Time: 上午10:18
 */

@HttpOpenApi(group = "admin.merchant", description = "参数配置")
public interface AdminMerchantConfigService {

    @HttpMethod(description = "列表", permission = "promote:merchant:create", permissionParentName = "参数配置", permissionName = "参数配置")
    public Page<ConfigDO> list(
            @HttpParam(name = "state", type = HttpParamType.COMMON, description = "状态【0： 停用 1：正常，2删除】") Integer state,
            @HttpParam(name = "keyWord", type = HttpParamType.COMMON, description = "参数名称") String keyWord,
            @HttpParam(name = "valueWorth", type = HttpParamType.COMMON, description = "参数值") String valueWorth,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "创建", permission = "promote:merchant:create", permissionParentName = "参数配置", permissionName = "参数配置")
    public ConfigDO addConfig(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "keyWord", type = HttpParamType.COMMON, description = "参数名称") String keyWord,
            @NotNull @HttpParam(name = "valueWorth", type = HttpParamType.COMMON, description = "参数值") String valueWorth,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "描述") String description
    ) throws ServiceException;


    @HttpMethod(description = "修改", permission = "promote:merchant:update", permissionParentName = "参数配置", permissionName = "商铺信息管理")
    public ConfigDO updateConfig(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "参数主键") Long id,
            @NotNull @HttpParam(name = "keyWord", type = HttpParamType.COMMON, description = "参数名称") String keyWord,
            @NotNull @HttpParam(name = "valueWorth", type = HttpParamType.COMMON, description = "参数值") String valueWorth,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "描述") String description
    ) throws ServiceException;

    @HttpMethod(description = "详情", permission = "promote:merchant:update", permissionParentName = "参数配置", permissionName = "参数配置")
    public ConfigDO configDetail(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "参数主键") Long id
    ) throws ServiceException;


}
