package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.StorageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 前置仓资料Mapper
 *
 * @author fy
 * @date 2020/2/18
 */
public interface StorageMapper extends BaseMapper<StorageDO> {

	/**
     * 批量更新前置仓库资料状态
     *
     * @param state 前置仓库资料状态
     * @return 影响行数
     */
    List<StorageDO> getStorageNameAll(@Param("state") int state);

    /**
     * 根据区域编码和外包矩形获取正常状态下的仓库
     *
     * @param minlat 最小纬度
     * @param maxlat 最大纬度
     * @param minlng 经度
     * @param maxlng 最小最大经度
     * @return
     */
    List<StorageDO> selectNomralStorageByTownCodeAndOutsourcingRectangular(@Param("minlat") double minlat, @Param("maxlat") double maxlat,
                                                                           @Param("minlng") double minlng, @Param("maxlng") double maxlng);

    /**
     * 批量更新前置仓库资料状态
     *
     * @param ids   前置仓库主键集合
     * @param state 前置仓库资料状态
     * @return 影响行数
     */
    Integer batchUpdateState(@Param("ids") List<Long> ids, @Param("state") int state);

    /**
     * 批量更新前置仓库资料营业状态
     *
     * @param ids            前置仓库主键集合
     * @param operatingState 前置仓库资料营业状态
     * @return 影响行数
     */
    Integer batchUpdateOperatingState(@Param("ids") List<Long> ids, @Param("operatingState") int operatingState);

    /**
     * 根据主键查询仓库
     * @param id
     * @return StorageDO
     */
    StorageDO selectStorageById(@Param("id") Long id);

    /**
     * 查询仓库id
     * @return List<Long>
     */
    List<Long> selectIds();
}
