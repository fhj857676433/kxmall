package com.kxmall.market.admin.api.api.stock;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.model.Page;

/**
 * 库存接口服务
 *
 * @author kaixin
 * @date 2020/2/18
 */
@HttpOpenApi(group = "admin.stock", description = "库存服务")
public interface StockService {

    @HttpMethod(description = "列表", permission = "admin.stock:list", permissionParentName = "库存管理", permissionName = "前置仓商品管理")
    Page<StockDTO> list(
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓id") Long storageId,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "name", type = HttpParamType.COMMON, description = "搜索商品条形码、id、名称") String name,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品状态") Integer status,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "idsNotInJson", type = HttpParamType.COMMON, description = "过滤商品id数组") String idsNotInJson,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "上下架", permission = "operation:stock:edit", permissionParentName = "库存管理", permissionName = "前置仓商品管理")
    StockDO freezeOrActivation(
            @NotNull @HttpParam(name = "stockId", type = HttpParamType.COMMON, description = "库存Id") Long stockId,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "库存商品想要变为的状态") Integer status,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "删除", permission = "admin.storage:delete", permissionParentName = "库存管理", permissionName = "前置仓商品管理")
    String delete(
            @NotNull @HttpParam(name = "stockId", type = HttpParamType.COMMON, description = "库存Id") Long stockId,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "新增", permission = "admin.storage:create", permissionParentName = "库存管理", permissionName = "前置仓商品管理")
    StockDO create(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品id") Long spuId,
            @NotNull @HttpParam(name = "skuId", type = HttpParamType.COMMON, description = "商品规格id") Long skuId,
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓id") Long storageId,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品状态") Integer status,
            @NotNull @HttpParam(name = "stock", type = HttpParamType.COMMON, description = "库存", valueDef = "1") Long stock,
            @NotNull @HttpParam(name = "sales", type = HttpParamType.COMMON, description = "销售量", valueDef = "20") Long sales,
            @NotNull @HttpParam(name = "frezzStock", type = HttpParamType.COMMON, description = "冻结库存", valueDef = "20") Long frezzStock,
            @NotNull @HttpParam(name = "price", type = HttpParamType.COMMON, description = "当前售价", valueDef = "20") Integer price,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新当前价格", permission = "admin.storage:updatePrice", permissionParentName = "库存管理", permissionName = "前置仓商品管理")
    Integer updatePrice(
            @NotNull @HttpParam(name = "stockId", type = HttpParamType.COMMON, description = "库存Id") Long stockId,
            @NotNull @HttpParam(name = "price", type = HttpParamType.COMMON, description = "当前价格") Integer price,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;



    @HttpMethod(description = "库存预警列表", permission = "admin.stock:list", permissionParentName = "库存管理", permissionName = "库存预警管理")
    Page<SpuDTO> warningList(
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓id") Long storageId,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "name", type = HttpParamType.COMMON, description = "搜索商品条形码、id、名称") String name,
            @HttpParam(name = "type", type = HttpParamType.COMMON, description = "库存数量类型") Integer type,
            @HttpParam(name = "minNum", type = HttpParamType.COMMON, description = "最小值") Integer minNum,
            @HttpParam(name = "maxNum", type = HttpParamType.COMMON, description = "最大值") Integer maxNum,
            @HttpParam(name = "showType", type = HttpParamType.COMMON, description = "只显示库存预警",valueDef = "false") Boolean showType,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "设置预警量", permission = "admin.stock:updateNum", permissionParentName = "库存管理", permissionName = "库存预警管理")
    Integer warningUpdate(
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓id") Long storageId,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品id") Long spuId,
            @NotNull @HttpParam(name = "num", type = HttpParamType.COMMON, description = "商品id") Integer num,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
