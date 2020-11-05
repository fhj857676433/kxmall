package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.ImgDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/7/1.
 */
public interface ImgMapper extends BaseMapper<ImgDO> {

    public List<String> getImgs(@Param("bizType") Integer bizType, @Param("bizId") Long bizId);

    public Integer insertImgs(List<ImgDO> imgs);

}
