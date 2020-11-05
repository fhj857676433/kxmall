package com.kxmall.market.admin.api.api.goodsoutstock;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.GoodsOutStockDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.dto.GoodsOutStockDTO;
import com.kxmall.market.data.model.Page;

import java.util.List;

@HttpOpenApi(group = "admin.goodsOutStock",description = "商品出库服务")
public interface GoodsOutStockService {
    @HttpMethod(description = "列表", permission = "admin.goodsOutStock:list", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public Page<GoodsOutStockDO> list(
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库名称") Long storageId,
            @HttpParam(name = "outStockNumbers", type = HttpParamType.COMMON, description = "出库单号") String outStockNumbers,
            @HttpParam(name = "states", type = HttpParamType.COMMON, description = "出库状态") Integer states,
            @HttpParam(name = "outgoingDay", type = HttpParamType.COMMON, description = "出库日期") String outgoingDay,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "admin.goodsOutStock:selectById", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public GoodsOutStockDTO selectById(
            @NotNull @HttpParam(name = "OutStockNumbers", type = HttpParamType.COMMON, description = "出库单号") String OutStockNumbers,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "出库id") Long id,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "添加", permission = "admin.goodsOutStock:create", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public GoodsOutStockDTO create(
            @NotNull @HttpParam(name = "goodsOutStockDTO", type = HttpParamType.COMMON, description = "商品出库DTO对象") GoodsOutStockDTO goodsOutStockDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新", permission = "admin.goodsOutStock:update", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public GoodsOutStockDTO update(
            @NotNull @HttpParam(name = "goodsOutStockDTO", type = HttpParamType.COMMON, description = "商品出库对象") GoodsOutStockDTO goodsOutStockDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "出库", permission = "admin.goodsOutStock:updateStateOutgoing", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public String updateOutOfStock(
            @NotNull @HttpParam(name = "outgoingPerson", type = HttpParamType.COMMON, description = "出库人") String outgoingPerson,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id") Long storageId,
            @NotNull @HttpParam(name = "outStockNumbers", type = HttpParamType.COMMON, description = "出库单号") String outStockNumbers) throws ServiceException;

    @HttpMethod(description = "删除出库信息", permission = "admin.goodsoutStock:batchToDelete", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public String delete(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "出库主键id") Long id,
            @NotNull @HttpParam(name = "outStockNumbers", type = HttpParamType.COMMON, description = "出库单号") String outStockNumbers) throws ServiceException;

    @HttpMethod(description = "获取所有仓库的名称", permission = "admin.goodsOutStock:getAllStorage", permissionParentName = "商品出库管理", permissionName = "商品出库管理")
    public List<StorageDO> storagAllName(
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
