package com.kxmall.market.admin.api.api.goods;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.dto.goods.SpuTreeNodeDTO;
import com.kxmall.market.data.model.Page;

import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/11
 */
@HttpOpenApi(group = "admin.goods", description = "管理员商品服务")
public interface AdminGoodsService {

    @HttpMethod(description = "获取SPU树")
    public List<SpuTreeNodeDTO> getSpuBigTree(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "创建", permission = "operation:goods:create", permissionParentName = "商品管理", permissionName = "商品管理")
    public String create(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") SpuDTO spuDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "编辑", permission = "operation:goods:edit", permissionParentName = "商品管理", permissionName = "商品管理")
    public String edit(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") SpuDTO spuDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "列表", permission = "operation:goods:list", permissionParentName = "商品管理", permissionName = "商品管理")
    public Page<SpuDTO> list(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title,
            @HttpParam(name = "barcode", type = HttpParamType.COMMON, description = "商品条形码") String barcode,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品状态") Integer status,
            @HttpParam(name = "idsNotInJson", type = HttpParamType.COMMON, description = "过滤商品id数组") String idsNotInJson,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "operation:goods:detail", permissionParentName = "商品管理", permissionName = "商品管理")
    public SpuDTO detail(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "上下架", permission = "operation:goods:edit", permissionParentName = "商品管理", permissionName = "商品管理")
    public SpuDTO freezeOrActivation(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品想要变为的状态") Integer status,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "operation:goods:delete", permissionParentName = "商品管理", permissionName = "商品管理")
    public String delete(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "批量删除", permission = "operation:goods:batchdelete", permissionParentName = "商品管理", permissionName = "商品管理")
    public String batchDelete(
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "商品Id") String ids,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;



    @HttpMethod(description = "获取商品详情集合", permission = "operation:goods:list", permissionParentName = "商品管理", permissionName = "商品管理")
    public List<SpuDTO> detailByIds(
            @HttpParam(name = "idsJson", type = HttpParamType.COMMON, description = "商品主键数组Json字符串") String idsJson,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;
}
