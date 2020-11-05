package com.kxmall.market.app.api.api.appraise;

/*
@author admin
@date  2019/7/6 - 10:18
*/

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.appraise.AppraiseRequestDTO;
import com.kxmall.market.data.dto.appraise.AppraiseResponseDTO;
import com.kxmall.market.data.model.Page;

@HttpOpenApi(group = "appraise", description = "评价商品")
public interface AppraiseService {

    @HttpMethod(description = "增加评价")
    public Boolean addAppraise(
            @NotNull @HttpParam(name = "appraiseRequestDTO", type = HttpParamType.COMMON, description = "来自订单的评价数据") AppraiseRequestDTO appraiseRequestDTO,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "根据评论Id删除评论")
    public Boolean deleteAppraiseById(
            @NotNull @HttpParam(name = "appraiseId", type = HttpParamType.COMMON, description = "评论ID") Long appraiseId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId) throws ServiceException;


    @HttpMethod(description = "查询用户所有评论")
    public Page<AppraiseResponseDTO> getUserAllAppraise(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer pageSize) throws ServiceException;


    @HttpMethod(description = "查询商品的所有评论")
    public Page<AppraiseResponseDTO> getSpuAllAppraise(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "查询某一条评论")
    public AppraiseResponseDTO getOneById(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId,
            @NotNull @HttpParam(name = "AppraiseId", type = HttpParamType.COMMON, description = "评论Id") Long appraiseId) throws ServiceException;

    @HttpMethod(description = "改变评论通过状态")
    public Boolean changeState(
            @NotNull @HttpParam(name = "appraiseId", type = HttpParamType.COMMON, description = "评论Id") Long appraiseId) throws ServiceException;

}
