package com.kxmall.market.admin.api.api.storage;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.model.Page;

import java.util.List;

/**
 * 仓库接口服务
 *
 * @author fy
 * @date 2020/2/18
 */
@HttpOpenApi(group = "admin.storage", description = "仓库服务")
public interface StorageService {

    @HttpMethod(description = "列表", permission = "admin.storage:list", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public Page<StorageDO> list(
            @HttpParam(name = "state", type = HttpParamType.COMMON, description = "仓库状态[0:禁用，1：正常]") Integer state,
            @HttpParam(name = "operatingState", type = HttpParamType.COMMON, description = "营业状态[0:休息，1:营业]") Integer operatingState,
            @HttpParam(name = "name", type = HttpParamType.COMMON, description = "名称") String name,
            @HttpParam(name = "province", type = HttpParamType.COMMON, description = "省") Long province,
            @HttpParam(name = "city", type = HttpParamType.COMMON, description = "市") Long city,
            @HttpParam(name = "county", type = HttpParamType.COMMON, description = "区") Long county,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "添加", permission = "admin.storage:create", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public StorageDO create(
            @NotNull @HttpParam(name = "storage", type = HttpParamType.COMMON, description = "前置仓对象") StorageDO storageDO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

//    @HttpMethod(description = "删除", permission = "admin.storage:delete", permissionParentName = "仓库管理", permissionName = "前置仓管理")
//    public String delete(
//            @NotNull @HttpParam(name = "storageId", type = HttpParamType.COMMON, description = "前置仓Id") Long storageId,
//            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新", permission = "admin.storage:update", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public StorageDO update(
            @NotNull @HttpParam(name = "storage", type = HttpParamType.COMMON, description = "前置仓对象") StorageDO storageDO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "admin.storage:selectById", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public StorageDO selectById(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "前置仓id") Long id,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "前置仓状态批量更新为正常", permission = "admin.storage:batchToNomral", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public String updateStateToNomral(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "仓库主键数组Json字符串") String idsJson) throws ServiceException;

    @HttpMethod(description = "前置仓状态批量更新为禁用", permission = "admin.storage:batchToAbort", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public String updateStateToAbort(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "仓库主键数组Json字符串") String idsJson) throws ServiceException;

    @HttpMethod(description = "前置仓营业状态批量更新为营业中", permission = "admin.storage:batchToOpen", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public String updateBusinessStateToOpen(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "仓库主键数组Json字符串") String idsJson) throws ServiceException;

    @HttpMethod(description = "前置仓营业状态批量更新为休息中", permission = "admin.storage:batchToRest", permissionParentName = "仓库管理", permissionName = "前置仓管理")
    public String updateBusinessStateToRest(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "仓库主键数组Json字符串") String idsJson) throws ServiceException;

    @HttpMethod(description = "前置仓枚举")
    public List<StorageDO> options(
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "根据省份主键获取当前省份的所有仓库")
    public List<StorageDO> getStorageAllByProvinceId(
            @NotNull @HttpParam(name = "provinceId", type = HttpParamType.COMMON, description = "省份主键ID") Long provinceId) throws ServiceException;

    @HttpMethod(description = "根据城市主键获取当前城市的所有仓库")
    public List<StorageDO> getStorageAllByCityId(
            @NotNull @HttpParam(name = "cityId", type = HttpParamType.COMMON, description = "城市主键ID") Long cityId) throws ServiceException;

    @HttpMethod(description = "根据区县主键获取当前区县的所有仓库")
    public List<StorageDO> getStorageAllByCountyId(
            @NotNull @HttpParam(name = "countyId", type = HttpParamType.COMMON, description = "城市主键ID") Long countyId) throws ServiceException;
}
