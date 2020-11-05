package com.kxmall.market.admin.api.api.enterstock;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.EnterStockDO;
import com.kxmall.market.data.dto.EnterStockDTO;
import com.kxmall.market.data.model.Page;

import java.util.Map;


/**
 * 入库接口服务
 *
 * @author wxf
 * @date 2020/3/1
 */
@HttpOpenApi(group = "admin.enterStock", description = "入库服务")
public interface EnterStockService {

    @HttpMethod(description = "列表", permission = "admin.enterStock:list", permissionParentName = "商品入库管理", permissionName = "商品入库列表")
    Page<EnterStockDTO> list(
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库ID") Long storageId,
            @HttpParam(name = "enterStockStatus", type = HttpParamType.COMMON, description = "入库状态[0:待入库，1:已入库]") Integer enterStockStatus,
            @HttpParam(name = "enterStockNo", type = HttpParamType.COMMON, description = "入库单号") String enterStockNo,
            @HttpParam(name = "gmtCreate", type = HttpParamType.COMMON, description = "入库时间") Long gmtCreate,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "添加", permission = "admin.enterStock:create", permissionParentName = "商品入库管理", permissionName = "商品入库添加")
    String create(
            @NotNull @HttpParam(name = "enterStock", type = HttpParamType.COMMON, description = "入库对象") String enterStockDOs,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改入库状态", permission = "admin.enterStock:updateEnterStockStatus", permissionParentName = "商品入库管理", permissionName = "修改入库状态")
    EnterStockDO UpdateEnterStockStatus(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "入库id") Long id,
            @NotNull @HttpParam(name = "enterStockStatus", type = HttpParamType.COMMON, description = "入库状态[0:待入库，1:已入库]") Integer enterStockStatus,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "删除", permission = "admin.enterStock:delete", permissionParentName = "商品入库管理", permissionName = "商品入库删除")
    String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "入库id") Long id,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新", permission = "admin.enterStock:update", permissionParentName = "商品入库管理", permissionName = "商品入库更新")
    EnterStockDO update(
            @NotNull @HttpParam(name = "enterStock", type = HttpParamType.COMMON, description = "入库对象") EnterStockDO storageDO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "admin.enterStock:selectById", permissionParentName = "商品入库管理", permissionName = "商品入库详情")
    EnterStockDO selectById(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "入库id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "批量删除", permission = "admin.enterStock:deleteBatch", permissionParentName = "商品入库管理", permissionName = "商品入库批量删除")
    String deleteBatch(
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "入库id数组") Long[] ids,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询入库详情", permission = "admin.enterStock:getGoodsDetail", permissionParentName = "商品入库管理", permissionName = "查询入库详情")
    Map<String, Object> getGoodsDetail(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "入库id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
