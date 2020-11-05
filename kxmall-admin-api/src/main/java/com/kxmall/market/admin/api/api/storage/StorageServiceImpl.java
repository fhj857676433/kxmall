package com.kxmall.market.admin.api.api.storage;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.enums.StorageBusinessStatusType;
import com.kxmall.market.data.enums.StorageStatusType;
import com.kxmall.market.data.mapper.StorageMapper;
import com.kxmall.market.data.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 仓库接口服务
 *
 * @author fy
 * @date 2020/2/18
 */
@Service
public class StorageServiceImpl implements StorageService {

    private static final String STORAGE_INFO_PREFIX = "STORAGE_INFO_";

    private static final String PROVINCIAL_WAREHOUSE_PREFIX = "PROVINCIAL_WAREHOUSE_";

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public Page<StorageDO> list(Integer state, Integer operatingState, String name, Long province, Long city, Long county, Integer page, Integer limit, Long adminId) throws ServiceException {
        Wrapper<StorageDO> wrapper = new EntityWrapper<>();
        if (state != null) {
            wrapper.eq("state", state);
        }
        if (operatingState != null) {
            wrapper.eq("operating_state", operatingState);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name).or().like("id", name);
        }
        if (!StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        List<StorageDO> storageDOs = storageMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);
        Integer count = storageMapper.selectCount(wrapper);
        return new Page<>(storageDOs, page, limit, count);
    }

    @Override
    public StorageDO create(StorageDO storageDO, Long adminId) throws ServiceException {
        storageDO.setState(StorageStatusType.NOMRAL.getCode());
        storageDO.setOperatingState(StorageBusinessStatusType.REST.getCode());
        Date now = new Date();
        storageDO.setGmtUpdate(now);
        storageDO.setGmtCreate(now);
        storageDO.setGmtUpdateUserId(adminId);
        storageDO.setGmtCreateUserId(adminId);
        if (storageMapper.insert(storageDO) > 0) {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
            return storageDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

//    @Override
//    public String delete(Long storageId, Long adminId) throws ServiceException {
//        if (storageMapper.deleteById(storageId) > 0) {
//            return "ok";
//        }
//        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
//    }

    @Override
    public StorageDO update(StorageDO storageDO, Long adminId) throws ServiceException {
        storageDO.setGmtUpdate(new Date());
        storageDO.setGmtUpdateUserId(adminId);
        if (storageMapper.updateById(storageDO) > 0) {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
            return storageDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public StorageDO selectById(Long id, Long adminId) {
        return storageMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateStateToNomral(Long adminId, String idsJson) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        }
        if (storageMapper.batchUpdateState(ids, StorageStatusType.NOMRAL.getCode()) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        } else {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateStateToAbort(Long adminId, String idsJson) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        }
        if (storageMapper.batchUpdateState(ids, StorageStatusType.ABORT.getCode()) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        } else {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateBusinessStateToOpen(Long adminId, String idsJson) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        }
        if (storageMapper.batchUpdateOperatingState(ids, StorageBusinessStatusType.BUSINESS.getCode()) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        } else {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateBusinessStateToRest(Long adminId, String idsJson) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        }
        if (storageMapper.batchUpdateOperatingState(ids, StorageBusinessStatusType.REST.getCode()) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.STORAGE_NOT_EXIST);
        } else {
            cacheComponent.delPrefixKey(STORAGE_INFO_PREFIX);
        }
        return "ok";
    }

    @Override
    public List<StorageDO> options(Long adminId) {
        return null;
    }

    @Override
    public List<StorageDO> getStorageAllByProvinceId(Long provinceId) throws ServiceException {
        Wrapper<StorageDO> wrapper = new EntityWrapper<>();
        wrapper.eq("province", provinceId);
        wrapper.eq("state", StorageStatusType.NOMRAL.getCode());
        return storageMapper.selectList(wrapper);
    }

    @Override
    public List<StorageDO> getStorageAllByCityId(Long cityId) throws ServiceException {
        Wrapper<StorageDO> wrapper = new EntityWrapper<>();
        wrapper.eq("city", cityId);
        wrapper.eq("state", StorageStatusType.NOMRAL.getCode());
        return storageMapper.selectList(wrapper);
    }

    @Override
    public List<StorageDO> getStorageAllByCountyId(Long countyId) throws ServiceException {
        Wrapper<StorageDO> wrapper = new EntityWrapper<>();
        wrapper.eq("county", countyId);
        wrapper.eq("state", StorageStatusType.NOMRAL.getCode());
        return storageMapper.selectList(wrapper);
    }


}
