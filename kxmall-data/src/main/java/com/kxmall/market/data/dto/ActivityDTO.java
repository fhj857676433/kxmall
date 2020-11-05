package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.ActivityCounponDO;
import com.kxmall.market.data.domain.SuperDO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动对象
 *
 * @author kaixin
 */
@Data
public class ActivityDTO extends SuperDO {

    /**
     * 活动名称
     */
    @DtoDescription(description = "活动名称")
    private String title;
    /**
     * 活动类型1新人注册活动2邀请新人活动3优惠券128活动
     */
    @DtoDescription(description = "活动类型1新人注册活动2邀请新人活动3优惠券128活动")
    private Integer activityType;

    /**
     * 活动状态0失效1正常2到期
     */
    @DtoDescription(description = "活动状态0失效1正常2到期")
    private Integer status;
    /**
     * 创建/修改人id
     */
    @DtoDescription(description = "创建/修改人id")
    private Long userId;

    /**
     * 活动说明
     */
    @DtoDescription(description = "说明")
    private String remark;



    @DtoDescription(description = "新用户参与资格（0无1有）")
    private Boolean qualifyNew;


    @DtoDescription(description = "老用户是否有参与资格（0无1有）")
    private Boolean qualifyOld;

    @DtoDescription(description = "拉新资格（0无1有）")
    private Boolean pullNew;

    @DtoDescription(description = "拉新数量")
    private Integer pullNum;

    @DtoDescription(description = "参与次数限制（0不限制）")
    private Integer limit;

    /**
     * 活动链接
     */
    @DtoDescription(description = "活动链接")
    private String url;

    /**
     * 活动开始时间
     */
    @DtoDescription(description = "活动开始时间")
    private Date activityStartTime;

    /**
     * 活动开始时间
     */
    @DtoDescription(description = "活动结束时间")
    private Date activityEndTime;


    @DtoDescription(description = "优惠券对象集合")
    private List<ActivityCounponDO> activityCounponDO;

    @DtoDescription(description = "管理员登录")
    private AdminDTO adminDTO;

}
