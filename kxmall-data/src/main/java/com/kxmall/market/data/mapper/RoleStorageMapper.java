package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.RoleStorageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/08 14:28
 **/
public interface RoleStorageMapper extends BaseMapper<RoleStorageDO> {

    public Integer insertBatch(@Param("list") List<RoleStorageDO> roleStorageDOList);
}
