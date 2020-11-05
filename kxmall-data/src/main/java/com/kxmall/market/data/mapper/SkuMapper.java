package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.goods.SkuDTO;
import com.kxmall.market.data.domain.SkuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/7/2.
 */
public interface SkuMapper extends BaseMapper<SkuDO> {

    public SkuDTO getSkuDTOById(Long skuId);

    public Integer decSkuStock(@Param("skuId") Long skuId,@Param("stock") Integer stock);

    public SkuDTO getSkuDTOBySkuIdAndStorageId(@Param("skuId") Long skuId,@Param("storageId") Long storageId);
    /**
     * 删除SPUID
     * @param spuId
     * @return
     */
    public List<Long> getSkuIds(@Param("spuId") Long spuId);

    List<Long> selectSkuIdsBySpuIds(@Param("ids") List<Long> ids);

    /**
     * 查询商品code
     * @return List<String>
     */
    List<String> selectCodes();
}
