package com.kxmall.market.biz.service.positioning;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.Const;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.dto.storage.RecentlyStorageDTO;
import com.kxmall.market.data.enums.StorageBusinessStatusType;
import com.kxmall.market.data.enums.StorageStatusType;
import com.kxmall.market.data.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description: 经纬度获取前置仓
 * @author: fy
 * @date: 2020/02/19 19:43
 **/
@Service
public class PositioningBizService {

    private static final String STORAGE_INFO_PREFIX = "STORAGE_INFO_";

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 根据经纬度信息获取最近的仓库营业的前置仓库
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param adcode    高德API区域adcode
     * @return 最近的前置仓库
     */
    public RecentlyStorageDTO getRecentlyStorage(String adcode, BigDecimal longitude, BigDecimal latitude) {
        RecentlyStorageDTO recentlyStorageDTO = new RecentlyStorageDTO();
        // 获取当前区域范围的仓库
        List<StorageDO> storageDOList = cacheComponent.getObjList(STORAGE_INFO_PREFIX + adcode, StorageDO.class);
        if (storageDOList == null) {
            Wrapper<StorageDO> wrapper = new EntityWrapper<>();
            wrapper.eq("state", StorageStatusType.NOMRAL.getCode());
            if (!StringUtils.isEmpty(adcode)) {
                wrapper.eq("adcode", adcode);
            }
            storageDOList = storageMapper.selectList(wrapper);
            if (storageDOList != null && storageDOList.size() > 0) {
                cacheComponent.putObj(STORAGE_INFO_PREFIX + adcode, storageDOList, Const.CACHE_ONE_DAY);
            }
        }
        if (storageDOList != null && storageDOList.size() > 0) {
            // 获取有效配送范围内的仓库
            double userStorgaeDistance;
            double[] userGps = {latitude.doubleValue(), longitude.doubleValue()};
            Map<Double, StorageDO> distanceStorageDOMap = new HashMap<>(storageDOList.size());
            for (StorageDO storageDO : storageDOList) {
                userStorgaeDistance = calculationDistance(new double[]{storageDO.getLatitude().doubleValue(), storageDO.getLongitude().doubleValue()}, userGps);
                if (userStorgaeDistance <= storageDO.getDeliveryRadius() * 1000) {
                    distanceStorageDOMap.put(userStorgaeDistance, storageDO);
                }
            }
            if (distanceStorageDOMap.size() > 0) {
                StorageDO storageDO;
                StorageDO recentlyStorage = null;
                Map.Entry<Double, StorageDO> currentEntry;
                distanceStorageDOMap = sortMapByValue(distanceStorageDOMap);
                // 获取有效仓库
                Iterator<Map.Entry<Double, StorageDO>> iterator = distanceStorageDOMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    currentEntry = iterator.next();
                    storageDO = currentEntry.getValue();
                    if (StorageBusinessStatusType.BUSINESS.getCode() == storageDO.getOperatingState()) {
                        recentlyStorage = storageDO;
                        break;
                    }
                }
                if (recentlyStorage != null) {
                    // 有仓库，营业中
                    recentlyStorageDTO.setId(recentlyStorage.getId());
                    recentlyStorageDTO.setHaveStorage(true);
                    recentlyStorageDTO.setBusinessState(true);
                    recentlyStorageDTO.setBusinessStartTime(recentlyStorage.getBusinessStartTime());
                    recentlyStorageDTO.setBusinessStopTime(recentlyStorage.getBusinessStopTime());
                    recentlyStorageDTO.setDeliveryStartTime(recentlyStorage.getDeliveryStartTime());
                    recentlyStorageDTO.setDeliveryStopTime(recentlyStorage.getDeliveryStopTime());
                } else {
                    // 有仓库，没营业
                    Collection<Double> collection = distanceStorageDOMap.keySet();
                    Double mapKeyMin = Collections.min(collection);
                    recentlyStorage = distanceStorageDOMap.get(mapKeyMin);
                    recentlyStorageDTO.setId(recentlyStorage.getId());
                    recentlyStorageDTO.setHaveStorage(true);
                    recentlyStorageDTO.setBusinessState(false);
                    recentlyStorageDTO.setBusinessStartTime(recentlyStorage.getBusinessStartTime());
                    recentlyStorageDTO.setBusinessStopTime(recentlyStorage.getBusinessStopTime());
                    recentlyStorageDTO.setDeliveryStartTime(recentlyStorage.getDeliveryStartTime());
                    recentlyStorageDTO.setDeliveryStopTime(recentlyStorage.getDeliveryStopTime());
                }
            } else {
                recentlyStorageDTO.setHaveStorage(false);
            }
        } else {
            // 没仓库
            recentlyStorageDTO.setHaveStorage(false);
        }
        // TODO 测试数据写死仓库
        // ==============================
        recentlyStorageDTO.setId(11L);
        recentlyStorageDTO.setHaveStorage(true);
        recentlyStorageDTO.setBusinessState(true);
        //===============================
        return recentlyStorageDTO;
    }

    /**
     * 计算两点距离
     *
     * @param point1 点1
     * @param point2 点2
     * @return 两点之间的距离(米)
     */
    private static double calculationDistance(double[] point1, double[] point2) {
        double lat1 = point1[0];
        double lat2 = point2[0];
        double lng1 = point1[1];
        double lng2 = point2[1];
        double radLat1 = lat1 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return s * 6370996.81;
    }

    /**
     * 根据Key-Map排序
     *
     * @param oriMap 需要排序的Map
     * @return 排序后的Map
     */
    public static Map<Double, StorageDO> sortMapByValue(Map<Double, StorageDO> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<Double, StorageDO> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<Double, StorageDO>> entryList = new ArrayList<>(oriMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Double, StorageDO>>() {
            @Override
            public int compare(Map.Entry<Double, StorageDO> me1, Map.Entry<Double, StorageDO> me2) {
                return me2.getKey().compareTo(me1.getKey());
            }
        });
        Iterator<Map.Entry<Double, StorageDO>> iter = entryList.iterator();
        Map.Entry<Double, StorageDO> tmpEntry;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}
