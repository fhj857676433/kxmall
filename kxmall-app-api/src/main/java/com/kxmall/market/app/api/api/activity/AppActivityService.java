package com.kxmall.market.app.api.api.activity;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.InviteDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;

import java.util.List;
import java.util.Map;

/**
 * APP活动接口
 *
 * @author kaixin
 */
@HttpOpenApi(group = "activity",description = "活动管理")
public interface AppActivityService {

    @HttpMethod(description = "邀请抢购信息")
    public Map<String,Object> inviteBuyingInfo(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户id") Long userId
    )throws ServiceException;


    @HttpMethod(description = "邀请列表")
    public List<InviteDTO> invitationList(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户id") Long userId,
            @HttpParam(name = "pageNo", valueDef = "1", type = HttpParamType.COMMON, description = "分页查询偏移量") Integer pageNo,
            @HttpParam(name = "pageSize", valueDef = "10", type = HttpParamType.COMMON, description = "分页查询长度") Integer pageSize
    )throws ServiceException;

    @HttpMethod(description = "活动商品列表")
    public List<SpuDTO> activityGoodsList(
            @NotNull @HttpParam(name = "activityType", type = HttpParamType.COMMON, description = "活动类型[1新人注册活动2邀请新人活动3优惠券128活动]") Integer activityType,
            @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "仓库id[说明：无仓库id用于宣传单展示，有仓库id用于指定抢购下单使用]") Long storageId,
            @HttpParam(name = "pageNo", valueDef = "1", type = HttpParamType.COMMON, description = "分页查询偏移量") Integer pageNo,
            @HttpParam(name = "pageSize", valueDef = "10", type = HttpParamType.COMMON, description = "分页查询长度") Integer pageSize
    )throws ServiceException;


    @HttpMethod(description = "获取当前用户的邀请码")
    public String shareCode(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户id") Long userId
    )throws ServiceException;

}
