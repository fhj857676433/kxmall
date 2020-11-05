package com.kxmall.market.admin.api.api.region;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.Const;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.RegionDO;
import com.kxmall.market.data.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionDOMapper;

    @Autowired
    private CacheComponent cacheComponent;

    private static final Integer PROVINCE_LEVEL = 1;
    private static final Integer CITY_LEVEL = 2;
    private static final Integer COUNTY_LEVEL = 3;
    private static final String PROVINCE_INFO_DATA = "PROVINCE_INFO_DATA";
    private static final String CITY_INFO_DATA = "CITY_INFO_DATA";
    private static final String COUNTY_INFO_DATA = "COUNTY_INFO_DATA";
    private static final String CITY_INFO_DATA_PREFIX = "CITY_INFO_DATA_PREFIX_";
    private static final String COUNTY_INFO_DATA_PREFIX = "COUNTY_INFO_DATA_PREFIX_";

    @Override
    public List<Map<String, Object>> getProvinceAll() throws AdminServiceException {
        List<RegionDO> regionDOList = cacheComponent.getObjList(PROVINCE_INFO_DATA, RegionDO.class);
        if (regionDOList == null || regionDOList.size() == 0) {
            Wrapper<RegionDO> wrapper = new EntityWrapper<>();
            wrapper.eq("level", PROVINCE_LEVEL);
            wrapper.orderBy("sort", false);
            regionDOList = regionDOMapper.selectList(wrapper);
            if (regionDOList != null && regionDOList.size() > 0) {
                cacheComponent.putObj(PROVINCE_INFO_DATA, regionDOList, Const.CACHE_SEVEN_DAY);
            }
        }
        List<Map<String, Object>> list = buildRegionListMapInfo(regionDOList);
        if (list != null) {
            return list;
        }
        throw new AdminServiceException(ExceptionDefinition.PROVINCE_IS_EMPTY);
    }

    @Override
    public List<Map<String, Object>> getCityAll() throws AdminServiceException {
        List<RegionDO> regionDOList = cacheComponent.getObjList(CITY_INFO_DATA, RegionDO.class);
        if (regionDOList == null || regionDOList.size() == 0) {
            Wrapper<RegionDO> wrapper = new EntityWrapper<>();
            wrapper.eq("level", CITY_LEVEL);
            wrapper.orderBy("sort", false);
            regionDOList = regionDOMapper.selectList(wrapper);
            if (regionDOList != null && regionDOList.size() > 0) {
                cacheComponent.putObj(CITY_INFO_DATA, regionDOList, Const.CACHE_SEVEN_DAY);
            }
        }
        List<Map<String, Object>> list = buildRegionListMapInfo(regionDOList);
        if (list != null) {
            return list;
        }
        throw new AdminServiceException(ExceptionDefinition.CITY_IS_EMPTY);
    }

    @Override
    public List<Map<String, Object>> getCountyAll() throws AdminServiceException {
        List<RegionDO> regionDOList = cacheComponent.getObjList(COUNTY_INFO_DATA, RegionDO.class);
        if (regionDOList == null || regionDOList.size() == 0) {
            Wrapper<RegionDO> wrapper = new EntityWrapper<>();
            wrapper.eq("level", COUNTY_LEVEL);
            wrapper.orderBy("sort", false);
            regionDOList = regionDOMapper.selectList(wrapper);
            if (regionDOList != null && regionDOList.size() > 0) {
                cacheComponent.putObj(COUNTY_INFO_DATA, regionDOList, Const.CACHE_SEVEN_DAY);
            }
        }
        List<Map<String, Object>> list = buildRegionListMapInfo(regionDOList);
        if (list != null) {
            return list;
        }
        throw new AdminServiceException(ExceptionDefinition.COUNTY_IS_EMPTY);
    }

    @Override
    public List<Map<String, Object>> getCity(Long provinceId) throws ServiceException {
        List<RegionDO> regionDOList = cacheComponent.getObjList(CITY_INFO_DATA_PREFIX + provinceId, RegionDO.class);
        if (regionDOList == null || regionDOList.size() == 0) {
            RegionDO provinceRegionDO = regionDOMapper.selectById(provinceId);
            if (provinceRegionDO != null) {
                Wrapper<RegionDO> wrapper = new EntityWrapper<>();
                wrapper.eq("level", CITY_LEVEL);
                wrapper.eq("superior_code", provinceRegionDO.getCode());
                wrapper.orderBy("sort", false);
                regionDOList = regionDOMapper.selectList(wrapper);
                if (regionDOList != null && regionDOList.size() > 0) {
                    cacheComponent.putObj(CITY_INFO_DATA_PREFIX + provinceId, regionDOList, Const.CACHE_SEVEN_DAY);
                }
            }
        }
        List<Map<String, Object>> list = buildRegionListMapInfo(regionDOList);
        if (list != null) {
            return list;
        }
        throw new AdminServiceException(ExceptionDefinition.CITY_IS_EMPTY);
    }

    @Override
    public List<Map<String, Object>> getCounty(Long cityId) throws ServiceException {
        List<RegionDO> regionDOList = cacheComponent.getObjList(COUNTY_INFO_DATA_PREFIX + cityId, RegionDO.class);
        if (regionDOList == null || regionDOList.size() == 0) {
            RegionDO cityRegionDO = regionDOMapper.selectById(cityId);
            if (cityRegionDO != null) {
                Wrapper<RegionDO> wrapper = new EntityWrapper<>();
                wrapper.eq("level", COUNTY_LEVEL);
                wrapper.eq("superior_code", cityRegionDO.getCode());
                wrapper.orderBy("sort", false);
                regionDOList = regionDOMapper.selectList(wrapper);
                if (regionDOList != null && regionDOList.size() > 0) {
                    cacheComponent.putObj(COUNTY_INFO_DATA_PREFIX + cityId, regionDOList, Const.CACHE_SEVEN_DAY);
                }
            }
        }
        List<Map<String, Object>> list = buildRegionListMapInfo(regionDOList);
        if (list != null) {
            return list;
        }
        throw new AdminServiceException(ExceptionDefinition.COUNTY_IS_EMPTY);
    }

    private List<Map<String, Object>> buildRegionListMapInfo(List<RegionDO> regionDOList) {
        if (regionDOList != null && regionDOList.size() > 0) {
            List<Map<String, Object>> list = new LinkedList<>();
            regionDOList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("value", item.getId());
                map.put("label", item.getName());
                list.add(map);
            });
            return list;
        }
        return null;
    }
}
