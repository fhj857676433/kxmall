package com.kxmall.market.app.api.api.goods;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.model.Page;

/**
 * 提供给app端口商品业务类
 *
 * @author kaixin
 * @date 2020/02/22
 */
@HttpOpenApi(group = "goods", description = "商品服务")
public interface GoodsService {

    @Deprecated
    @HttpMethod(description = "搜索Goods列表")
    public Page<SpuDTO> getGoodsPage(
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "orderBy", type = HttpParamType.COMMON, description = "排序 id 或 sales", valueDef = "a.id") String orderBy,
            @HttpParam(name = "isAsc", type = HttpParamType.COMMON, description = "是否升序", valueDef = "false") Boolean isAsc,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title) throws ServiceException;

    @Deprecated
    @HttpMethod(description = "获取商品详情")
    public SpuDTO getGoods(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @HttpParam(name = "groupShopId", type = HttpParamType.COMMON, description = "团购Id") Long groupShopId,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;


    @HttpMethod(description = "APP端-制定仓库下搜索Goods列表")
    public Page<SpuDTO> getGoodsPageByStorage(
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id") Long storageId,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "orderBy", type = HttpParamType.COMMON, description = "排序 id 或 sales", valueDef = "a.id") String orderBy,
            @HttpParam(name = "isAsc", type = HttpParamType.COMMON, description = "是否升序", valueDef = "false") Boolean isAsc,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title) throws ServiceException;

    @HttpMethod(description = "APP端-制定仓库下获取商品详情")
    public SpuDTO getGoodsByStorage(
            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id") Long storageId,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @HttpParam(name = "activityId", type = HttpParamType.COMMON, description = "活动id",valueDef = "0") Long activityId,
            @HttpParam(name = "couponId", type = HttpParamType.COMMON, description = "购物券id",valueDef = "0") Long couponId
            ) throws ServiceException;


}
