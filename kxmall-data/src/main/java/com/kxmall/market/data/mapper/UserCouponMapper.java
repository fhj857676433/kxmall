package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.domain.UserCouponDO;
import com.kxmall.market.data.dto.UserCouponDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/4
 */
public interface UserCouponMapper extends BaseMapper<UserCouponDO> {

    /**
     * 获取该用户优惠券列表
     * @param userId  用户id
     * @param couponType 优惠券类型
     * @param status 优惠券状态
     * @return 优惠券列表
     */
    public List<UserCouponDTO> getUserCoupons(@Param("userId") Long userId, @Param("couponType") Integer couponType, @Param("status") Integer status);

    public UserCouponDTO getUserCouponById(@Param("userCouponId") Long userCouponId, @Param("userId") Long userId);


    /**
     * 获取优惠券列表
     *
     * @param name         用户名称
     * @param activityName 活动名称
     * @param activityType 活动类型
     * @param rowBounds    分页对象
     * @return 优惠券集合
     */
    List<UserCouponDTO> selectUserCouponByPage(@Param("name") String name, @Param("activityName") String activityName, @Param("activityType") Integer activityType, @Param("rowBounds") RowBounds rowBounds);

    /**
     * 获取优惠券总数
     *
     * @param name         用户名称
     * @param activityName 活动名称
     * @param activityType 活动类型
     * @return 总数
     */
    Integer selectUserCouponByCount(@Param("name") String name, @Param("activityName") String activityName, @Param("activityType") Integer activityType);
}
