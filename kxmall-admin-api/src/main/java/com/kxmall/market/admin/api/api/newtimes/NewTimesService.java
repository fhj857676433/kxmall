package com.kxmall.market.admin.api.api.newtimes;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;
import com.kxmall.market.core.annotation.param.NotNull;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.NewTimesDO;
import com.kxmall.market.data.domain.StorageDO;

import java.util.List;


@HttpOpenApi(group = "admin.newtimes",description = "新鲜时报")
public interface NewTimesService {
    @HttpMethod(description = "模糊查询所有仓库的名称", permission = "admin.newtimes:getAllStorageName", permissionParentName = "后台新鲜时报", permissionName = "后台新鲜时报管理")
    public List<StorageDO> storagAllName(
            @NotNull @HttpParam(name = "name", type = HttpParamType.COMMON, description = "配皮仓库名")String name,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询仓库的新鲜时报", permission = "admin.newtimes:getNewTimes", permissionParentName = "后台新鲜时报", permissionName = "后台新鲜时报管理")
    public NewTimesDO getNewTimes(
            @NotNull @HttpParam(name = "storage_id", type = HttpParamType.COMMON, description = "仓库id")Integer storage_id,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "添加或者更新新鲜时报", permission = "admin.newtimes:getAllStorageName", permissionParentName = "后台新鲜时报", permissionName = "后台新鲜时报管理")
    public NewTimesDO updageOrAdd(
            @NotNull @HttpParam(name = "newTimesDO", type = HttpParamType.COMMON, description = "新鲜时报对象")NewTimesDO newTimesDO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
