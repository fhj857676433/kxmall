package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.FreightTemplateDO;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-07
 * Time: 下午3:26
 */
public interface FreightTemplateMapper extends BaseMapper<FreightTemplateDO> {

    public FreightTemplateDO selectFreightBySkuId(@Param("skuId") Long skuId);
}
