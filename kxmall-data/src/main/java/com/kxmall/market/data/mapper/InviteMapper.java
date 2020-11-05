package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.InviteDO;
import com.kxmall.market.data.dto.InviteDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 邀请管理mapper
 *
 * @author kaixin
 * @date 2020/3/6
 */
public interface InviteMapper extends BaseMapper<InviteDO> {

    /**
     * 获取邀请列表
     * @param rowBounds 分页对象
     * @param userId 用户id
     * @return 邀请对象集合
     */
    List<InviteDTO> selectInviteByList(@Param("rowBounds") RowBounds rowBounds, @Param("userId") Long userId);

    /**
     * 获取邀请列表总数
     * @param userId 用户id
     * @return 邀请对象集合
     */
    Integer selectInviteByCount(@Param("userId") Long userId);
}
