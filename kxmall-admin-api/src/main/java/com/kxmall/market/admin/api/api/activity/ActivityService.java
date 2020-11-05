package com.kxmall.market.admin.api.api.activity;


import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.ActivityDTO;
import com.kxmall.market.data.dto.EnumsDTO;
import com.kxmall.market.data.model.Page;

import java.util.List;

/**
 *
 * @author kaixin
 * @date 2020/03/01
 */
@HttpOpenApi(group = "admin.activity", description = "活动管理服务")
public interface ActivityService {

    @HttpMethod(description = "新增",permission = "admin:activity:create", permissionParentName = "活动管理", permissionName = "活动管理")
    public String create(
            @NotNull @HttpParam(name = "activityDTO", type = HttpParamType.COMMON, description = "活动管理JSON数据") ActivityDTO activityDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "编辑", permission = "admin:activity:edit", permissionParentName = "活动管理", permissionName = "活动管理")
    public String edit(
            @NotNull @HttpParam(name = "activityDTO", type = HttpParamType.COMMON, description = "活动管理JSON数据") ActivityDTO activityDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "查询", permission = "admin:activity:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public ActivityDTO queryActivityById(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "活动ID") Long id) throws ServiceException;


    @HttpMethod(description = "列表", permission = "operation:activity:list", permissionParentName = "活动管理", permissionName = "活动管理")
    public Page<ActivityDTO> list(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title,
            @HttpParam(name = "activityType", type = HttpParamType.COMMON, description = "活动类型[1新人注册2邀请新人活动3优惠券128大礼包活动]") Integer activityType,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "活动状态") Integer status,
            @HttpParam(name = "activityStartTime", type = HttpParamType.COMMON, description = "活动有效期") Long activityStartTime,
            @HttpParam(name = "activityEndTime", type = HttpParamType.COMMON, description = "活动有效期") Long activityEndTime,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "更新状态", permission = "operation:activity:updateStatus", permissionParentName = "活动管理", permissionName = "活动管理")
    public String updateStatus(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "活动id")Long  id,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "活动状态") Integer status,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "活动类型枚举列表")
    public List<EnumsDTO> getActivityTypeEnums() throws ServiceException;


    @HttpMethod(description = "生成链接")
    public String generateLinkes(
            @NotNull @HttpParam(name = "activityType", type = HttpParamType.COMMON, description = "活动类型[1新人注册2邀请新人活动3优惠券128大礼包活动]") Integer activityType
    ) throws ServiceException;

}
