package com.kxmall.market.admin.api.api.coupon;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.CouponDTO;
import com.kxmall.market.data.dto.UserCouponDTO;
import com.kxmall.market.data.model.Page;

import java.util.List;

/**
 * 后台优惠券管理
 *
 *
 * Description:
 * User: admin
 * Date: 2019-07-12
 * Time: 下午10:47
 * @author kaixin
 */

@HttpOpenApi(group = "admin.coupon", description = "优惠卷")
public interface AdminCouponService {

    @HttpMethod(description = "创建", permission = "promote:coupon:create", permissionParentName = "推广管理", permissionName = "优惠管理")
    public String addCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "couponDTO", type = HttpParamType.COMMON, description = "优惠券对象") CouponDTO couponDTO ) throws ServiceException;


    @HttpMethod(description = "删除", permission = "promote:coupon:delete", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Boolean deleteCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id) throws ServiceException;


    @HttpMethod(description = "查询", permission = "promote:coupon:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public CouponDTO queryCouponById(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id) throws ServiceException;


    @HttpMethod(description = "获取优惠券详情集合", permission = "promote:coupon:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public List<CouponDTO> queryCouponByIds(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "idsJson", type = HttpParamType.COMMON, description = "商品主键数组Json字符串") String idsJson
    ) throws ServiceException;



    @HttpMethod(description = "修改", permission = "promote:coupon:update", permissionParentName = "推广管理", permissionName = "优惠管理")
    public String updateCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "couponDTO", type = HttpParamType.COMMON, description = "优惠券对象") CouponDTO couponDTO) throws ServiceException;


    @HttpMethod(description = "修改", permission = "promote:coupon:update", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Boolean updateCouponStatus(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态[0失效/1正常]") Integer status) throws ServiceException;



    @HttpMethod(description = "查询", permission = "promote:coupon:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Page<CouponDTO> queryCouponByTitle(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "优惠卷标题") String title,
            @HttpParam(name = "type", type = HttpParamType.COMMON, description = "优惠卷类型") Integer type,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态") Integer status,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit) throws ServiceException;



    @HttpMethod(description = "获取优惠用户列表", permission = "promote:coupon:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Page<UserCouponDTO> queryUserCouponByList(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "name", type = HttpParamType.COMMON, description = "用户昵称或用户手机号") String name,
            @HttpParam(name = "activityName", type = HttpParamType.COMMON, description = "活动名称") String activityName,
            @HttpParam(name = "activityType", type = HttpParamType.COMMON, description = "活动状态") Integer activityType,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit) throws ServiceException;


}
