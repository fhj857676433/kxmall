package com.kxmall.market.data.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.goods.GroupShopSkuDTO;
import com.kxmall.market.data.domain.GroupShopSkuDO;

import java.util.List;

/*
@PackageName:com.kxmall.kxmall.data.mapper
@ClassName: GroupShopSkuMapper
@Description:
@author admin
@date 19-11-13下午4:28
*/
public interface GroupShopSkuMapper extends BaseMapper<GroupShopSkuDO>{

    public List<GroupShopSkuDTO> getSkuList(Long groupShopId);

}
