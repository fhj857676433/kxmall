package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.CouponDTO;
import com.kxmall.market.data.model.KVModel;
import com.kxmall.market.data.domain.CouponDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/4
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    public Integer decCoupon(Long couponId);

    //这样写MyBatis无法直接映射泛型，只能用Long了
    public List<KVModel<Long, Long>> getUserCouponsCount(@Param("userId") Long userId, @Param("couponIds") List<Long> couponIds);

    public List<CouponDTO> getActiveCoupons();


    /**
     * 获取优惠券列表
     * @param title  标题
     * @param type   优惠券类型
     * @param status 状态
     * @param now    当前时间
     * @param offset 偏移量
     * @param limit  页码
     * @return 优惠券对象集合
     */
    public List<CouponDTO> getAdminCouponList(@Param("title") String title, @Param("type") Integer type, @Param("status") Integer status, @Param("now") Date now, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
