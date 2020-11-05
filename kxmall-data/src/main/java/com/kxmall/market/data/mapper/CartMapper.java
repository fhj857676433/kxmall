package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.CartDTO;
import com.kxmall.market.data.domain.CartDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kaixin
 * @date 2019/7/3
 */
public interface CartMapper extends BaseMapper<CartDO> {

    public Integer countCart(Long userId);

    /**
     * 获取购物车商品
     *
     * @param userId    用户id
     * @param storageId 仓库id
     * @return 购物车DTO
     */
    public List<CartDTO> getCartList(@Param("userId") Long userId, @Param("storageId") Long storageId);

}
