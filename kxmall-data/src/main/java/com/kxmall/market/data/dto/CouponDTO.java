package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.CouponSkuDO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author kaixin
 * @date 2020/3/4
 */
@Data
public class CouponDTO extends SuperDTO {

    @DtoDescription(description = "购物券名称")
    private String title;
    @DtoDescription(description = "购物券类型1优惠券2抢购券")
    private Integer type;
    @DtoDescription(description = "优惠类型1满减券")
    private Integer discountType;
    @DtoDescription(description = "活动介绍")
    private String description;
    @DtoDescription(description = "满减金额")
    private Integer discount;
    @DtoDescription(description = "最低消费")
    private Integer min;
    @DtoDescription(description = "优惠券状态1正常0失效")
    private Integer status;
    @DtoDescription(description = "类目id")
    private Long categoryId;
    @DtoDescription(description = "领券相对天数")
    private Integer days;
    @DtoDescription(description = "指定绝对开始时间")
    private Date gmtStart;
    @DtoDescription(description = "指定结束开始时间")
    private Date gmtEnd;
    @DtoDescription(description = "是否多选一 0否 1 是")
    private Integer chooseOne;


    @DtoDescription(description = "优惠券指定商品新增对象")
    private List<CouponSkuDO> couponSkuDoList;

}
